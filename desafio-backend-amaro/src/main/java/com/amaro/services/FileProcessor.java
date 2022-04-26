package com.amaro.services;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amaro.dto.DataFile;
import com.amaro.entities.Product;
import com.amaro.entities.Tag;
import com.amaro.persistence.ProductRepository;
import com.amaro.persistence.TagRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileProcessor {

	private final ObjectMapper objectMapper;
	private final ProductRepository productRepository;
	private final TagRepository tagRepository;

	public FileProcessor(ObjectMapper objectMapper, ProductRepository productRepository, TagRepository tagRepository) {
		this.objectMapper = objectMapper;
		this.productRepository = productRepository;
		this.tagRepository = tagRepository;
	}

	@Transactional
	@SneakyThrows
	public Set<Product> process(MultipartFile file) {
		final String originalFileName = file.getOriginalFilename();
		log.info("Processing the file {}.", originalFileName);
		DataFile dataFile = this.objectMapper.readValue(file.getInputStream(), DataFile.class);
		Set<Product> products = new HashSet<>();
		dataFile.getProducts().forEach(p -> {
			Set<Tag> tags = processTags(p.getTags());
			Product persistedProduct = this.productRepository.save(new Product(p.getId(), p.getName(), tags));
			products.add(persistedProduct);
		});
		log.info("The '{}' file was processed successfully.", originalFileName);
		return products;
	}

	private Set<Tag> processTags(Set<String> tagNames) {
		Set<Tag> tags = new HashSet<>();
		for (String name : tagNames) {
			Tag tag = this.tagRepository.saveIfNotExist(new Tag(name));
			tags.add(tag);
		}
		return tags;
	}

}
