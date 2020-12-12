package com.controllers;

import com.entities.DecoroCoppetteEntity;
import com.entities.UtentiEntity;
import com.fasterxml.jackson.databind.node.TextNode;
import com.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utente")
public class UtentiController {

    @Autowired
    UtentiService utentiService;

    @GetMapping(value = "/findUser", produces = MediaType.APPLICATION_JSON_VALUE)
    //TODO : andrebbe fatto un JSON ad hoc "{user:user}" al momento è sufficiente così
    public Optional<UtentiEntity> findUtente(@RequestBody TextNode user) throws SQLException, ClassNotFoundException {
        int userID = utentiService.getIdUtenti(user.asText());
        return utentiService.getUtentiById(userID);
        //TODO : recuperare password
    }
}
