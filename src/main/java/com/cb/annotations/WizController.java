package com.cb.annotations;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@CrossOrigin(origins = "http://3.135.185.219:4200")
@Controller
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "CREATED"),
        @ApiResponse(code = 206, message = "PARTIAL_CONTENT"),
        @ApiResponse(code = 226, message = "IM_USED"),
        @ApiResponse(code = 409, message = "CONFLICT"),
        @ApiResponse(code = 424, message = "FAILED_DEPENDENCY"),
        })
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WizController {
}

