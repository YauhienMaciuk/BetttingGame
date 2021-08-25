package com.betting.bettinggameapp.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class NamedLocks<K> {

    private final ReentrantLock activateLock = new ReentrantLock();
    private final Map<K, NamedLock> activeLocks = new HashMap<>();
    private NamedLock unusedLocks = null;
    private boolean fair;

    public NamedLocks() {
        this(false);
    }

    public NamedLocks(boolean fair) {
        this.fair = fair;
    }

    // named lock keeps name, usage count, and pointer to next unused lock
    // override unlock() method to decrease usage count and to remove self from active map, if usage count is zero
    private class NamedLock extends ReentrantLock implements Unlockable {
        private static final long serialVersionUID = -5118970711762896544L;

        // name is assigned before returning from unused queue or if new instance is created
        private K name;

        // usage count is increased only if the lock already is used (found into active map)
        private int usageCount;

        // is used to organize unidirectional linked list of unused named locks
        private NamedLock next;

        public NamedLock(boolean fair) {
            super(fair);
        }

        @Override
        public void unlock() {
            super.unlock(); // release external lock
            deactivateLock(this);
        }

        @Override
        public void close() {
            if (usageCount > 0) {
                unlock();
            }
        }
    }

    // Find lock into active locks map by specified name, if lock was not found
    // create new lock or fetch existent lock from unused queue and put it into active locks map
    private NamedLock activateLock(K lockName) {
        activateLock.lock();
        try {
            NamedLock namedLock = activeLocks.get(lockName);
            if (namedLock != null) {
                // found active named lock
                namedLock.usageCount++;
                return namedLock;
            }

            if (unusedLocks != null) {
                // fetch unused named lock
                namedLock = unusedLocks;
                unusedLocks = unusedLocks.next;
            } else {
                // create new named lock
                namedLock = new NamedLock(fair);
            }

            // assign requested lock name, put to activate locks map and return it
            namedLock.name = lockName;
            namedLock.usageCount = 1;
            activeLocks.put(lockName, namedLock);
            return namedLock;
        } finally {
            activateLock.unlock();
        }
    }

    // decrease usage count or remove from active map
    private void deactivateLock(NamedLock namedLock) {
        activateLock.lock();
        try {
            if (namedLock.usageCount > 1) {
                // somebody else uses Lock, decrease usage count only
                namedLock.usageCount--;
            } else {
                // nobody uses Lock, move lock from active locks map to unused queue
                activeLocks.remove(namedLock.name);
                namedLock.usageCount = 0;
                namedLock.next = unusedLocks;
                unusedLocks = namedLock;
            }
        } finally {
            activateLock.unlock();
        }
    }

    /**
     * Find existent Lock by name or create new one with specified name and apply lock() method to returned Lock object,
     * unlock() method must be called by client
     *
     * @param lockName - lockName
     * @return - Unlockable
     */
    public Unlockable lock(K lockName) {
        NamedLock namedLock = activateLock(lockName);
        try {
            namedLock.lock();
            return namedLock;
        } catch (RuntimeException ex) {
            deactivateLock(namedLock);
            throw ex;
        }
    }
}
