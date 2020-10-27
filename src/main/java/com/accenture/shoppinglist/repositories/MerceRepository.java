package com.accenture.shoppinglist.repositories;

import com.accenture.shoppinglist.entities.MerceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerceRepository extends CrudRepository<MerceEntity, Integer> {
}
