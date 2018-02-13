package web.action;

import domain.Standard;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import service.StandardService;

import java.util.Date;
import java.util.List;

/**
 * Created by zhaohui on 09/01/2018.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name="list", type="redirect", location = "/pages/base/standard.jsp")
})
public class StandardAction extends BaseAction<Standard>{

    /**
     * 继承了BaseAction就不需要在这里用模型驱动了
     * private Standard model = new Standard();
     * public Standard getModel(){
     *     return model;
     * }
     */

    @Autowired
    private StandardService ss;

    @Action("standardAction_save")
    public String save() throws Exception {
        model.setOperatingTime(new Date());
        ss.save(model);
        return "list";
    }

    /**
     *
     * //属性驱动获取datagrid，提交page(当前页)，rows(页大小)
     private int page;
     private int rows;
     @Override
     public void setPage(int page) {
     this.page = page;
     }
     @Override
     public void setRows(int rows) {
     this.rows = rows;
     }
     */

    /**
     * 收派标准分页查询
     * 返回json对象：{"total":100,"rows":[{},{}]}
     * @return
     * @throws Exception
     */
    @Action("standardAction_pageQuery")
    public String pageQuery() throws Exception {

        Pageable pageable = new PageRequest(page-1, rows);
        Page<Standard> page = ss.pageQuery(pageable);
        this.java2Json(page, null);
        return NONE;
    }


    /**
     * 查询收派标准，返回json数组
     */
    @Action("standardAction_findAll")
    public void findAll() throws Exception {
        List<Standard> list = ss.findAll();
        String json = JSONArray.fromObject(list).toString();

        //向浏览器响应数据
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().write(json);
    }

    /**
     * 接收页面传入的要作废的标准id
     */
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * 作废收派标准
     * @return
     * @throws Exception
     */
    @Action("standardAction_invalid")
    public String invalid() throws Exception {
        ss.invalid(ids);
        return "list";
    }


    /**
     * 还原收派标准
     * @return
     * @throws Exception
     */
    @Action("standardAction_validation")
    public String validation() throws Exception {
        ss.validation(ids);
        return "list";
    }
}
