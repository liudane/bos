package service;

import dao.MenuDao;
import domain.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhaohui on 14/01/2018.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuDao menuDao;


    /**
     * treegrid要求的数据格式 是通过children展示子节点，如果查询fundAll，会出现重复数据，改为只查询顶级节点
     * @return
     */
    @Override
    public List<Menu> findAll() {
        return menuDao.findByParentMenuIsNull();
    }

    @Override
    public List<Menu> findBySimple() {
        return menuDao.findAll();
    }

    @Override
    public void save(Menu model) {
        Menu parentMenu = model.getParentMenu();
        if(parentMenu.getId() == null){
            model.setParentMenu(null);
        }
        menuDao.save(model);
    }
}
