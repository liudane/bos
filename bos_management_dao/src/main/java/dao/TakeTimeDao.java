package dao;

import domain.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhaohui on 21/01/2018.
 */
public interface TakeTimeDao extends JpaRepository<TakeTime, Integer>{
}
