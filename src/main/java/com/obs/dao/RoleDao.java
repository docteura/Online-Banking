package com.obs.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.obs.domain.security.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
