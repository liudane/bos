package service;

import dao.TakeTimeDao;
import domain.TakeTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhaohui on 21/01/2018.
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {

    @Autowired
    private TakeTimeDao takeTimeDao;



    @Override
    public void save(TakeTime model) {
        takeTimeDao.save(model);
    }

    @Override
    public Page<TakeTime> pageQuery(Pageable pageable) {


        return takeTimeDao.findAll(pageable);


    }

    @Override
    public void delete(String ids) {

        if(StringUtils.isNotBlank(ids)){
            String[] strings = ids.split(",");
            for (String id:strings) {
                takeTimeDao.delete(Integer.parseInt(id));
            }
        }
    }
}
