package service;

import domain.Order;

import javax.jws.WebService;

/**
 * Created by zhaohui on 11/01/2018.
 */

@WebService
public interface OrderService {

    public void save(Order order);
}
