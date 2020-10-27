package com.shoppinglist.repositories;

import com.shoppinglist.entities.CeramicheEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CeramicheRepository extends CrudRepository<CeramicheEntity, Integer> {
}
