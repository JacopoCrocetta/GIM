package com.repositories;

import com.entities.DecoroVassoiEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecoroVassoiRepository extends CrudRepository<DecoroVassoiEntity, Integer>{
    
}