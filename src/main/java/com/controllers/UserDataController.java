package com.controllers;

import com.entities.SecurityEntity;
import com.entities.UserCompleteDataEntity;
import com.entities.UserDataEntity;
import com.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.sql.SQLException;

@RestController
@RequestMapping("/user")
public class UserDataController {

    @Autowired
    UserDataService userDataService;

    @PostMapping(value = "/findUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SecurityEntity findUser(@RequestBody UserDataEntity userData) throws SQLException {
        return userDataService.findUser(userData);
    }

    @PostMapping(value = "/findUserWithSaltedPwd", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SecurityEntity findUserWithSaltedPwd(@RequestBody UserDataEntity userData) throws SQLException {
        return userDataService.findUserWithSaltedPwd(userData);
    }

    @PutMapping(value = "/insertUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean insertNewUser(@RequestBody UserCompleteDataEntity userDataToInsert) throws SQLException {
        boolean isUserAlreadyPresent = userDataService.checkUserisAlreadyPresent(userDataToInsert);
        return isUserAlreadyPresent  ? false :userDataService.insertNewUser(userDataToInsert);
    }
}
