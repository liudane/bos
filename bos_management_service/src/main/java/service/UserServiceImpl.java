package service;

import dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhaohui on 14/01/2018.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;
}
