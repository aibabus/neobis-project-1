package com.shop.ShopApplication.service;



import com.shop.ShopApplication.user.User;

import java.util.List;

public interface UserService {
    List<User> getUser();
    User saveUser (User user);
    User getSingleUser (int id);
    void deleteUser (int id);
    User updateUser (User user);
}
