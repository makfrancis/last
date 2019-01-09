package com.accenture.bars.controller;

import java.io.File;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.circuitbreaker.BarsHystrix;

/**
 * BarsController Class
 *
 * @author christian.p.c.mirano
 * @since 17.10.18
 */

/**
 * BarsController - controller of the Bars Web App
 *
 */

@EnableCircuitBreaker
@Controller
public class BarsController {

	@Autowired
	BarsHystrix barsHystrix;
	String role;

	@RequestMapping(value = "/bars")
	public String roleFromAccount(
			@RequestParam(value = "rights", required = false) String role,
			Model model){
		String page = "";

		if("admin".equals(role)) {
			page = "barsadmin";
		} else if ("user".equals(role)) {
			page = "barsnonadmin";
		}
		this.role = role;
		model.addAttribute("rights", role);

		return page;
	}

	@RequestMapping(value = "/")
	public String goToBarsHome() {
		String page = "";
		if("admin".equals(role)) {
			page = "barsadmin";
		} else if ("user".equals(role)) {
			page = "barsnonadmin";
		}
		return page;
	}

	@RequestMapping(value = "/process")
	public ModelAndView processRequest(@RequestParam("files") File file) throws JSONException {

		return barsHystrix.execute(file, role);
	}

}