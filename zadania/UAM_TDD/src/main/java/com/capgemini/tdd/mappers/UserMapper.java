package com.capgemini.tdd.mappers;

import com.capgemini.tdd.dao.entities.UserBE;
import com.capgemini.tdd.services.TO.UserTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper
{
    public static UserTO fromBE(UserBE userBE)
    {
        return new UserTO(userBE.getName());
    }

    public static List<UserTO> fromBEList(List<UserBE> userBEList)
    {
        return userBEList.stream().map( x -> fromBE(x) ).collect(Collectors.toList());
    }

}
