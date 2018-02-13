package dao;

import domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhaohui on 11/01/2018.
 */
public interface OrderDao extends JpaRepository<Order, Integer>{
}
