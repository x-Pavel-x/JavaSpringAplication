package com.example.ArtifatName;

public class User
{
    private String name;
    private String familyName;
    private String birthday;
    public User(String name, String familyName, String birthday)
    {
        this.name = name;
        this.familyName = familyName;
        this.birthday = birthday;
    }
    public User(){}
    public String getName()
    {
        return this.name;
    }
    public String getFamilyName()
    {
        return this.familyName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString()
    {
        return " {Name : " + this.getName() + " Family Name : " + this.getFamilyName() + " Birthday : " + this.getBirthday() + "}";
    }
}
