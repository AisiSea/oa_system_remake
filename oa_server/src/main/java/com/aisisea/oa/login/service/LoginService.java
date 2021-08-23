package com.aisisea.oa.login.service;

import com.aisisea.oa.login.domain.Account;

public interface LoginService {
    Account login(String acUsername, String acPassword);
}
