package service;

import domain.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zhaohui on 22/01/2018.
 */
public interface VehicleService {
    void save(Vehicle model);

    Page<Vehicle> pageQuery(Pageable pageable);

    void delete(String ids);
}
