package service;

import dao.StandardDao;
import domain.Standard;
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
public class StandardServiceImpl implements StandardService{

    @Autowired
    private StandardDao sd;

    public void save(Standard model) {
        sd.save(model);
    }

    public Page<Standard> pageQuery(Pageable pageable) {
        return sd.findAll(pageable);
    }

    public List<Standard> findAll() {

        return sd.findAll();
    }

    @Override
    public void invalid(String ids) {
        if(StringUtils.isNotBlank(ids)){
            String[] strings = ids.split(",");
            for (String id:strings) {
                sd.invalid(Integer.parseInt(id));
            }
        }
    }

    @Override
    public void validation(String ids) {
        if(StringUtils.isNotBlank(ids)){
            String[] strings = ids.split(",");
            for (String id:strings) {
                sd.validation(Integer.parseInt(id));
            }
        }
    }
}
