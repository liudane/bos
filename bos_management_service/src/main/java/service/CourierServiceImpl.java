package service;

import dao.CourierDao;
import domain.Courier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhaohui on 11/01/2018.
 */
@Controller
@Transactional
public class CourierServiceImpl implements CourierService{

    @Autowired
    private CourierDao cd;


    public void save(Courier model) {
        cd.save(model);
    }

    public Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable) {
        return cd.findAll(specification, pageable);
    }

    public void logicdelete(String ids) {

        if(StringUtils.isNoneBlank(ids)){
            String[] strings = ids.split(",");
            for (String cId:strings) {
                cd.logicDelete(Integer.parseInt(cId));
            }
        }
    }

    public List<Courier> findNotDel() {
        return cd.fingNotDel();
    }

    /**
     * 还原快递员
     * @param ids
     */
    @Override
    public void validation(String ids) {
        if(StringUtils.isNotBlank(ids)){
            String[] strings = ids.split(",");
            for (String id:strings) {
                cd.validation(Integer.parseInt(id));
            }
        }
    }
}
