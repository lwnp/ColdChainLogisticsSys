package com.xzit.common.sys.entity;

import com.xzit.common.sys.enums.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 响应类型
 * @param <T>
 */
@Getter
@ToString
@NoArgsConstructor
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
        return new ServerResponse<>(ResponseCodeEnum.SUCCESS.getCode(),ResponseCodeEnum.SUCCESS.getDesc(),true,data);

    }
    public static <T> ServerResponse<T> success(){
        return new ServerResponse<>(ResponseCodeEnum.SUCCESS.getCode(),ResponseCodeEnum.SUCCESS.getDesc(),true,null);
    }
    public static <T> ServerResponse<T> fail(Integer code,String msg){
        return new ServerResponse<>(code,msg,false,null);
    }
    public static <T> ServerResponse<T> fail(Integer code){
        return new ServerResponse<>(code,"出错啦",false,null);
    }
    public static <T> ServerResponse<T> fail(ResponseCodeEnum responseCodeEnum){
        return new ServerResponse<>(responseCodeEnum.getCode(), responseCodeEnum.getDesc(), false,null);
    }
    public static <T> ServerResponse<T> fail(ResponseCodeEnum responseCodeEnum,T data){
        return new ServerResponse<>(responseCodeEnum.getCode(), responseCodeEnum.getDesc(), false,data);
    }

}
