package com.capgemini.tdd.services.user.impl;

import com.capgemini.tdd.dao.UserDao;
import com.capgemini.tdd.dao.entities.UserBE;
import com.capgemini.tdd.mappers.UserMapper;
import com.capgemini.tdd.services.TO.UserTO;
import com.capgemini.tdd.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserDao userDao;

    @Override
    public UserBE findByName(final String name)
    {
        return userDao.findByName(name);
    }

    @Override
    public List<UserBE> findAll()
    {
        return userDao.findAll();
    }
}
