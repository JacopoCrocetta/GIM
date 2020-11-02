package com.shoppinglist.services;

import com.shoppinglist.entities.DecoroVassoiEntity;
import com.shoppinglist.repositories.DecoroVassoiRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DecoroCoppetteService {
    @Autowired
    private DecoroVassoiRepository repository;

    public Iterable<DecoroVassoiEntity> getShoppingItems() {
        return repository.findAll();
    }

    public DecoroVassoiEntity addShoppingItem(DecoroVassoiEntity item) {
        return repository.save(item);
    }

    public void deleteShoppingItem(Integer id) throws NotFoundException {
        Optional<DecoroVassoiEntity> itemToDelete = repository.findById(id);
        if(itemToDelete.isEmpty()){
           throw new NotFoundException("Item not found");
        }

        repository.delete(itemToDelete.get());
    }
}