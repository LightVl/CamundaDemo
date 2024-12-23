package ru.neoflex.education;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Warrior implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonAlias("first")
    private String firstName;
    @JsonAlias("last")
    private String lastName;
    private Boolean isAlive = true;

    public Warrior() {
    }

    public Warrior(String firstName, String lastName, Boolean isAlive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAlive = isAlive;
    }
    public Warrior(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAlive = true;
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

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean isAlive) {
        this.isAlive = isAlive;
    }

}