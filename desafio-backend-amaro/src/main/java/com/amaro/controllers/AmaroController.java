package com.amaro.controllers;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amaro.entities.Product;
import com.amaro.services.FileProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AmaroController {
	
	private final FileProcessor fileProcessor;
	
	public AmaroController(FileProcessor fileProcessor) {
		this.fileProcessor = fileProcessor;
	}

	@GetMapping(value = "/")
	public String index() {
		return "API da Amaro Funcionando!";
	}
	
	@PostMapping(value = "/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
		Set<Product> products = this.fileProcessor.process(file);
		products.forEach(p -> log.info("\t{}", p));
		return ResponseEntity.ok("The '" + file.getOriginalFilename() + "' file was processed successfully.");
	}

}
