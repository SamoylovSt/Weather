package com.weather.controllers.advice;

import com.weather.exception.AppException;
import com.weather.exception.NotFoundException;
import com.weather.exception.PersistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PersistException.class)
    public ModelAndView persistException(PersistException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", ex.getMessage());
        mav.addObject("errorCode", ex.getErrorCode());
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return mav;
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(NotFoundException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorCode", ex.getErrorCode());
        mav.addObject("message", ex.getUserMessage());
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }

    @ExceptionHandler(AppException.class)
    public ModelAndView handleException(AppException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", ex.getUserMessage());
        mav.addObject("errorCode", ex.getErrorCode());
        mav.setStatus(HttpStatus.BAD_REQUEST);
        return mav;
    }

}
