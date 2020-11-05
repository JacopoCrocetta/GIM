package com.services;

import com.entities.DecoroVassoiEntity;
import com.repositories.DecoroVassoiRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DecoroVassoiService {
    @Autowired
    private DecoroVassoiRepository repository;

    //SEZIONE GET
    public Iterable<DecoroVassoiEntity> getShoppingItems() {
        return repository.findAll();
    }

    public Iterable<DecoroVassoiEntity> getDecoriItemsByIDS(Iterable<Integer> ids){
        return repository.findAllById(ids);
    }

    public Optional<DecoroVassoiEntity> getDecoriItemById (Int id){
        return repository.findById(id);
    }

    //SEZIONE SAVE
    public DecoroVassoiEntity addDecoroItem(DecoroVassoiEntity item) {
        return repository.save(item);
    }

    public Iterable<DecoroVassoiEntity> addDecoriItems(Iterable<DecoroVassoiEntity> itemsToAdd){
        return repository.saveAll(itemsToAdd); 
    }

    //SEZIONE DELETE
    public void deleteShoppingItem(Integer id) throws NotFoundException {
        if(repository.existsById(id)){
            repository.delete(repository.findById(id).get());
        }
        throw new NotFoundException("Item not found");
    }

    public void deleteDecoroItemByEntity(DecoroVassoiEntity itemToDelete)throws NotFoundException {
        if(repository.existsById(itemToDelete.getId())){
            repository.delete(itemToDelete);
        }
        throw new NotFoundException("Item not found");
    }

    public void deleteAllDecoroItems(){
        repository.deleteAll();
    }

    public void deleteAllSelectedDecoroItems(Iterable<DecoroVassoiEntity> itemsToDelete){
        repository.deleteAll(itemsToDelete);
    }
}