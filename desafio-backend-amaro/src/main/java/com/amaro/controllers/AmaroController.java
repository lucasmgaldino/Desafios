package com.amaro.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amaro.dto.DTOProduct;
import com.amaro.entities.Product;
import com.amaro.persistence.ProductRepository;
import com.amaro.services.FileProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AmaroController {
	
	private final FileProcessor fileProcessor;
	private final ProductRepository productRepository;
	
	public AmaroController(FileProcessor fileProcessor, ProductRepository productRepository) {
		this.fileProcessor = fileProcessor;
		this.productRepository = productRepository;
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
	
	@GetMapping(value = "/product/{id}")
	public ResponseEntity<DTOProduct> getProductById(@PathVariable(name = "id") Optional<Integer> optionalId) {
		if (Objects.isNull(optionalId) || optionalId.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Optional<Product> optionalProduct = this.productRepository.findById(optionalId.get());
		if (optionalProduct.isPresent()) {
			return ResponseEntity.ok(DTOProduct.from(optionalProduct.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/products")
	public List<DTOProduct> getProductsByNameAndTag(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "tag", required = false) String tag) {
		Set<Product> products = this.productRepository.findProductByNameAndTag(StringUtils.trimWhitespace(name),
				StringUtils.trimWhitespace(tag));
		return products.stream().map(DTOProduct::from).toList();
	}

}
