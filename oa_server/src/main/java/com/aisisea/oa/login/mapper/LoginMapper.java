package com.aisisea.oa.login.mapper;

import com.aisisea.oa.login.domain.Account;
import org.apache.ibatis.annotations.Param;

public interface LoginMapper {
    Account login(@Param("acUsername") String acUsername, @Param("acPassword") String acPassword);
}
