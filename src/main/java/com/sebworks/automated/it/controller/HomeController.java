package com.sebworks.automated.it.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sebworks.automated.it.repository.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	public ModelAndView index() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("rows", userRepository.findAll());
		return new ModelAndView("index", map);
	}
}
