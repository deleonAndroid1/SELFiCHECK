package com.training.android.selficheck.Datas;

/**
 * Created by Maouusama on 3/27/2017.
 */

public class PersonData {
    private String Name, Email, Id, Role;

    public PersonData(){

    }

    public PersonData(String name, String email, String id, String role) {
        Name = name;
        Email = email;
        Id = id;
        Role = role;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

}
