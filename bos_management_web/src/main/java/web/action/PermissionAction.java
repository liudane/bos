package web.action;

import domain.Permission;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import service.PermissionService;

import java.util.List;

/**
 * Created by zhaohui on 15/01/2018.
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Results({
        @Result(name="list", type="redirect", location="/pages/system/permission.jsp")
})
public class PermissionAction extends BaseAction<Permission>{

    @Autowired
    private PermissionService permissionService;




    /**
     * 查找所有权限数据
     * @return
     * @throws Exception
     */
    @Action("permissionAction_findAll")
    public String findAll() throws Exception {

        List<Permission> list = permissionService.findAll();
        java2Json(list, new String[]{"roles"});
        return NONE;
    }


    /**
     * 添加权限
     * @return
     * @throws Exception
     */
    @Action("permissionAction_save")
    public String save() throws Exception {
        permissionService.save(model);

        return "list";
    }



}
