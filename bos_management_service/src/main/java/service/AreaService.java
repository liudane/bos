package service;

import domain.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhaohui on 09/01/2018.
 */
public interface AreaService {

    void save(Area model);

    void save(List<Area> list);

    Page<Area> pageQuery(Pageable pageable);

    List<Area> findAll();

    void delete(String ids);
}
