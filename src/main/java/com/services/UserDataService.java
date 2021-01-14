package com.services;

import com.configs.DatasourceProperties;
import com.configs.EmailProperties;
import com.configs.StandardMessagesProperties;
import com.entities.SecurityEntity;
import com.entities.UserCompleteDataEntity;
import com.entities.UserDataEntity;
import com.utility.Filters;
import com.utility.PasswordGenerator;
import net.bytebuddy.implementation.bytecode.Throw;
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
    DatasourceProperties dsProp;
    @Autowired
    EmailProperties emProp;
    @Autowired
    StandardMessagesProperties msgProp;

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

    public Boolean insertNewUser(UserCompleteDataEntity userDataToInsert) throws SQLException {
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
        con.commit();
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
        int tentativesfromDB = getTentativesfromDB(userData.getUSER());
        System.out.println(tentativesfromDB);
        boolean isAccessLocked = Filters.maxTentatives <= tentativesfromDB;
        if(isAccessLocked) {
            destroyPassword(userData.getUSER());
            if(Filters.maxTentatives == tentativesfromDB)
            sendEmail(userData);
        }
        userDataChecks.setACCESS_LOCKED(isAccessLocked);
        return userDataChecks;
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

    //TODO: parametrizzare l'invio della mail in caso di account loccato per password errata o password_salted errata
    public void sendEmail(UserDataEntity userData) throws SQLException {
        String to = retrieveUserMail(userData.getUSER());
        String sub ="[GIM] Messaggio importante relativo al tuo account";

        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", emProp.getHost());
        props.put("mail.smtp.socketFactory.port", emProp.getSocketFactoryport());
        props.put("mail.smtp.socketFactory.class", emProp.getSocketFactoryclass());
        props.put("mail.smtp.auth", emProp.getAuth());
        props.put("mail.smtp.port", emProp.getPort());
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emProp.getUser(),emProp.getPwd());
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msgProp.getAccountlocked());
            //send message
            Transport.send(message);
            System.out.println("email type \"AccountLocked\" sent successfully");
        } catch (MessagingException e) {throw new RuntimeException(e);}

    }

    private String retrieveUserMail(String user) throws SQLException {
        String psEmail = null;
        Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.retrieveUserMail);
        stmt.setString(1, user.trim());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            psEmail = rs.getString(1 );;
        }
        con.close();
        return psEmail;
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

    public SecurityEntity findUserWithSaltedPwd(UserDataEntity userData) throws SQLException {
        SecurityEntity userDataChecks = new SecurityEntity();
        boolean isAccessGranted = userData.getPWD().equals(getSaltedPWDfromDB(userData.getUSER()));
        updateSaltedTentatives(userData, isAccessGranted);
        userDataChecks.setACCESS_GRANTED(isAccessGranted);
        int tentativesfromDB = getSaltedTentativesfromDB(userData.getUSER());
        System.out.println(tentativesfromDB);
        boolean isAccessLocked = Filters.maxTentatives <= tentativesfromDB;
        if(isAccessLocked) {
            destroySaltedPassword(userData.getUSER());
            if(Filters.maxTentatives == tentativesfromDB)
                sendEmail(userData);
        }
        userDataChecks.setACCESS_LOCKED(isAccessLocked);
        return userDataChecks;
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

    public boolean checkUserisAlreadyPresent(UserCompleteDataEntity userDataToInsert) throws SQLException {
        Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.checkUser);
        stmt.setString(1, userDataToInsert.getUSER());
        ResultSet rs = stmt.executeQuery();
        int found = 0;
        while (rs.next()) {
            found = rs.getInt(1 );
        }
        con.close();
        return (found > 0);
    }
}


