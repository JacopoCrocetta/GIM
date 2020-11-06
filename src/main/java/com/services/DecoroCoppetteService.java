package com.services;

import com.entities.DecoroCoppetteEntity;
import com.repositories.DecoroCoppetteRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DecoroCoppetteService {
    @Autowired
    private DecoroCoppetteRepository repository;

    //SEZIONE GET
    public Iterable<DecoroCoppetteEntity> getAllDecoriItems() {
        return repository.findAll();
    }

    public Iterable<DecoroCoppetteEntity> getDecoriItemsByIDS(Iterable<Integer> ids){
        return repository.findAllById(ids);
    }

    public Optional<DecoroCoppetteEntity> getDecoriItemById (int id){
        return repository.findById(id);
    }

    //SEZIONE SAVE
    public DecoroCoppetteEntity addDecoroItem(DecoroCoppetteEntity item) {
        return repository.save(item);
    }

    public Iterable<DecoroCoppetteEntity> addDecoriItems(Iterable<DecoroCoppetteEntity> itemsToAdd){
        return repository.saveAll(itemsToAdd); 
    }

    //SEZIONE DELETE
    public void deleteDecoroItemById(Integer id) throws NotFoundException {
        if(repository.existsById(id)){
            repository.delete(repository.findById(id).get());
        }
        throw new NotFoundException("Item not found");
    }

    public void deleteDecoroItemByEntity(DecoroCoppetteEntity itemToDelete)throws NotFoundException {
        if(repository.existsById(itemToDelete.getId())){
            repository.delete(itemToDelete);
        }
        throw new NotFoundException("Item not found");
    }

    public void deleteAllDecoroItems(){
        repository.deleteAll();
    }

    public void deleteAllSelectedDecoroItems(Iterable<DecoroCoppetteEntity> itemsToDelete){
        repository.deleteAll(itemsToDelete);
    }
}