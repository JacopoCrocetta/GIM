package com.controllers;

import com.entities.DecoroCoppetteEntity;
import com.entities.UserDataEntity;
import com.entities.UtentiEntity;
import com.fasterxml.jackson.databind.node.TextNode;
import com.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utente")
public class UserDataController {

    @Autowired
    UserDataService userDataService;

    @GetMapping(value = "/findUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean findUtente(@RequestBody UserDataEntity userdata) throws SQLException, ClassNotFoundException, IOException {
        return userdata.getPWD().equals(userDataService.getIdUtenti(userdata.getUTENTE()));
    }
}
