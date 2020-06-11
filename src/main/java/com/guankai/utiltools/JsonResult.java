package com.guankai.utiltools;

/**
 * 通用后台返回结果集
 *
 * @author: guan.kai
 * @date: 2020/6/11 15:17
 **/
public class JsonResult {

    /** 是否成功 */
    private boolean success;

    /** 是否需要登陆 */
    private boolean needLogin;

    /** 提示信息 */
    private String msg;

    /** 请求返回数据结果 */
    private Object obj;


    /**
     * 请求失败
     * @param msg 提示信息
     * @return
     */
    public static JsonResult failure(String msg){
        return new JsonResult(false, false, msg, null);
    }

    /**
     * 需要登陆后才能请求
     * @return
     */
    public static JsonResult needLogin(){
        return new JsonResult(false, true, null, null);
    }

    /**
     * 请求成功
     * @param msg 提示信息
     * @param obj 返回数据
     * @return
     */
    public static JsonResult success(String msg, Object obj){
        return new JsonResult(true, false, msg, obj);
    }

    /**
     * 请求成功
     * @param obj 返回数据
     * @return
     */
    public static JsonResult success(Object obj){
        return success(null, obj);
    }

    /**
     * 请求成功
     * @return
     */
    public static JsonResult success(){
        return success(null, null);
    }

    public JsonResult() {
    }

    public JsonResult(boolean success, boolean needLogin, String msg, Object obj) {
        this.success = success;
        this.needLogin = needLogin;
        this.msg = msg;
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
