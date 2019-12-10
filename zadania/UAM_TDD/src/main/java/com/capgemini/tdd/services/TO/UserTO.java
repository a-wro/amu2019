package com.capgemini.tdd.services.TO;


public class UserTO
{
    private String name;

    public UserTO(final String pName)
    {
        name = pName;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "UserTO{" +
               "name='" + name + '\'' +
               '}';
    }
}
