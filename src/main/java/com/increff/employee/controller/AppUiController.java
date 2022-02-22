package com.increff.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController {

	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}


	@RequestMapping(value = "/ui/employee")
	public ModelAndView employee() {
		return mav("employee.html");
	}

	@RequestMapping(value = "/ui/brand")
	public ModelAndView brand() {
		return mav("brand.html");
	}

	@RequestMapping(value = "/ui/product")
	public ModelAndView product() {
		return mav("product.html");
	}

	@RequestMapping(value = "/ui/inventory")
	public ModelAndView inventory() {
		return mav("inventory.html");
	}

	@RequestMapping(value = "/ui/orderEdit")
	public ModelAndView orderEdit() {
		return mav("orderEdit.html");
	}

	@RequestMapping(value = "/ui/orderPreview/{orderId}", method = RequestMethod.GET)
	public ModelAndView orderPreview(@PathVariable String orderId) {
		ModelAndView mavObject = mav("orderPreview.html");
		mavObject.addObject("orderId", orderId.toString());
		// System.out.print(orderId);
		return mavObject;
	}

	@RequestMapping(value = "/ui/order")
	public ModelAndView order() {
		return mav("order.html");
	}

	@RequestMapping(value = "/ui/orderEdit/{orderId}", method = RequestMethod.GET)
	public ModelAndView orderEdit(@PathVariable String orderId) {
		ModelAndView mavObject = mav("orderEdit.html");
		mavObject.addObject("orderId", orderId.toString());
		// System.out.print(orderId);
		return mavObject;
	}

	@RequestMapping(value = "/ui/reports")
	public ModelAndView reports() {
		return mav("reports.html");
	}

	@RequestMapping(value = "/ui/reportsSales")
	public ModelAndView salesReport() {
		return mav("salesReport.html");
	}



	@RequestMapping(value = "/ui/admin")
	public ModelAndView admin() {
		return mav("user.html");
	}

}
