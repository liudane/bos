package web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaohui on 09/01/2018.
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    /**
     * BaseAction<Standard>：参数化类型
     * <T>括号中称为实际类型参数
     * @return
     */
    protected T model;
    public T getModel() {
        return model;
    }

    //子类对象创建，父类无參构造执行，在无參构造中获取实际参数
    public BaseAction(){
        try{
            //获取当前运行的class
            Class clazz1 = this.getClass();
            //获取参数类型
            //Type getGenericSuperclass()，返回表示此class所表示的实体(类、接口、基本类型或void)的父类的type
            Type type = clazz1.getGenericSuperclass();
            //获取实际参数类型
            //Type[] getActualTypeArgument(),返回表示此类型实际参数的Type对象数组
            ParameterizedType pt = (ParameterizedType) type;
            Type[] types = pt.getActualTypeArguments();
            Class clazz2 = (Class) types[0];
            model = (T) clazz2.newInstance();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected int page;
    protected int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * 将page对象转换为Json
     * @param page
     * @param excludes
     */
    public void java2Json(Page<T> page, String[] excludes){
        try{
            //创建map集合，自定义键名
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total", page.getTotalElements());
            map.put("rows", page.getContent());
            //使用Json-lib工具，将对象转换为Json
            //转对象、map  使用JsonObject
            //转数组、集合  使用JsonArray
            JsonConfig jsonConfig = new JsonConfig();
            //将不需要转json的属性排除
            jsonConfig.setExcludes(excludes);
            //将map对象转换成json对象
            String json = JSONObject.fromObject(map, jsonConfig).toString();

            //响应给浏览器
            ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
            ServletActionContext.getResponse().getWriter().write(json);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void java2Json(List list, String[] excludes){

        try {
            JsonConfig jsonConfig = new JsonConfig();

            //将一些不需要转json的属性排除
            jsonConfig.setExcludes(excludes);
            String json = JSONArray.fromObject(list, jsonConfig).toString();

            //将数据响应给浏览器
            ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
            ServletActionContext.getResponse().getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
