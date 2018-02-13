package service;

import domain.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by zhaohui on 11/01/2018.
 */
public interface CourierService {
    void save(Courier model);

    Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable);

    void logicdelete(String ids);

    List<Courier> findNotDel();

    void validation(String ids);
}
