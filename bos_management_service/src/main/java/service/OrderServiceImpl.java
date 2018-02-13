package service;

import crm_service.CustomerService;
import dao.AreaDao;
import dao.FixedAreaDao;
import dao.OrderDao;
import dao.WorkBillDao;
import domain.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.AliSmsUtil;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by zhaohui on 11/01/2018.
 */
@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CustomerService customerProxy;

    @Autowired
    private FixedAreaDao fixedAreaDao;

    @Autowired
    private WorkBillDao workBillDao;

    @Autowired
    private AreaDao areaDao;


    /**
     * 订单保存；尝试自动分单（找快递员，给快递员产生工单）
     * @param order
     */
    public void save(Order order) {
        order.setOrderNum(UUID.randomUUID().toString());
        order.setOrderTime(new Date());

        //object references an unsaved transient instance引用了一个为保存的瞬时态对象
        //解决方案一：根据省市区查询出区域对象
        //解决方案二：将订单对象中的区域对象设置为null
        Area sendArea = order.getSendArea();
        order.setSendArea(null);
        order.setRecArea(null);
        orderDao.save(order);

        //策略一：根据客户详细地址查找定区ID：尝试自动分单
        String sendAddress = order.getSendAddress();
        //调用CRM查询定区ID
        String fixedAreaId = customerProxy.findByAddress(sendAddress);
        //地址对应客户分配过定区
        if(StringUtils.isNotBlank(fixedAreaId)){
            FixedArea fixedArea = fixedAreaDao.findOne(fixedAreaId);
            //定区和快递员：多对多
            Set<Courier> couriers = fixedArea.getCouriers();
            //判断快递员取件数量/快递员上班时间
            for (Courier courier:couriers) {
                //找到快递员：创建取件任务
                WorkBill workBill = new WorkBill();
                workBill.setAttachbilltimes(0);//追单次数
                workBill.setBuildtime(new Date());
                workBill.setCourier(courier);//工单关联快递员
                workBill.setOrder(order);//工单关联订单
                workBill.setPickstate("未取件");
                workBill.setRemark(order.getRemark());//备注
                workBill.setType("新单");
                workBillDao.save(workBill);

                //发送短信
                boolean ret;
                try{
                    ret = AliSmsUtil.sendMessage(courier.getTelephone(), null, null);
                    workBill.setSmsNumber(ret + "");
                }catch (Exception e){
                    e.printStackTrace();
                }

                order.setOrderType("自动分单");
                order.setCourier(courier);
                order.setStatus("待取件");
                return;
            }
        }
        //策略二：根据客户取件省市区，详细地址完成自动分单
        //根据寄件人省市区查询区域对象
        Area area = areaDao.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());

        if(area != null){
            Set<SubArea> subareas = area.getSubareas();
            for (SubArea subArea:subareas) {
                //寄件人详细地址对比
                if(sendAddress.contains(subArea.getKeyWords()) || sendAddress.contains(subArea.getAssistKeyWords())){
                    //找到分区，分区--定区：多对一
                    FixedArea fixedArea = subArea.getFixedArea();
                    //定区--快递员：多对多
                    Set<Courier> couriers = fixedArea.getCouriers();
                    //判断快递员待取件数量/快递员上班时间
                    for (Courier courier:couriers) {
                        //找到快递员，创建取件任务
                        WorkBill workBill = new WorkBill();
                        workBill.setAttachbilltimes(0);//追单次数
                        workBill.setBuildtime(new Date());
                        workBill.setCourier(courier);//工单关联快递员
                        workBill.setOrder(order);//工单关联订单
                        workBill.setPickstate("未取件");
                        workBill.setRemark(order.getRemark());
                        workBill.setType("新单");
                        workBillDao.save(workBill);
                        //发送快递
                        boolean ret;
                        try{
//                            ret = AliSmsUtil.sendMessage(courier.getTelephone(), null, null);
//                            workBill.setSmsNumber(ret +"");
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        //订单完成自动分单
                        order.setOrderType("自动分单");
                        order.setCourier(courier);
                        order.setStatus("待取件");
                        return;
                    }
                }
            }
        }
        order.setOrderType("人工分单");

    }
}































