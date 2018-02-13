package service;

import domain.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhaohui on 09/01/2018.
 */
public interface SubAreaService {
    void save(SubArea model);

    Page<SubArea> pageQuery(Pageable pageable);

    List<SubArea> findByFixedAreaIsNull();
}
