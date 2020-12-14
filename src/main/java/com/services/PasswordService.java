package com.services;

import com.entities.PasswordEntity;
import com.repositories.PasswordRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordService {
    /**
     * Management variable for connection to the DB
     */
    @Autowired
    private PasswordRepository repository;

    ///SEZIONE GET
    public Optional<PasswordEntity> getUtenteById (int id){
        return repository.findById(id);
    }

    public Iterable<PasswordEntity> getUtentiByIds(Iterable<Integer> ids){
        return repository.findAllById(ids);
    }

    public Iterable<PasswordEntity> getAllUtenti(){
        return repository.findAll();
    }

    //Sezione SAVE
    public PasswordEntity addUser(PasswordEntity userToAdd){
        return repository.save(userToAdd);
    }

    public Iterable<PasswordEntity> addAListOfUsers(Iterable<PasswordEntity> usersToAdd){
        return repository.saveAll(usersToAdd);
    }

    //Sezione Delete
    public boolean deleteAUser(PasswordEntity userToDelete) throws NotFoundException {
        if(repository.existsById(userToDelete.getId())){
            repository.delete(userToDelete);
            return true;
        }
        return false;
    }

    public void deleteAllUsers(){
        repository.deleteAll();
    }

    public void deleteAllSelectedUser(Iterable<PasswordEntity> selectedUserToDelete){
        repository.deleteAll(selectedUserToDelete);
    }
}
