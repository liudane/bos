package service;

import dao.SubAreaDao;
import domain.SubArea;
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
public class SubAreaServiceImpl implements SubAreaService{

    @Autowired
    private SubAreaDao sad;

    public void save(SubArea model) {
        sad.save(model);
    }

    public Page<SubArea> pageQuery(Pageable pageable) {
        return sad.findAll(pageable);
    }

    public List<SubArea> findByFixedAreaIsNull() {
        return sad.findByFixedAreaIsNull();
    }
}
