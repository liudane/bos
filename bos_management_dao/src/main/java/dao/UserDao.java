package dao;

import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhaohui on 14/01/2018.
 */
public interface UserDao extends JpaRepository<User, Integer>{
    User findByUsername(String username);
}
