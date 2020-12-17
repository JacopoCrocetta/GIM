package com.controllers;

import com.entities.UserCompleteDataEntity;
import com.entities.UserDataEntity;
import com.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/user")
public class UserDataController {

    @Autowired
    UserDataService userDataService;

    @GetMapping(value = "/findUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean findUtente(@RequestBody UserDataEntity userData) throws SQLException {
        return userData.getPWD().equals(userDataService.getPWDfromDB(userData.getUSER()));
    }

    @PutMapping(value = "/insertUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean addDecoroItem(@RequestBody UserCompleteDataEntity userDataToInsert) throws SQLException {
        return userDataService.insertNewUser(userDataToInsert);
    }
}
