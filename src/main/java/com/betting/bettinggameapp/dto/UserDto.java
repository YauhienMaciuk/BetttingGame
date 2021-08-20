package com.betting.bettinggameapp.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class UserDto {
    private long id;
    @NotBlank(message = "The firstName may not be blank")
    @Length(min = 1, max = 25)
    private String firstName;
    @NotBlank(message = "The lastName may not be blank")
    @Length(min = 1, max = 25)
    private String lastName;
    @NotBlank(message = "The nickname may not be blank")
    @Length(min = 1, max = 25)
    private String nickname;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
