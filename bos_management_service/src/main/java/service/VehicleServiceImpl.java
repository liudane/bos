package service;

import dao.VehicleDao;
import domain.Vehicle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhaohui on 22/01/2018.
 */
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleDao vehicleDao;

    @Override
    public void save(Vehicle model) {
        vehicleDao.save(model);
    }

    @Override
    public Page<Vehicle> pageQuery(Pageable pageable) {
        return vehicleDao.findAll(pageable);
    }

    @Override
    public void delete(String ids) {
        if(StringUtils.isNotBlank(ids)){
            String[] strings = ids.split(",");
            for (String id:strings) {
                vehicleDao.delete(Integer.parseInt(id));
            }
        }
    }
}
