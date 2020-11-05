package com.services;

import com.entities.MerceEntity;
import com.repositories.MerceRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerceService {

    @Autowired
    private MerceRepository repository;

    //SEZIONE GET
    public Iterable<MerceEntity> getShoppingItems() {
        return repository.findAll();
    }

    public Iterable<MerceEntity> getDecoriItemsByIDS(Iterable<Integer> ids){
        return repository.findAllById(ids);
    }

    public Optional<MerceEntity> getDecoriItemById (Int id){
        return repository.findById(id);
    }

    //SEZIONE SAVE
    public MerceEntity addMerceItem(MerceEntity item) {
        return repository.save(item);
    }
    public Iterable<MerceEntity> addMerceItems(Iterable<MerceEntity> itemsToAdd){
        return repository.saveAll(itemsToAdd); 
    }

    //SEZIONE DELETE
    public void deleteShoppingItem(Integer id) throws NotFoundException {
        if(repository.existsById(id)){
            repository.delete(repository.findById(id).get());
        }
        throw new NotFoundException("Item not found");
    }

    public void deleteDecoroItemByEntity(MerceEntity itemToDelete)throws NotFoundException {
        if(repository.existsById(itemToDelete.getId())){
            repository.delete(itemToDelete);
        }
        throw new NotFoundException("Item not found");
    }

    public void deleteAllDecoroItems(){
        repository.deleteAll();
    }

    public void deleteAllSelectedDecoroItems(Iterable<MerceEntity> itemsToDelete){
        repository.deleteAll(itemsToDelete);
    }
}
