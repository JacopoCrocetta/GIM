package com.services;

import com.entities.MerceEntity;
import com.repositories.MerceRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 * @author Jacopo Crocetta
 * @author Edoardo Laurini
 */
@Service
public class MerceService {

    //TODO Nelle delete con le eccezioni ritornare un messaggio personalizzato in base a come è andato il metodo

    /**
     * Management variable for connection to the DB
     */
    @Autowired
    private MerceRepository repository;

    //SEZIONE GET
    /**
     *
     * <p>
     *     Method that returns all the fields that are in DB
     * </p>
     * @return All records saved in the table
     */
    public Iterable<MerceEntity> getAllMerceItems() {
        return repository.findAll();
    }

    /**
     *
     * <p>
     *     Method that given a list of ids returns the saved records
     *     of specific ids
     * </p>
     * @param ids The ids to look for in DB
     * @return the exact records with the ids passed in input
     */
    public Iterable<MerceEntity> getMerceItemsByIDS(Iterable<Integer> ids){
        return repository.findAllById(ids);
    }

    /**
     *
     * <p>
     *     Method that returns the item searched for by id
     * </p>
     * @param id The id to look for in DB
     * @return The exact record with the id passed in input
     */
    public Optional<MerceEntity> getMerceItemById (int id){
        return repository.findById(id);
    }

    //SEZIONE SAVE
    /**
     *
     *<p>
     *     Method that saves the item passed in input
     * </p>
     * @param item Item to save
     * @return Item saved
     */
    public MerceEntity addMerceItem(MerceEntity item) {
        return repository.save(item);
    }

    /**
     *
     *<p>
     *     Method that saves the item passed in input
     * </p>
     * @param itemsToAdd Items to save
     * @return The list of the item saved
     */
    public Iterable<MerceEntity> addMerceItems(Iterable<MerceEntity> itemsToAdd){
        return repository.saveAll(itemsToAdd); 
    }

    //SEZIONE DELETE
    /**
     *
     * <p>
     *     Method that deletes an item of the record's id passed in input
     * </p>
     * @param id record's id to delete
     * @exception NotFoundException In case the id is not in the DB
     */
    public void deleteMerceItemById(Integer id) throws NotFoundException, NullPointerException  {
        if(repository.existsById(id) && repository.findById(id).isPresent()){
            repository.delete(repository.findById(id).get());
        }
        throw new NotFoundException("Item not found");
    }

    /**
     *
     * <p>
     *     Method that deletes an item passed in input
     * </p>
     * @param itemToDelete The CeramicheEntity to delete
     * @exception NotFoundException In case the item's id is not in the DB
     * @exception NullPointerException In case the item is not set
     */
    public void deleteMerceItemByEntity(MerceEntity itemToDelete)throws NotFoundException, NullPointerException {
        if (itemToDelete == null){
            throw new NullPointerException("Item is not set");
        }
        if(repository.existsById(itemToDelete.getId())){
            repository.delete(itemToDelete);
        }
        throw new NotFoundException("Item not found");
    }

    /**
     *
     * <p>
     *     Method that deletes al record in the Database
     * </p>
     */
    public void deleteAllMerceItems(){
        repository.deleteAll();
    }

    /**
     *
     * <p>
     *     Method that delete a list of item
     * </p>
     * @param itemsToDelete records id to delete
     */
    public void deleteAllSelectedMerceItems(Iterable<MerceEntity> itemsToDelete){
        repository.deleteAll(itemsToDelete);
    }
}
