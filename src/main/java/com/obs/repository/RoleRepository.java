package com.obs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.obs.security.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
	Role findByName(String name);
}
