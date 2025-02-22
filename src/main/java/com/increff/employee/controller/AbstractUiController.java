package com.increff.employee.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


@Controller
public abstract class AbstractUiController {


	@Value("${app.baseUrl}")
	private String baseUrl;

	@Value("${app.orderId}")
	private String orderId;

	protected ModelAndView mav(String page) {
		// Set info
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("baseUrl", baseUrl);
		return mav;
	}



}
