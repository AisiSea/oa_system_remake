package com.aisisea.oa.login.service.impl;

import com.aisisea.oa.login.domain.Account;
import com.aisisea.oa.login.mapper.LoginMapper;
import com.aisisea.oa.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public Account login(String acUsername, String acPassword) {
        return loginMapper.login(acUsername, acPassword);
    }

}
