package com.services;

import com.entities.UtentiEntity;
import com.repositories.UtentiRepository;
import com.utility.Filters;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Optional;

@Service
public class UtentiService {

    //TODO Nelle delete con le eccezioni ritornare un messaggio personalizzato in base a come è andato il metodo

    Filters filters;

    /**
     * Management variable for connection to the DB
     */
    @Autowired
    private UtentiRepository repository;

    //SEZIONE GET
    public Optional<UtentiEntity> getUtenteById (int id){
        return repository.findById(id);
    }

    public Iterable<UtentiEntity> getUtentiByIds(Iterable<Integer> ids){
        return repository.findAllById(ids);
    }

    public Iterable<UtentiEntity> getAllUtenti(){
        return repository.findAll();
    }

    //Sezione SAVE
    public UtentiEntity addUser(UtentiEntity userToAdd){
        return repository.save(userToAdd);
    }

    public Iterable<UtentiEntity> addAListOfUsers(Iterable<UtentiEntity> usersToAdd){
        return repository.saveAll(usersToAdd);
    }

    //Sezione Delete
    public boolean deleteAUser(UtentiEntity userToDelete) throws NotFoundException {
        if(repository.existsById(userToDelete.getID_UTENTE())){
            repository.delete(userToDelete);
            return true;
        }
        return false;
    }

    public void deleteAllUsers(){
        repository.deleteAll();
    }

    public void deleteAllSelectedUser(Iterable<UtentiEntity> selectedUserToDelete){
        repository.deleteAll(selectedUserToDelete);
    }

    //Query complesse
    public int getIdUtenti(String user) throws SQLException {
        /*int UserId = 0;
        System.out.println("cerco " + user.toUpperCase().trim());
        //TODO: parametrizzare e nascondere
        //Grande Edo ti seguo da quando i commenti dovevano essere esaustivi
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/GIM?serverTimezone=Europe/Rome","root","GIM_123");

        PreparedStatement stmt=con.prepareStatement("select ID_UTENTE from utenti where UPPER(username_utente) = ?");

        stmt.setString(1,user.toUpperCase().trim());
        ResultSet rs=stmt.executeQuery();
        while(rs.next()) {
            UserId = rs.getInt(1);
        }
        con.close();*/

        return filters.getIdUtente(repository.findAll(), user);
    }
}

