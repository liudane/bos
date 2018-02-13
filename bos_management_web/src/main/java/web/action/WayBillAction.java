package web.action;

import domain.WayBill;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import service.WayBillService;

/**
 * Created by zhaohui on 18/01/2018.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")

public class WayBillAction extends BaseAction<WayBill>{

    @Autowired
    private WayBillService wayBillService;


    @Action("wayBillAction_save")
    public String save() throws Exception {

        String flag = "1";

        try {
            wayBillService.save(model);
        } catch (Exception e) {
            e.printStackTrace();
            flag="0";
        }
        ServletActionContext.getResponse().getWriter().write(flag);
        return NONE;
    }
}
