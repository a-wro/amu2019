package com.capgemini.tdd.rest;

import com.capgemini.tdd.mappers.UserMapper;
import com.capgemini.tdd.services.TO.UserTO;
import com.capgemini.tdd.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/players")
public class UserRestService
{

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{name}")
    public UserTO findById(@PathVariable String name) {
        return UserMapper.fromBE(userService.findByName(name));
    }

    @GetMapping(path="/", produces = "application/json")
    public List<UserTO> getEmployees()
    {
        return UserMapper.fromBEList(userService.findAll());
    }

}
