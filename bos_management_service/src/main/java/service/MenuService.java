package service;

import domain.Menu;

import java.util.List;

/**
 * Created by zhaohui on 14/01/2018.
 */
public interface MenuService {
    List<Menu> findAll();

    List<Menu> findBySimple();

    void save(Menu model);
}
