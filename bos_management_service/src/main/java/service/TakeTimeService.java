package service;

import domain.TakeTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zhaohui on 21/01/2018.
 */
public interface TakeTimeService {
    void save(TakeTime model);

    Page<TakeTime> pageQuery(Pageable pageable);

    void delete(String ids);
}
