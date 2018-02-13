package web.action;

import domain.Role;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import service.RoleService;

import java.util.List;

/**
 * Created by zhaohui on 15/01/2018.
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Results({
        @Result(name="list", type="redirect", location="/pages/system/role.jsp")
})
public class RoleAction extends BaseAction<Role>{


    @Autowired
    private RoleService roleService;

    //接收页面提交的菜单ID  权限ID
    private String menuIds;
    private Integer[] permissionIds;
    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
    public void setPermissionIds(Integer[] permissionIds) {
        this.permissionIds = permissionIds;
    }


    /**
     * 保存角色，关联权限，关联菜单
     * @return
     * @throws Exception
     */
    @Action("roleAction_save")
    public String save() throws Exception {
        roleService.save(model, menuIds, permissionIds);
        return "list";
    }


    @Action("roleAction_findAll")
    public String findAll() throws Exception {
        List<Role> list = roleService.findAll();
        this.java2Json(list, new String[]{"menus","permissions","users"});
        return NONE;
    }
}
