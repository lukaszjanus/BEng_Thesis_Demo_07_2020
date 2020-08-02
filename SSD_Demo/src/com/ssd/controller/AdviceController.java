package com.ssd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import com.ssd.statics.Statics;

/**
 * Controller for redirect handled Exception to view
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 13 kwi 2020
 *
 */
@Controller
@ControllerAdvice
public class AdviceController {

	/**
	 * Standard Spring method generate object 'Model and View'. Here this method is
	 * used to catch as Global Error Handle Exception.
	 *
	 * @param e - error information
	 * 
	 * @return ModelAndView with address 'handlerException'
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handle(Exception e) {

		ModelAndView mv = new ModelAndView();

		if (e.getMessage().equals("4")) {
			String error = "Probably error with null point exception, java-code: " + e.getMessage();
			mv.addObject("message", error);
		} else if (e.getMessage().contains("org.hibernate.hql")) {
			String error = "Probably HQL-error.<br>Please contact with administrator.<br>";
			mv.addObject("message", error);
		} else if (e.getMessage().contains("could not execute statement")) {
			String error = "Probably SQL-error.<br>Please contact with administrator.<br>";
			mv.addObject("message", error);
		} else if (e.getMessage().equals("5")) {
			String error = "Probably array-error (inbound exception).<br>If you try upload file, please check data, if all cells are not empty.<br>Please contact with administrator.<br>";
			mv.addObject("message", error);
		} else {
			mv.addObject("message", e.getMessage());
		}

		mv.addObject("helpLabel", Statics.getHelp());
		mv.addObject("nameApp", Statics.getnameApp());

		mv.setViewName("errors/handlerExeption");

		return mv;
	}
}
