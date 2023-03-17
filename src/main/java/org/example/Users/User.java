package org.example.Users;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.Enums.EnumID;

import java.util.ArrayList;

@Getter
@Setter
@ToString

public class User {
    public static ArrayList<User> ListSide = new ArrayList<>();
    private String name;
    private int ID;
    private EnumID enumID;
    public User(String name, int ID, EnumID enumID) {
        this.name = name;
        this.ID = ID;
        this.enumID=enumID;
    }
    public User(){

    }
}
