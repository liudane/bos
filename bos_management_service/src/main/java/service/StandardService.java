package service;

import domain.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhaohui on 09/01/2018.
 */

public interface StandardService {
    void save(Standard model);

    Page<Standard> pageQuery(Pageable pageable);

    List<Standard> findAll();

    void invalid(String ids);

    void validation(String ids);
}
