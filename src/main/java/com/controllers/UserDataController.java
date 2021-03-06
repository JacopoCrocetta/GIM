package com.controllers;

import com.entities.SecurityEntity;
import com.entities.SecurityResetEntity;
import com.entities.UserCompleteDataEntity;
import com.entities.UserDataEntity;
import com.services.UserDataService;
import com.utility.Filters;
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
        SecurityEntity userDataNotPresent = null;
        boolean isUserPresent = userDataService.checkUserisAlreadyPresent(userData.getUSER());
        if(!isUserPresent) {
            userDataNotPresent = new SecurityEntity();
            userDataNotPresent.setACCESS_GRANTED(false);
            userDataNotPresent.setACCESS_LOCKED(false);
            userDataNotPresent.setACCESS_PWD_ERR(false);
            System.err.println("Attenzione: utenza " + userData.getUSER() + " inesistente.");
        }
        return isUserPresent  ? userDataService.findUser(userData):userDataNotPresent;
    }

    @PostMapping(value = "/findUserWithSaltedPwd", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SecurityResetEntity findUserWithSaltedPwd(@RequestBody UserDataEntity userData) throws SQLException {
        SecurityResetEntity userDataNotPresent = null;
        boolean isUserPresent = userDataService.checkUserisAlreadyPresent(userData.getUSER());
        if(!isUserPresent) {
            userDataNotPresent = new SecurityResetEntity();
            userDataNotPresent.setACCESS_GRANTED(false);
            userDataNotPresent.setACCESS_LOCKED(false);
            userDataNotPresent.setACCESS_PWD_ERR(false);
            userDataNotPresent.setACCESS_PWD_NEW(null);
            System.err.println("Attenzione: utenza " + userData.getUSER() + " inesistente.");
        }
        return userDataService.findUserWithSaltedPwd(userData);
    }

    @PutMapping(value = "/insertUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SecurityEntity insertNewUser(@RequestBody UserCompleteDataEntity userDataToInsert) throws SQLException {
        SecurityEntity userDataPresent = null;
        boolean isUserAlreadyPresent = userDataService.checkUserisAlreadyPresent(userDataToInsert.getUSER());
        if(isUserAlreadyPresent) {
            userDataPresent = new SecurityEntity();
            userDataPresent.setACCESS_GRANTED(false);
            userDataPresent.setACCESS_LOCKED(true);
            userDataPresent.setACCESS_PWD_ERR(false);
            System.err.println("Attenzione: utenza " + userDataToInsert.getUSER() + " già presente.");
        }
        return isUserAlreadyPresent  ? userDataPresent :userDataService.insertNewUser(userDataToInsert);
    }

    @PostMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SecurityResetEntity insertNewUser(@RequestBody UserDataEntity userData) throws SQLException {
        SecurityResetEntity userDataNotPresent = null;
        boolean isUserPresent = userDataService.checkUserisAlreadyPresent(userData.getUSER());
        if(!isUserPresent) {
            userDataNotPresent = new SecurityResetEntity();
            userDataNotPresent.setACCESS_GRANTED(false);
            userDataNotPresent.setACCESS_LOCKED(false);
            userDataNotPresent.setACCESS_PWD_ERR(false);
            userDataNotPresent.setACCESS_PWD_NEW(null);
            System.err.println("Attenzione: utenza " + userData.getUSER() + " inesistente.");
        }

        return isUserPresent  ? userDataService.changePassword(userData):userDataNotPresent;
    }

}
