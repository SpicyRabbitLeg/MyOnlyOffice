package com.onlyoffice.excetion;


import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class GlobalException {


    @ExceptionHandler(BindException.class)
    public R bindingException(BindException bindException){
        BindingResult bindingResult = bindException.getBindingResult();
        Map<String,String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError)->{
            errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return R.failed("数据校验不成功").setData(errorMap);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Object> MethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return R.failed("数据校验不成功").setData(errorMap);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public R some(Exception e){
        e.printStackTrace();
        return R.failed("全局异常");
    }


}
