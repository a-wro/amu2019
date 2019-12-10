package com.capgemini.tdd.dao;

import com.capgemini.tdd.dao.entities.UserBE;

import java.util.List;

public interface UserDao
{

    UserBE save(UserBE userBE);

    UserBE findByName(String name);

    UserBE findById(Long id);

    List<UserBE> findAll();
}
