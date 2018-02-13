package service;

import domain.Role;

import java.util.List;

/**
 * Created by zhaohui on 15/01/2018.
 */
public interface RoleService {
    List<Role> findAll();

    void save(Role model, String menuIds, Integer[] permissionIds);
}
