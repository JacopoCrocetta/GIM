package com.services;

import com.entities.CeramicheEntity;
import com.repositories.CeramicheRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CeramicheService {

    @Autowired
    private CeramicheRepository repository;

    //SEZIONE GET
    public Iterable<CeramicheEntity> getAllCeramicheItems() {
        return repository.findAll();
    }

    public Iterable<CeramicheEntity> getCeramicheItemsByIDS(Iterable<Integer> ids){
        return repository.findAllById(ids);
    }

    public Optional<CeramicheEntity> getCeramicheItemById (int id){
        return repository.findById(id);
    }

    //SEZIONE SAVE
    public CeramicheEntity addCeramicheItem(CeramicheEntity item) {
        return repository.save(item);
    }

    public Iterable<CeramicheEntity> addCeramicheItems(Iterable<CeramicheEntity> itemsToAdd){
        return repository.saveAll(itemsToAdd); 
    }

    //SEZIONE DELETE
    public void deleteCeramicaItemById(Integer id) throws NotFoundException {
        if(repository.existsById(id)){
            repository.delete(repository.findById(id).get());
        }
        throw new NotFoundException("Item not found");
    }

    public void deleteCeramicheItemByEntity(CeramicheEntity itemToDelete)throws NotFoundException {
        if(repository.existsById(itemToDelete.getId())){
            repository.delete(itemToDelete);
        }
        throw new NotFoundException("Item not found");
    }

    public void deleteAllCeramicheItems(){
        repository.deleteAll();
    }

    public void deleteAllSelectedCeramicheItems(Iterable<CeramicheEntity> itemsToDelete){
        repository.deleteAll(itemsToDelete);
    }
}
