package com.aisisea.oa.login.web.controller;

import com.aisisea.oa.commons.contants.ReturnObjectConstants;
import com.aisisea.oa.commons.domain.ReturnObject;
import com.aisisea.oa.commons.domain.TokenConstants;
import com.aisisea.oa.commons.utils.DefaultReturnObject;
import com.aisisea.oa.commons.utils.TokenUtilsFactory;
import com.aisisea.oa.commons.utils.token.TokenUtils;
import com.aisisea.oa.login.domain.Account;
import com.aisisea.oa.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ResponseBody
    @RequestMapping(value = "/token", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public Object token(@RequestBody Map<String, Object> map) {
        String token = (String) map.get("token");
        TokenUtils tokenUtils = TokenUtilsFactory.getTokenUtils();
        if (token != null && !tokenUtils.isExpireFromToken(token)) {
            return DefaultReturnObject.getSuccessReturnObject(null);
        } else return DefaultReturnObject.getTokenErrorReturnObject(null);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody Map<String, Object> map) throws Exception {
        //验证数据合法性
        String acUsername = (String) map.get("acUsername");
        String acPassword = (String) map.get("acPassword");
        if (acUsername == null || acUsername.equals("") || acPassword == null || acPassword.equals(""))
            return DefaultReturnObject.getErrorParamReturnObject(null);
        //使用Service层对象调用DAO层查询数据
        Account account = loginService.login(acUsername, acPassword);
        if (account == null) {  //如果查询失败则返回失败信息
            return DefaultReturnObject.getErrorParamReturnObject(null);
        } else {
            account.setAcPassword(null);
            //准备token数据
            Boolean loginKeep = (Boolean) map.get("loginKeep");
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("account", account);
            tokenMap.put("loginKeep", loginKeep);
            //使用TokenUtils获取token
            String token = "";
            TokenUtils tokenUtils = TokenUtilsFactory.getTokenUtils();
            if (loginKeep)
                token = tokenUtils.createToken(tokenMap, TokenConstants.THREE_DAY_EXPIRE_TIME);
            else token = tokenUtils.createToken(tokenMap, TokenConstants.HALF_HOUR_EXPIRE_TIME);
            //设置登录成功的数据
            HashMap<String, Object> returnMap = new HashMap<>();
            returnMap.put(ReturnObjectConstants.RETURN_DATA_PARAM_ACCOUNT, account);
            returnMap.put(ReturnObjectConstants.RETURN_DATA_PARAM_TOKEN, token);
            //返回成功数据
            ReturnObject returnObject = DefaultReturnObject.getSuccessReturnObject(returnMap);
            return returnObject;
        }
    }

}
