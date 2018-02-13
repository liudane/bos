package web.action;

import domain.Menu;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import service.MenuService;

import java.util.List;

/**
 * Created by zhaohui on 14/01/2018.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name = "list", type = "redirect", location = "/pages/system/menu.jsp")
})
public class MenuAction extends BaseAction<Menu>{

    @Autowired
    private MenuService menuService;


    /**
     * 从数据库查询所有菜单数据，并回显给jsp页面,treegrid要求数据格式：json数组
     * @return
     * @throws Exception
     */
    @Action("menuAction_findAll")
    public String findAll() throws Exception {
        List<Menu> list = menuService.findAll();
        this.java2Json(list, new String[]{"parentMenu", "roles","childrenMenus","childrenMenus"});//parentMenu引起死循环，roles引起nosession
        return NONE;
    }


    /**
     * 查询所有的菜单数据，返回ztree要求简单的json数据格式
     * 简单格式：[{id:1,name="一级菜单",pId:0},{id:2,name="二级菜单",pId:3}]
     * 标准格式：[{id:1,name="一级菜单",children:[{id:2,name="二级菜单",}]}]
     * @return
     * @throws Exception
     */
    @Action("menuAction_findBySimple")
    public String findBySimple() throws Exception {
        List<Menu> list = menuService.findBySimple();
        this.java2Json(list, new String[]{"parentMenu", "roles", "children", "childrenMenus"});
        return NONE;
    }


    @Action("menuAction_save")
    public String save() throws Exception {
        menuService.save(model);
        return "list";
    }
}
































