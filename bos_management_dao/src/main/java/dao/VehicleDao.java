package dao;

import domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhaohui on 22/01/2018.
 */
public interface VehicleDao extends JpaRepository<Vehicle, Integer>{
}
