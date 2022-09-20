package com.hyvercode.springday.repository;

import com.hyvercode.springday.model.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
  Optional<Role> findRoleByName(String name);

 Role findByRoleId(String roleId);
}
