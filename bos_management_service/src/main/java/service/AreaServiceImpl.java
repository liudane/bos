package service;

import dao.AreaDao;
import domain.Area;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhaohui on 09/01/2018.
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService{

    @Autowired
    private AreaDao ad;


    @Override
    public void save(Area model) {
        ad.save(model);
    }

    public void save(List<Area> list) {

        ad.save(list);
    }

    public Page<Area> pageQuery(Pageable pageable) {


        return ad.findAll(pageable);
    }

    public List<Area> findAll() {
        return ad.findAll();
    }

    @Override
    public void delete(String ids) {
        if(StringUtils.isNotBlank(ids)){
            String[] strings = ids.split(",");
            for (String id:strings) {
                ad.delete(id);
            }
        }
    }
}
