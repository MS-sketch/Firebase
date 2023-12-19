package com.ms.firebase;

public class Users {
    String firstName, lastName, DOB, email;

    public Users(){

    }

    public Users(String firstName, String lastName, String DOB, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.email = email;

    }

    // FIRST NAME
    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // LAST NAME
    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    // DOB
    public String getDOB(){
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }


    // EMAIL
    public String getEmail(){
        return email;
    }

    public void setEmail(){
        this.email = email;
    }


}
