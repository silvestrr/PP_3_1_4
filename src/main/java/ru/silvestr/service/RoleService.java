package ru.silvestr.service;

import ru.silvestr.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> findAllRole();

    Set<Role> findByIdRoles(List<Long> roles);
}
