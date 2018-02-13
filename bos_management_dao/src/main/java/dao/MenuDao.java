package dao;

import domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhaohui on 14/01/2018.
 */
public interface MenuDao extends JpaRepository<Menu, Integer>{



    //select * from T_MENU t where t.C_pid is null;
    List<Menu> findByParentMenuIsNull();
}
