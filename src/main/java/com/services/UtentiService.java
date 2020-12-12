package com.services;

import com.entities.DecoroCoppetteEntity;
import com.entities.UtentiEntity;
import com.repositories.UtentiRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Optional;

@Service
public class UtentiService {

    //TODO Nelle delete con le eccezioni ritornare un messaggio personalizzato in base a come è andato il metodo

    /**
     * Management variable for connection to the DB
     */
    @Autowired
    private UtentiRepository repository;

    //SEZIONE GET

    public Optional<UtentiEntity> getUtentiById (int id){
        return repository.findById(id);
    }

    public int getIdUtenti(String user) throws SQLException, ClassNotFoundException {
        int UserId = 0;
        System.out.println("cerco " + user.toUpperCase().trim());
    try{

        //TODO: parametrizzare e nascondere
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/GIM?serverTimezone=Europe/Rome","root","GIM_123");

        PreparedStatement stmt=con.prepareStatement("select ID_UTENTE from utenti where UPPER(username_utente) = ?");
        stmt.setString(1,user.toUpperCase().trim());
        ResultSet rs=stmt.executeQuery();
        while(rs.next()) {
            UserId = rs.getInt(1);
        }
        con.close();

    }catch(Exception e){ throw e;}
    return UserId;
}
}

