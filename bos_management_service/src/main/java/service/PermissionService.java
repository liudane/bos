package service;

import domain.Permission;

import java.util.List;

/**
 * Created by zhaohui on 15/01/2018.
 */
public interface PermissionService {
    List<Permission> findAll();

    void save(Permission model);

}
