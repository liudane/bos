package web.action;

import domain.Courier;
import domain.Standard;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import service.CourierService;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaohui on 10/01/2018.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name="list", type="redirect", location="pages/base/courier.jsp")
})
public class CourierAction extends BaseAction<Courier>{

    /**
        private Courier model = new Courier;
        public Courier getModel() {
            return model;
        }
    */


    @Autowired
    private CourierService cs;


    /**
     * 保存快递员
     * @return
     * @throws Exception
     */
    @Action("courierAction_save")
    public String save() throws Exception {
        cs.save(model);
        return "list";
    }


    /**
        //属性驱动，获取页面提交的page和rows属性
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
     * 分页查询快递员信息
     * @return
     * @throws Exception
     */
    @Action("courierAction_pageQuery")
    public String pageQuery() throws Exception {
        //获取页面提交的数据
        final String courierNum = model.getCourierNum();
        final String type = model.getType();
        final String company = model.getCompany();

        //String name = model.getStandard().getName();

        Pageable pageable = new PageRequest(page - 1, rows);

        //查询条件dao：Page<T> findAll(Specification<T> spec, Pageable pageable);
        Specification<Courier> specification = new Specification<Courier>(){

            //return一个条件或者条件组合
            //参数1：根实体，是查询对象的主体
            //参数2：用来构建CriteriaQuery对象
            //参数3：设置form、where和select部分
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();

                if(StringUtils.isNoneBlank(courierNum)){
                    //参数1：通过对象导航查询
                    //参数2：查询条件的值
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }

                if(StringUtils.isNoneBlank(type)){
                    Predicate p2 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p2);
                }

                if(StringUtils.isNoneBlank(company)){
                    Predicate p3 = cb.equal(root.get("company").as(String.class), company);
                    list.add(p3);
                }

                //收派标准名称进行查询：关联查询
                Standard standard = model.getStandard();

                if(standard != null && StringUtils.isNoneBlank(standard.getName())){
                    //jpql/jql:select c from Courier c inner join c.standard s where s.name = ?
                    //通过快递员Courier实体，关联到收派标准对象--关联对象
                    Join<Object, Object> join = root.join("standard", JoinType.INNER);
                    Predicate p4 = cb.like(join.get("name").as(String.class), "%" + standard.getName() + "%");
                    list.add(p4);
                }

                if(list.size() == 0){
                    return null;
                }

                Predicate[] restrictions = new Predicate[list.size()];
                restrictions = list.toArray(restrictions);//将list转为数组

                return cb.and(restrictions);
            }
        };
        Page<Courier> page = cs.pageQuery(specification, pageable);
        this.java2Json(page, new String[]{"fixedAreas"});
        /*
            Map<String, Object> map = new HashMap<>();
            map.put("total", page.getTotalElements());
            map.put("rows", page.getContent());

             //转courier实体中属性，将courier实体中fixedareas集合属性排除掉
             JsonConfig jsonConfig = new JsonConfig()；
             //将一些不需要转json的属性排除掉
             jsonConfig.setExcludes(new String[]{"fixedareas"});

             String json = JSONObject.fromObject(map, jsonConfig).toString();
             System.err.println(json);
             ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
             ServletActionContext.getResponse().getWriter().write(json);
         */
        return NONE;
    }



    //获取页面提交的多个快递员ID
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * 批量删除快递员
     * @return
     * @throws Exception
     */
    @Action("courierAction_invalid")
    public String delete() throws Exception {
        cs.logicdelete(ids);
        return "list";
    }


    /**
     * 还原快递员
     * @return
     * @throws Exception
     */
    @Action("courierAction_validation")
    public String validation() throws Exception {
        cs.validation(ids);
        return "list";
    }

    /**
     * 查询在职快递员
     * @return
     * @throws Exception
     */
    @Action("courierAction_listajax")
    public String listajax() throws Exception{
        List<Courier> list = cs.findNotDel();
        this.java2Json(list, new String[]{});
        return NONE;
    }
}






































