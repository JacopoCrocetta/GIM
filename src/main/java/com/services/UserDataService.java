package com.services;

import com.configs.ApplicationProperties;
import com.entities.UtentiEntity;
import com.repositories.UtentiRepository;
import com.utility.Filters;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

@Service
public class UserDataService {
    @Autowired
    ApplicationProperties prop;

    public String getIdUtenti(String user) throws SQLException, IOException {
        String psId = null;
        //TODO: WIP parametrizzare e nascondere
            Connection con = DriverManager.getConnection(prop.getUrl(), "root", "GIM_123");

            PreparedStatement stmt = con.prepareStatement("select p.ps from utenti u, password p where username_utente = ?");

            stmt.setString(1, user.trim());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                psId = rs.getString(1);
            }
            con.close();
        return psId;
        //return filters.getIdUtente(repository.findAll(), user);
    }
}

