package com.repositories;

import com.entities.DecoroCoppetteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecoroCoppetteRepository extends CrudRepository<DecoroCoppetteEntity, Integer>{
    
}