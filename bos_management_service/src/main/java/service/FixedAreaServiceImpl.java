package service;

import dao.CourierDao;
import dao.FixedAreaDao;
import dao.SubAreaDao;
import domain.Courier;
import domain.FixedArea;
import domain.SubArea;
import domain.TakeTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhaohui on 10/01/2018.
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService{

    @Autowired
    private FixedAreaDao fixedAreaDao;

    @Autowired
    private SubAreaDao subAreaDao;

    @Autowired
    private CourierDao courierDao;


    /**
     * 保存定区，分区关联定区
     * @param model
     */
    public void save(FixedArea model, String[] subareaIds) {
        //如果主键类型是基本类型，使用save方法传递参数
        //如果主键不是基本类型，使用save返回结果
        //用save方法，将model对象从瞬时态转为持久态
        model = fixedAreaDao.save(model);
        if(subareaIds != null && subareaIds.length>0){
            for (String subareaId:subareaIds) {
                SubArea subArea = subAreaDao.findOne(subareaId);
                //关联定区对象：持久态、托管态
                subArea.setFixedArea(model);//更新分区表中的外键：定区ID
            }
        }

    }

    public Page<FixedArea> pageQuery(Pageable pageable) {
        return fixedAreaDao.findAll(pageable);
    }

    public void associationCourier2FixedArea(String id, Integer courierId, Integer takeTimeId) {
        //定区关联快递员 关系：多对多
        FixedArea fixedArea = fixedAreaDao.findOne(id);
        Courier courier = courierDao.findOne(courierId);
        fixedArea.getCouriers().add(courier);//向定区快递员中间表中添加记录

        //给快递员关联收派时间：多对一
        TakeTime takeTime = new TakeTime();
        takeTime.setId(takeTimeId);
        courier.setTakeTime(takeTime);//持久态可以关联托管态/持久态

    }
}
