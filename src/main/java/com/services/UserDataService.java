package com.services;

import com.configs.DatasourceProperties;
import com.configs.EmailProperties;
import com.configs.StandardMessagesProperties;
import com.entities.SecurityEntity;
import com.entities.SecurityResetEntity;
import com.entities.UserCompleteDataEntity;
import com.entities.UserDataEntity;
import com.utility.Filters;
import com.utility.MailGenerator;
import com.utility.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.sql.*;
import java.util.Properties;

//TODO : Ottimizzare
@Service
public class UserDataService {
    @Autowired
    EmailProperties emProp;
    @Autowired
    StandardMessagesProperties msgProp;
    @Autowired
    DatasourceProperties dsProp;

    public String getPWDfromDB(String userData) throws SQLException {
        String psId = null;
            Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
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
        Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.retrieveTentativesfromUser);
        stmt.setString(1, userData.trim());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            psTentatives = rs.getInt(1);
        }
        con.close();
        return psTentatives;
    }

    public SecurityEntity insertNewUser(UserCompleteDataEntity userDataToInsert) throws SQLException {
        SecurityEntity userDataInsert = new SecurityEntity();
        Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.insertNewUser);
        stmt.setString(1, userDataToInsert.getUSER().trim());
        stmt.setString(2, userDataToInsert.getEMAIL().trim());
        stmt.setString(3, userDataToInsert.getNAME().trim());
        stmt.setString(4, userDataToInsert.getSURNAME().trim());
        int user = stmt.executeUpdate();
        PreparedStatement stmt2 = con.prepareStatement(Filters.insertNewPwd);
        stmt2.setInt(1, retrieveLastId(userDataToInsert.getUSER().trim(), con));
        stmt2.setString(2, userDataToInsert.getPWD().trim());
        stmt2.setString(3, PasswordGenerator.getNextSalt().toString());
        int pwd = stmt2.executeUpdate();
        if(user+pwd == 2) {
            userDataInsert.setACCESS_GRANTED(true);
            userDataInsert.setACCESS_LOCKED(false);
            userDataInsert.setACCESS_PWD_ERR(false);
        }
        return userDataInsert;
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
        userDataChecks.setACCESS_PWD_ERR(false);
        boolean isAccessGranted = userData.getPWD().equals(getPWDfromDB(userData.getUSER()));
        updateTentatives(userData, isAccessGranted);
        userDataChecks.setACCESS_GRANTED(isAccessGranted);
        int tentativesfromDB = getTentativesfromDB(userData.getUSER());
        if (tentativesfromDB > 0) {
            System.err.println("Attenzione: utenza " + userData.getUSER() + " tentativi di accesso " +
                    "errati: " + tentativesfromDB + " su " + Filters.maxTentatives);
            userDataChecks.setACCESS_PWD_ERR(true);
        }
        boolean isAccessLocked = Filters.maxTentatives <= tentativesfromDB;
        if(isAccessLocked) {
            destroyPassword(userData.getUSER());
            if(Filters.maxTentatives == tentativesfromDB) {
                String msgType = "accountLocked";
                MailGenerator mailGenerator = new MailGenerator();
                mailGenerator.sendEmail(userData, msgType, msgProp, emProp, dsProp);
            }
        }
        userDataChecks.setACCESS_LOCKED(isAccessLocked);
        return userDataChecks;
    }

    public SecurityResetEntity findUserWithSaltedPwd(UserDataEntity userData) throws SQLException {
        SecurityResetEntity userDataChecks = new SecurityResetEntity();
        userDataChecks.setACCESS_PWD_ERR(false);
        boolean isAccessGranted = userData.getPWD().equals(getSaltedPWDfromDB(userData.getUSER()));
        updateSaltedTentatives(userData, isAccessGranted);
        userDataChecks.setACCESS_GRANTED(isAccessGranted);
        int tentativesfromDB = getSaltedTentativesfromDB(userData.getUSER());
        if (tentativesfromDB > 0) {
            System.err.println("Attenzione: utenza " + userData.getUSER() + " tentativi di accesso " +
                    "errati: " + tentativesfromDB + " su " + Filters.maxTentatives);
            System.err.println("Attenzione: Password salted ERRATA!");
            userDataChecks.setACCESS_PWD_ERR(true);
        }
        boolean isAccessLocked = Filters.maxTentatives <= tentativesfromDB;
        if(isAccessLocked) {
            destroySaltedPassword(userData.getUSER());
            if(Filters.maxTentatives == tentativesfromDB) {
                String msgType = "accountLockedDef";
                MailGenerator mailGenerator = new MailGenerator();
                mailGenerator.sendEmail(userData, msgType, msgProp, emProp, dsProp);            }
        }
        userDataChecks.setACCESS_LOCKED(isAccessLocked);
        if(isAccessGranted) {
            String newPwd = PasswordGenerator.generateRandomPassword(8);
            updatePassword(newPwd, userData.getUSER());
            userDataChecks.setACCESS_PWD_NEW(newPwd);
        }
        return userDataChecks;
    }

    private void updatePassword(String newPwd, String user) throws SQLException {
        int psId = 0;
        Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.retrieveidUser);
        stmt.setString(1, user.trim());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            psId = rs.getInt(1);
        }
        PreparedStatement stmt2 = con.prepareStatement(Filters.updatePassword);
        stmt2.setString(1, newPwd);
        stmt2.setInt(2, psId);
        int done = stmt2.executeUpdate();
        System.out.println("generata nuova password per utenza: " + user);
        con.close();
    }

    private void destroyPassword(String user) throws SQLException {
        int psId = 0;
        Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
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
    }

    private void updateTentatives(UserDataEntity userData, Boolean isAccessGranted) throws SQLException {
        int psId = 0;
        Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
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

    private void destroySaltedPassword(String user) throws SQLException {
        int psId = 0;
        Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.retrieveidUser);
        stmt.setString(1, user.trim());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            psId = rs.getInt(1);
        }
        PreparedStatement stmt2;
        stmt2 = con.prepareStatement(Filters.destroyUserSaltedPassword);
        stmt2.setInt(1, psId);
        int done = stmt2.executeUpdate();
        con.close();
    }

    private int getSaltedTentativesfromDB(String userData) throws SQLException {
        int psTentatives = 0;
        Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.retrieveSaltedTentativesfromUser);
        stmt.setString(1, userData.trim());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            psTentatives = rs.getInt(1);
        }
        con.close();
        return psTentatives;
    }

    private void updateSaltedTentatives(UserDataEntity userData, boolean isAccessGranted) throws SQLException {
        int psId = 0;
        Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.retrieveidUser);
        stmt.setString(1, userData.getUSER().trim());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            psId = rs.getInt(1);
        }
        PreparedStatement stmt2;
        if(isAccessGranted){
            stmt2 = con.prepareStatement(Filters.resetSaltedTentatives);
        }else {
            stmt2 = con.prepareStatement(Filters.updateSaltedTentatives);
        }
        stmt2.setInt(1, psId);
        int user = stmt2.executeUpdate();
        con.close();
    }

    private String getSaltedPWDfromDB(String userData) throws SQLException {
        String psId = null;
        Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.retrieveSaltedPasswordfromUser);
        stmt.setString(1, userData.trim());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            psId = rs.getString(1);
        }
        con.close();
        return psId;
    }

    public boolean checkUserisAlreadyPresent(String username) throws SQLException {
        Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.checkUser);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        int found = 0;
        while (rs.next()) {
            found = rs.getInt(1 );
        }
        con.close();
        return (found > 0);
    }

    public SecurityResetEntity changePassword(UserDataEntity userData) throws SQLException {
        SecurityResetEntity userDataChecks = new SecurityResetEntity();
        updatePassword(userData.getPWD(), userData.getUSER());
        userDataChecks.setACCESS_GRANTED(true);
        userDataChecks.setACCESS_PWD_NEW(userData.getPWD());
        return userDataChecks;
    }
}


