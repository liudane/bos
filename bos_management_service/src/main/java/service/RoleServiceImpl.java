package service;

import dao.PermissionDao;
import dao.RoleDao;
import domain.Menu;
import domain.Permission;
import domain.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhaohui on 15/01/2018.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public void save(Role model, String menuIds, Integer[] permissionIds) {

        roleDao.save(model);

        if(permissionIds != null && permissionIds.length>0){
            for (Integer permissionId : permissionIds) {
                //创建持久态权限对象或者new托管态对象setId
                Permission permission = permissionDao.findOne(permissionId);
                model.getPermissions().add(permission);//角色关联权限，向中间表t_role_permission角色权限关系表添加记录
            }
        }
        if(StringUtils.isNotBlank(menuIds)){
            String[] strings = menuIds.split(",");
            for(String menuId: strings){
                //角色关联菜单
                Menu menu = new Menu();
                menu.setId(Integer.parseInt(menuId));//角色关联菜单，向中间表t_role_menu角色菜单关系表添加记录
                model.getMenus().add(menu);

            }
        }


    }
}
