package com.weather.controllers.advice;

import com.weather.exception.PersistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PersistException.class)
    public ModelAndView PersistException(PersistException ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        mav.addObject("message", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }

}
