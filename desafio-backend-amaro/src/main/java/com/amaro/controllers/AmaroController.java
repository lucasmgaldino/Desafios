package com.amaro.controllers;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amaro.dto.DataFile;
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
	
	@Transactional
	@PostMapping(value = "/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
		Optional<DataFile> optionalDataFile = this.fileProcessor.process(file);
		if (optionalDataFile.isEmpty()) {
			return ResponseEntity.internalServerError().build();
		}
		optionalDataFile.get().getProducts().forEach(p -> {
			log.info("Product: {}", p);
		});
		return ResponseEntity.ok("File uploaded successfully.");
	}

}
