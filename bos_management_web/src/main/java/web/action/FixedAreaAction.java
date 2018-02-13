package web.action;

import crm_service.Customer;
import crm_service.CustomerService;
import domain.FixedArea;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import service.FixedAreaService;

import java.util.List;

/**
 * Created by zhaohui on 09/01/2018.
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Results({
        @Result(name="list", type="redirect", location="/pages/base/fixed_area.jsp")
})
public class FixedAreaAction extends BaseAction<FixedArea>{



    @Autowired
    private FixedAreaService fas;

    //接收页面提交的分区ID

    private String[] subareaIds;
    public void setSubareaIds(String[] subareaIds) {
        this.subareaIds = subareaIds;
    }


    /**
     * 保存定区
     * @return
     * @throws Exception
     */
    @Action("fixedAreaAction_save")
    public String save() throws Exception {
        fas.save(model, subareaIds);
        return "list";
    }

    /**
     * 分页查询定区
     * @return
     * @throws Exception
     */
    @Action("fixedAreaAction_pageQuery")
    public String pageQuery() throws Exception {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<FixedArea> page = fas.pageQuery(pageable);
        this.java2Json(page, new String[]{"couriers","subareas"});
        return NONE;
    }


    @Autowired
    private CustomerService customerProxy;

    /**
     * 调用远程CRM系统，获取未关联到定区的客户数据
     * @return
     * @throws Exception
     */
    @Action("fixedAreaAction_findNotAssociation")
    public String findNotAssociation() throws Exception {
        List<Customer> list = customerProxy.findNotAssociation();
        this.java2Json(list, null);
        return NONE;
    }


    /**
     * 查询已关联到定区的客户数据
     * @return
     * @throws Exception
     */
    @Action("fixedAreaAction_findHasAssociation")
    public String findHasAssociation() throws Exception {

        List<Customer> list = customerProxy.findHasAssociation(model.getId());
        this.java2Json(list, null);
        return NONE;
    }


    //接收下拉框提交的多个客户ID
    private List<Integer> customerIds;
    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }


    /**
     * 调用远程CRM系统客户关联定区
     * @return
     * @throws Exception
     */
    @Action("fixedAreaAction_assignCustomer2FixedArea")
    public String assignCustomer2FixedArea() throws Exception {
        customerProxy.associationFixedAreaId2Customer(model.getId(), customerIds);
        return "list";
    }


    //接收页面提交的快递员ID，收派时间
    private Integer courierId;
    private Integer takeTimeId;
    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }
    public void setTakeTimeId(Integer takeTimeId) {
        this.takeTimeId = takeTimeId;
    }

    @Action("fixedAreaAction_associationCourier2FixedArea")
    public String associationCourier2FixedArea() throws Exception {
        fas.associationCourier2FixedArea(model.getId(), courierId, takeTimeId);
        return "list";
    }
}
































