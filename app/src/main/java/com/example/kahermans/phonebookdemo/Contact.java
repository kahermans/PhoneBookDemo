package com.example.kahermans.phonebookdemo;

public class Contact {
    private String name;
    private String number;
    private String uid;

    public Contact()
    {
        name ="NA";
        number="NA";
    }

    public Contact( String name, String number, String uid )
    {
        this.name = name;
        this.number = number;
        this.uid = uid;
    }

    public String getName()
    {
        return name;
    }

    public String getNumber()
    {
        return number;
    }

    public String getUid()
    {
        return uid;
    }
    public String toString()
    {
        return name + ": " + number;
    }
}
