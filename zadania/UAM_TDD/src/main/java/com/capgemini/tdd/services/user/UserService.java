package com.capgemini.tdd.services.user;

import com.capgemini.tdd.dao.entities.UserBE;

import java.util.List;

public interface UserService
{

    UserBE findByName(String name);

    List<UserBE> findAll();

}
