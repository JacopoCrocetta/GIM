package com.services;

import com.configs.ApplicationProperties;
import com.entities.UserCompleteDataEntity;
import com.utility.Filters;
import com.utility.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.*;

//TODO : Ottimizzare

@Service
public class UserDataService {
    @Autowired
    ApplicationProperties prop;

    public String getPWDfromDB(String userData) throws SQLException {
        String psId = null;
            Connection con = DriverManager.getConnection(prop.getUrl(), prop.getUsername(), prop.getPassword());
            PreparedStatement stmt = con.prepareStatement(Filters.retrievePasswordfromUser);
            stmt.setString(1, userData.trim());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                psId = rs.getString(1);
            }
            con.close();
        return psId;
    }

    public Boolean insertNewUser(UserCompleteDataEntity userDataToInsert) throws SQLException {
        Connection con = DriverManager.getConnection(prop.getUrl(), prop.getUsername(), prop.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.insertNewUser);
        stmt.setString(1, userDataToInsert.getUSER().trim());
        stmt.setString(2, userDataToInsert.getNAME().trim());
        stmt.setString(3, userDataToInsert.getSURNAME().trim());
        int user = stmt.executeUpdate();
        PreparedStatement stmt2 = con.prepareStatement(Filters.insertNewPwd);
        stmt2.setInt(1, retrieveLastId(userDataToInsert.getUSER().trim(), con));
        stmt2.setString(2, userDataToInsert.getPWD().trim());
        stmt2.setString(3, PasswordGenerator.getNextSalt().toString());
        int pwd = stmt2.executeUpdate();
        con.close();
        return user+pwd == 2;
    }

    private int retrieveLastId(String userToSearch, Connection con) throws SQLException {
        int psId = 0;
        PreparedStatement stmt = con.prepareStatement(Filters.retrieveidUser);
        stmt.setString(1, userToSearch.trim());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            psId = rs.getInt(1);
        }
        return psId;
    }
}

