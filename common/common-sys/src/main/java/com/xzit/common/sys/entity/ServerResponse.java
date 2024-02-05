package com.xzit.common.sys.entity;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 响应类型
 * @param <T>
 */
@Getter
public class ServerResponse <T> implements Serializable {
    @Serial
    private static final long serialVersionUID=2L;
    T data;
    String msg;
    Boolean isSuccess;
    Integer code;
    private ServerResponse(Integer code,String msg,Boolean isSuccess,T data){
        this.code=code;
        this.msg=msg;
        this.isSuccess=isSuccess;
        this.data=data;
    }
    public static <T> ServerResponse<T> success(T data){
        return new ServerResponse<>(200,"请求成功",true,data);

    }
    public static <T> ServerResponse<T> success(){
        return new ServerResponse<>(200,"请求成功",true,null);
    }
    public static <T> ServerResponse<T> fail(Integer code,String msg){
        return new ServerResponse<>(code,msg,false,null);
    }
    public static <T> ServerResponse<T> fail(Integer code){
        return new ServerResponse<>(code,"出错啦",false,null);
    }

}
