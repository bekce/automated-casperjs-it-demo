package com.sebworks.automated.it.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sebworks.automated.it.entity.User;

/**
 * @author Selim Eren Bekçe
 */
public interface UserRepository extends MongoRepository<User, String> {
}
