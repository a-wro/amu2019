package com.capgemini.tdd.dao.entities;

public class UserBE
{

    private Long id;
    private String name;

    public UserBE(final String pName)
    {
        name = pName;
    }

    public void setId(final Long pId)
    {
        id = pId;
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "UserBE{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }

    @Override
    public boolean equals(final Object pO)
    {
        if (this == pO)
            return true;
        if (pO == null || getClass() != pO.getClass())
            return false;

        final UserBE userBE = (UserBE) pO;

        if (!id.equals(userBE.id))
            return false;
        return name.equals(userBE.name);
    }

    @Override
    public int hashCode()
    {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
