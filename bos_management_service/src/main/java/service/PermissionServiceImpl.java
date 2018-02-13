package service;

import dao.PermissionDao;
import domain.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhaohui on 15/01/2018.
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {


    @Autowired
    private PermissionDao permissionDao;



    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public void save(Permission model) {

        permissionDao.save(model);
    }


}
