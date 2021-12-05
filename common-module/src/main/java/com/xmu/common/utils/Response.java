package com.xmu.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xmu.common.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T>{
    private int status;
    private String msg;
    private T data;

    public ResponseEntity<?> entity(HttpStatus httpStatus){
        return new ResponseEntity<>(this,httpStatus);
    }

    public static Response<?> of(int code,String msg){
        return new Response<>().setStatus(code).setMsg(msg);
    }

    public static Response<?> of(ResponseCode responseCode){
        return new Response<>().setStatus(responseCode.getCode()).setMsg(responseCode.getMsg());
    }

    public static Response<?> of(ResponseCode responseCode,Object data){
        return new Response<>().setStatus(responseCode.getCode()).setMsg(responseCode.getMsg()).setData(data);
    }
}
