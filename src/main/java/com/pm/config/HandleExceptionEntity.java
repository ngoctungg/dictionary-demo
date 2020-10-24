package com.pm.config;

import com.pm.exception.NotFoundPostException;
import com.pm.model.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class HandleExceptionEntity extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity hanldeAllException(){
        return ResponseEntity.badRequest().body(new ResponseMessage("400","ERROR"));
    }

    @ExceptionHandler(NotFoundPostException.class)
    public final ResponseEntity hanldePostNotFound(Exception ex, WebRequest request){
        return ResponseEntity.notFound().build();
    }
}
