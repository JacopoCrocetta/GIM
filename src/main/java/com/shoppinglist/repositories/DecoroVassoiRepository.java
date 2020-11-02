package com.shoppinglist.repositories;

import com.shoppinglist.entities.DecoroVassoiEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecoroVassoiRepository extends CrudRepository<DecoroVassoiEntity, Integer>{
    
}