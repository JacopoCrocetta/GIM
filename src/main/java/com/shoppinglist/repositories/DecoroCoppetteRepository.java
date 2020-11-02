package com.shoppinglist.repositories;

import com.shoppinglist.entities.DecoroCoppetteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecoroCoppetteRepository extends CrudRepository<DecoroCoppetteEntity, Integer>{
    
}