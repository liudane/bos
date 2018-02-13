package dao;

import domain.SubArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhaohui on 09/01/2018.
 */
public interface SubAreaDao extends JpaRepository<SubArea, String> {

    List<SubArea> findByFixedAreaIsNull();
}
