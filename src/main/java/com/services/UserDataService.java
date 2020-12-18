package com.services;

import com.configs.ApplicationProperties;
import com.entities.SecurityEntity;
import com.entities.UserCompleteDataEntity;
import com.entities.UserDataEntity;
import com.utility.Filters;
import com.utility.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public int getTentativesfromDB(String userData) throws SQLException {
        int psTentatives = 0;
        Connection con = DriverManager.getConnection(prop.getUrl(), prop.getUsername(), prop.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.retrieveTentativesfromUser);
        stmt.setString(1, userData.trim());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            psTentatives = rs.getInt(1);
        }
        con.close();
        return psTentatives;
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

    public SecurityEntity findUser(UserDataEntity userData) throws SQLException {
        SecurityEntity userDataChecks = new SecurityEntity();
        boolean isAccessGranted = userData.getPWD().equals(getPWDfromDB(userData.getUSER()));
        updateTentatives(userData, isAccessGranted);
        userDataChecks.setACCESS_GRANTED(isAccessGranted);
        boolean isAccessLocked = Filters.maxTentatives < (getTentativesfromDB(userData.getUSER()));
        if(isAccessLocked)
            destroyPassword(userData.getUSER());
        userDataChecks.setACCESS_LOCKED(isAccessLocked);
        return userDataChecks;
    }

    private void destroyPassword(String user) throws SQLException {
        int psId = 0;
        Connection con = DriverManager.getConnection(prop.getUrl(), prop.getUsername(), prop.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.retrieveidUser);
        stmt.setString(1, user.trim());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            psId = rs.getInt(1);
        }
        PreparedStatement stmt2;
        stmt2 = con.prepareStatement(Filters.destroyUserPassword);
        stmt2.setInt(1, psId);
        int done = stmt2.executeUpdate();
        con.close();
        //TODO: inviare mail
    }

    private void updateTentatives(UserDataEntity userData, Boolean isAccessGranted) throws SQLException {
        int psId = 0;
        Connection con = DriverManager.getConnection(prop.getUrl(), prop.getUsername(), prop.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.retrieveidUser);
        stmt.setString(1, userData.getUSER().trim());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            psId = rs.getInt(1);
        }
        PreparedStatement stmt2;
        if(isAccessGranted){
            stmt2 = con.prepareStatement(Filters.resetTentatives);
        }else {
            stmt2 = con.prepareStatement(Filters.updateTentatives);
        }
        stmt2.setInt(1, psId);
        int user = stmt2.executeUpdate();
        con.close();
    }
}

