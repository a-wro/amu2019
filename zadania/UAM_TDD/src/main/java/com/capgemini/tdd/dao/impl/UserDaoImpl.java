package com.capgemini.tdd.dao.impl;

import com.capgemini.tdd.dao.UserDao;
import com.capgemini.tdd.dao.entities.UserBE;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao
{

    private List<UserBE> allUsers;

    @PostConstruct
    private void postConstruct()
    {
        allUsers = fillData();
    }

    @Override
    public UserBE save(final UserBE userBE)
    {
        Long maxId = allUsers.stream().map(x -> x.getId()).max(Long::compare).get().longValue() + 1;
        UserBE newUser = new UserBE(userBE.getName());
        newUser.setId(Long.valueOf(maxId));
        allUsers.add(newUser);
        return newUser;
    }

    @Override
    public UserBE findByName(final String name)
    {
        return allUsers.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public UserBE findById(final Long id)
    {
        return allUsers.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<UserBE> findAll()
    {
        return allUsers;
    }

    private List<UserBE> fillData()
    {
        UserBE userBE1 = new UserBE("Adam");
        userBE1.setId(1L);

        UserBE userBE2 = new UserBE("Kasztan");
        userBE2.setId(2L);

        return new ArrayList<>(Arrays.asList(userBE1, userBE2));

    }
}
