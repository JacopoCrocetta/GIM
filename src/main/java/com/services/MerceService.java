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

    public Iterable<MerceEntity> getShoppingItems() {
        return repository.findAll();
    }

    public MerceEntity addShoppingItem(MerceEntity item) {
        return repository.save(item);
    }

    public void deleteShoppingItem(Integer id) throws NotFoundException {
        Optional<MerceEntity> itemToDelete = repository.findById(id);
        if(itemToDelete.isEmpty()){
            throw new NotFoundException("Item not found");
        }

        repository.delete(itemToDelete.get());
    }
}
