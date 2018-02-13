package web.action;

import domain.Vehicle;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import service.VehicleService;

/**
 * Created by zhaohui on 22/01/2018.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name="list", type="redirect", location="/pages/base/vehicle.jsp")
})
public class VehicleAction extends BaseAction<Vehicle>{

    @Autowired
    private VehicleService vehicleService;


    /**
     * 分页显示
     * @return
     * @throws Exception
     */
    @Action("vehicleAction_pageQuery")
    public String pageQuery() throws Exception {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Vehicle> page = vehicleService.pageQuery(pageable);
        this.java2Json(page, new String[]{});
        return NONE;
    }


    /**
     * 保存
     * @return
     * @throws Exception
     */
    @Action("vehicleAction_save")
    public String save() throws Exception {
        vehicleService.save(model);
        return "list";
    }


    /**
     * 眼删除的id
     */
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * 删除
     * @return
     * @throws Exception
     */
    @Action("vehicleAction_delete")
    public String delete() throws Exception {

        vehicleService.delete(ids);

        return "list";
    }
}
