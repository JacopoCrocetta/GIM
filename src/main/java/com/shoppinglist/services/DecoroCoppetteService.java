package com.shoppinglist.services;

import com.shoppinglist.entities.DecoroCoppetteEntity;
import com.shoppinglist.repositories.DecoroCoppetteRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DecoroCoppetteService {
    @Autowired
    private DecoroCoppetteRepository repository;

    public Iterable<DecoroCoppetteEntity> getShoppingItems() {
        return repository.findAll();
    }

    public DecoroCoppetteEntity addShoppingItem(DecoroCoppetteEntity item) {
        return repository.save(item);
    }

    public void deleteShoppingItem(Integer id) throws NotFoundException {
        Optional<DecoroCoppetteEntity> itemToDelete = repository.findById(id);
        if(itemToDelete.isEmpty()){
           throw new NotFoundException("Item not found");
        }

        repository.delete(itemToDelete.get());
    }
}