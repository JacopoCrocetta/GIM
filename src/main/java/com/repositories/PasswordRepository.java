package com.repositories;

import com.entities.PasswordEntity;
import org.springframework.data.repository.CrudRepository;

public interface PasswordRepository extends CrudRepository<PasswordEntity, Integer> {
}
