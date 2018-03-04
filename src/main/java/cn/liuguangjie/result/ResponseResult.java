package cn.liuguangjie.result;

import java.io.Serializable;

/**
 * @Author ms.liu
 * ~~Email liuguangj@dingtalk.com
 * @Date 2018-03-03 下午6:04
 */

public class ResponseResult<T> implements Serializable {

    private String code;

    private String msg;

    private T data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseResult buildSuccess(T t) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setMsg("请求正常");
        result.setCode("200");
        result.setData(t);
        return result;
    }

    public static <T> ResponseResult buildError(T t) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setMsg("服务器异常");
        result.setCode("500");
        result.setData(t);
        return result;
    }
}
