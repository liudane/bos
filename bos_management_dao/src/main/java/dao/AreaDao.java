package dao;

import domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhaohui on 09/01/2018.
 */
public interface AreaDao extends JpaRepository<Area, String>{

    Area findByProvinceAndCityAndDistrict(String province, String city, String district);
}
