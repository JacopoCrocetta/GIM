package com.shoppinglist.services;

import com.shoppinglist.entities.CeramicheEntity;
import com.shoppinglist.repositories.CeramicheRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CeramicheService {

    @Autowired
    private CeramicheRepository repository;

    public Iterable<CeramicheEntity> getShoppingItems() {
        return repository.findAll();
    }

    public CeramicheEntity addShoppingItem(CeramicheEntity item) {
        return repository.save(item);
    }

    public void deleteShoppingItem(Integer id) throws NotFoundException {
        Optional<CeramicheEntity> itemToDelete = repository.findById(id);
        if(itemToDelete.isEmpty()){
           throw new NotFoundException("Item not found");
        }

        repository.delete(itemToDelete.get());
    }
}
