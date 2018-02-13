package service;

import domain.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zhaohui on 10/01/2018.
 */
public interface FixedAreaService {
    void save(FixedArea model, String[] subareaIds);

    Page<FixedArea> pageQuery(Pageable pageable);

    void associationCourier2FixedArea(String id, Integer courierId, Integer takeTimeId);
}
