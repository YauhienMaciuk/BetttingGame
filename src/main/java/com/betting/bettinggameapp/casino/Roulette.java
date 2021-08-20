package com.betting.bettinggameapp.casino;

public class Roulette {
    private Slot[] slots;

    public Roulette() {
        slots = new Slot[]{
                Slot.WIN_TWENTY_EURO,
                Slot.WIN_TWENTY_EURO,
                Slot.WIN_TWENTY_EURO,
                Slot.WIN_TWENTY_EURO,
                Slot.WIN_TWENTY_EURO,
                Slot.WIN_TWENTY_EURO,
                Slot.FREE_ROUND,
                Slot.FREE_ROUND_AND_DOUBLE_MONEY_BACK,
                Slot.LOSE,
                Slot.LOSE,
                Slot.LOSE,
                Slot.LOSE,
                Slot.LOSE,
                Slot.LOSE,
                Slot.LOSE,
                Slot.LOSE,
                Slot.LOSE,
                Slot.LOSE,
                Slot.LOSE,
                Slot.LOSE
        };
    }

    public Slot[] getSlots() {
        return slots;
    }
}
