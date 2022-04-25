package com.amaro.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AmaroController {
	
	@GetMapping(value = "/")
	public String index() {
		return "API da Amaro Funcionando!";
	}

}
