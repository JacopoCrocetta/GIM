package com.accenture.shoppinglist.repositories;

import com.accenture.shoppinglist.entities.CeramicheEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CeramicheRepository extends CrudRepository<CeramicheEntity, Integer> {
}
