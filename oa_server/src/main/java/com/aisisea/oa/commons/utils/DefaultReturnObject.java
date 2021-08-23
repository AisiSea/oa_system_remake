package com.aisisea.oa.commons.utils;

import com.aisisea.oa.commons.contants.ReturnObjectConstants;
import com.aisisea.oa.commons.domain.ReturnObject;

import java.util.Map;

public class DefaultReturnObject {

    /************************************错误参数*********************************/

    public static ReturnObject getErrorParamReturnObject(Map<String, Object> obj) {
        return getErrorParamReturnObject(ReturnObjectConstants.PARAM_ERROR_RETURN_MESSAGE, obj);
    }

    public static ReturnObject getErrorParamReturnObject(String msg, Map<String, Object> obj) {
        return getReturnObject(ReturnObjectConstants.PARAM_ERROR_RETURN_STATE, msg, obj);
    }

    /************************************成功状态*********************************/

    public static ReturnObject getSuccessReturnObject(Map<String, Object> obj) {
        return getSuccessReturnObject(ReturnObjectConstants.SUCCESS_RETURN_MESSAGE, obj);
    }

    public static ReturnObject getSuccessReturnObject(String msg, Map<String, Object> obj) {
        return getReturnObject(ReturnObjectConstants.SUCCESS_RETURN_STATE, msg, obj);
    }

    /************************************令牌错误*********************************/

    public static ReturnObject getTokenErrorReturnObject(Map<String, Object> obj) {
        return getTokenErrorReturnObject(ReturnObjectConstants.TOKEN_ERROR_RETURN_MESSAGE, obj);
    }

    public static ReturnObject getTokenErrorReturnObject(String msg, Map<String, Object> obj) {
        return getReturnObject(ReturnObjectConstants.TOKEN_ERROR_RETURN_STATE, msg, obj);
    }

    /************************************默认方法*********************************/

    public static ReturnObject getReturnObject(String state, Map<String, Object> obj) {
        switch (state) {
            case ReturnObjectConstants.SUCCESS_RETURN_STATE: return getSuccessReturnObject(obj);
            case ReturnObjectConstants.PARAM_ERROR_RETURN_MESSAGE: return getErrorParamReturnObject(obj);
            default: return getReturnObject(state, null, obj);
        }
    }

    public static ReturnObject getReturnObject(String state, String msg, Map<String, Object> obj) {
        ReturnObject returnObject = new ReturnObject();
        returnObject.setState(state);
        returnObject.setMsg(msg);
        returnObject.setData(obj);
        return returnObject;
    }

}
