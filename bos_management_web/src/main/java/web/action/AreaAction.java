package web.action;

import domain.Area;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import service.AreaService;
import utils.PinYin4jUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaohui on 09/01/2018.
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Results({
        @Result(name="list", type="redirect", location="/pages/base/area.jsp")
})
public class AreaAction extends BaseAction<Area>{

    /**
        private Area model = new Area();
        @Override
        public Area getModel() {
            return model;
        }
    */


    @Autowired
    private AreaService as;

    //接收上传的文件
    private File upload;
    public void setUpload(File upload) {
        this.upload = upload;
    }

    //接收上传文件的名称
    private String uploadFileName;
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    //接收上传文件的MIME类型
    private String uploadContentType;
    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    @Action("areaAction_importXls")
    public String importXls() throws Exception {

        List<Area> list = new ArrayList<Area>();
        Area area;
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(upload));
        HSSFSheet sheet = workbook.getSheet("sheet1");
        for(Row row : sheet){
            //去除第一行数据--标题行
            if(row.getRowNum() == 0){
               continue;
            }
            String id = row.getCell(0).getStringCellValue();
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();

            area = new Area(id, province, city, district, postcode, null, null);

            //简码HBSJZQD--河北石家庄桥东（河北省石家庄市桥东区）
            province = province.substring(0, province.length()-1);
            city = city.substring(0, city.length()-1);
            district = district.substring(0, district.length()-1);

            String all = province + city + district;

            String[] strings = PinYin4jUtils.getHeadByString(all);
            String shortcode = StringUtils.join(strings);
            area.setShortcode(shortcode);

            //城市编码  shijiazhuang
            String citycode = PinYin4jUtils.hanziToPinyin(city, "");
            area.setCitycode(citycode);
            list.add(area);

        }
        as.save(list);
        return NONE;
    }


    /**
     * 区域分页查询
     * @return
     * @throws Exception
     */
    @Action("areaAction_pageQuery")
    public String pageQuery() throws Exception {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Area> page = as.pageQuery(pageable);
        this.java2Json(page, new String[]{"subareas"});
        return NONE;
    }


    /**
     * 保存区域信息
     * @return
     * @throws Exception
     */
    @Action("areaAction_save")
    public String save() throws Exception {

        String province = model.getProvince();
        String city = model.getCity();
        String district = model.getDistrict();


        //简码HBSJZQD--河北石家庄桥东（河北省石家庄市桥东区）
        province = province.substring(0, province.length()-1);
        city = city.substring(0, city.length()-1);
        district = district.substring(0, district.length()-1);
        String all = province + city + district;
        String[] strings = PinYin4jUtils.getHeadByString(all);
        String shortcode = StringUtils.join(strings);
        model.setShortcode(shortcode);

        //城市编码  shijiazhuang
        String citycode = PinYin4jUtils.hanziToPinyin(city, "");
        model.setCitycode(citycode);

        as.save(model);
        return "list";
    }


    //接收页面选中的要删除的数据id
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * 删除区域信息
     * @return
     * @throws Exception
     */
    @Action("areaAction_delete")
    public String delete() throws Exception {
        as.delete(ids);
        return "list";
    }


    /**
     * 区域--查找全部
     */
    @Action("areaAction_findAll")
    public String findAll() throws Exception {
        List<Area> list = as.findAll();
        this.java2Json(list, new String[]{"subareas"});
        return NONE;
    }
}





























