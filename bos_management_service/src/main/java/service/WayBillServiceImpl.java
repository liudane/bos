package service;

import dao.WayBillDao;
import domain.WayBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhaohui on 18/01/2018.
 */
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {

    @Autowired
    private WayBillDao wayBillDao;


    @Override
    public void save(WayBill model) {

        wayBillDao.save(model);
    }
}
