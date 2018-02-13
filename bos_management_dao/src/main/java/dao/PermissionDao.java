package dao;

import domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhaohui on 15/01/2018.
 */
public interface PermissionDao extends JpaRepository<Permission, Integer>{


}
