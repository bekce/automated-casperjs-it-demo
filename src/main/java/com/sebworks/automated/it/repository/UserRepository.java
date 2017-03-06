package com.sebworks.automated.it.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sebworks.automated.it.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
}
