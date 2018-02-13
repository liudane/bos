package dao;

import domain.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zhaohui on 09/01/2018.
 */
public interface StandardDao extends JpaRepository<Standard, Integer> {

    @Modifying
    @Query("update Standard s set s.status='1-已作废' where s.id=?1")
    void invalid(int i);


    @Modifying
    @Query("update Standard s set s.status='0-正常' where s.id=?1")
    void validation(int i);
}
