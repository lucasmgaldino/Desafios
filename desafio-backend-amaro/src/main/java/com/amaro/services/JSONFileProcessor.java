package com.amaro.services;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.amaro.dto.DataFile;
import com.amaro.entities.Product;
import com.amaro.entities.Tag;
import com.amaro.persistence.ProductRepository;
import com.amaro.persistence.TagRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@Component
public class JSONFileProcessor extends AbstractProcessor {
	
	private final ObjectMapper objectMapper;
	private final ProductRepository productRepository;

	public JSONFileProcessor(ObjectMapper objectMapper, ProductRepository productRepository,
			TagRepository tagRepository) {
		super(tagRepository);
		this.objectMapper = objectMapper;
		this.productRepository = productRepository;
	}
	
	@Override
	public final boolean accept(String contentType) {
		if (!StringUtils.hasText(contentType)) {
			return false;
		}
		return MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType);
	}

	@SneakyThrows
	public Set<Product> process(InputStream inputStream) {
		DataFile dataFile = this.objectMapper.readValue(inputStream, DataFile.class);
		Set<Product> products = new HashSet<>();
		dataFile.getProducts().forEach(p -> {
			Set<Tag> tags = processTags(p.getTags());
			Product persistedProduct = this.productRepository.save(new Product(p.getId(), p.getName(), tags));
			products.add(persistedProduct);
		});
		return products;
	}
	
}
