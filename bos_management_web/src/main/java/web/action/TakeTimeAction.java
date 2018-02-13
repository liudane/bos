package web.action;

import domain.TakeTime;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import service.TakeTimeService;

import java.util.Date;

/**
 * Created by zhaohui on 21/01/2018.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name="list", type="redirect", location = "/pages/base/take_time.jsp")
})
public class TakeTimeAction extends BaseAction<TakeTime>{

    @Autowired
    private TakeTimeService takeTimeService;



    @Action("takeTimeAction_pageQuery")
    public String pageQuery() throws Exception {

        Pageable pageable = new PageRequest(page-1, rows);

        Page<TakeTime> page = takeTimeService.pageQuery(pageable);



        this.java2Json(page,new String[]{});


        return NONE;
    }

    /**
     * 新增取派时间
     * @return
     * @throws Exception
     */
    @Action("takeTimeAction_save")
    public String save() throws Exception {

        model.setOperatingTime(new Date());

        takeTimeService.save(model);

        return "list";
    }



    //获取页面提交要删除的数据ID
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }


    @Action("takeTimeAction_delete")
    public String delete() throws Exception {

        takeTimeService.delete(ids);

        return "list";
    }
}
