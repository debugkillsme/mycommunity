package com.friday.peanutbutter.service;


import com.friday.peanutbutter.mapper.UserMapper;
import com.friday.peanutbutter.model.User;
import com.friday.peanutbutter.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        //根据githubuser的account_id找到对应的user
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users= userMapper.selectByExample(userExample);
        if(users.size()==0){
            //数据库中没有相同accountId的user账号，直接插入数据库
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //数据库中有相同账号，对数据库中内容进行更新
            User dbUser = users.get(0);

            User updateUser = new User();
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setGmtCreate(System.currentTimeMillis());
            updateUser.setGmtModified(System.currentTimeMillis());
            //更新token
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            //使用updateUser更新example
            userMapper.updateByExampleSelective(updateUser,example);
        }
    }
}
