package dao;

import domain.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zhaohui on 11/01/2018.
 */
public interface CourierDao extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier>{

    //将表中的c_deltag改为1
    //@Query("update Courier c set c.deltag='1' where c.id=?1")也可以用
    @Query("update Courier c set c.deltag = '1' where c.id = ?1")
    @Modifying
    void logicDelete(int i);


    @Query("from Courier c where c.deltag = '0'")
    List<Courier> fingNotDel();

    @Modifying
    @Query("update Courier c set c.deltag='0' where c.id=?1")
    void validation(int i);
}
