package com.utility;

import com.configs.DatasourceProperties;
import com.configs.EmailProperties;
import com.configs.StandardMessagesProperties;
import com.entities.UserDataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.Properties;

//TODO : Optimizer
@Component
public class MailGenerator {

    public void sendEmail(UserDataEntity userData, String msgType, StandardMessagesProperties msgProp, EmailProperties emProp, DatasourceProperties dsProp ) throws SQLException {

        String to = retrieveUserMail(userData.getUSER(), dsProp);

        String text;
        String subject;
        switch(msgType) {
            case "accountLocked":
                subject = msgProp.getSub();
                text = msgProp.getMsgStandard();
                break;
            case "accountLockedDef":
                subject = msgProp.getSub();
                text = msgProp.getMsgDefinitive();
                break;
            default:
                throw new IllegalStateException("Unexpected value for msgType: " + msgType);
        }

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
            message.setSubject(subject);
            message.setText(text);
            //send message
            Transport.send(message);
            System.out.println("email type \""+msgType+"\" sent successfully");
        } catch (MessagingException e) {throw new RuntimeException(e);}

    }

    private String retrieveUserMail(String user, DatasourceProperties dsProp) throws SQLException {
        String psEmail = null;
        Connection con = DriverManager.getConnection(dsProp.getUrl(), dsProp.getUsername(), dsProp.getPassword());
        PreparedStatement stmt = con.prepareStatement(Filters.retrieveUserMail);
        stmt.setString(1, user.trim());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            psEmail = rs.getString(1 );
        }
        con.close();
        return psEmail;
    }
}
