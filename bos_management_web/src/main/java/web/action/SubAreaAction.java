package web.action;

import domain.SubArea;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import service.SubAreaService;

import java.util.List;

/**
 * Created by zhaohui on 09/01/2018.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name="list", type="redirect", location = "/pages/base/sub_area.jsp"),
        @Result(name="input", location = "/input.jsp")
})
public class SubAreaAction extends BaseAction<SubArea>{
    @Autowired
    private SubAreaService sas;


    /**
     * 保存分区
     * @return
     * @throws Exception
     */
    @Action("subAreaAction_save")
    public String save() throws Exception {

        sas.save(model);
        return "list";
    }


    /**
     * 分页查询
     */
    @Action("subAreaAction_pageQuery")
    public String pageQuery() throws Exception {

        Pageable pageable = new PageRequest(page-1, rows);
        Page<SubArea> page = sas.pageQuery(pageable);
        this.java2Json(page, new String[]{"subareas"});
        return NONE;
    }


    /**
     * 查询所有未关联到定区的分区
     * @return
     * @throws Exception
     */
    @Action("subAreaAction_findAll")
    public String findAll() throws Exception {
        List<SubArea> list = sas.findByFixedAreaIsNull();
        this.java2Json(list, new String[]{"subareas"});
        return NONE;
    }
}





























