package com.example.tourismapp;
public class User {
    public int Id;
    public String Name;
    public String Email;
    public String Password;

    public User(){
        this.Name = "";
        this.Email = "";
        this.Password = "";
    }

    public int getId() {
        return Id;
    }
    public void setId(int Id) {
        this.Id = Id;
    }
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
