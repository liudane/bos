package dao;

import domain.WorkBill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhaohui on 11/01/2018.
 */
public interface WorkBillDao extends JpaRepository<WorkBill, String> {
}
