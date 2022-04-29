package com.amaro.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.InvalidMediaTypeException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.amaro.entities.Product;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileProcessor {

	private Set<Processor> processors;

	public FileProcessor(Set<Processor> processors) {
		this.processors = processors;
	}

	@SneakyThrows
	public Set<Product> process(MultipartFile file) {
		Assert.notNull(file, "A MultipartFile is required.");
		final String originalFileName = file.getOriginalFilename();
		log.info("Processing the '{}' file.", originalFileName);
		Optional<Processor> optionalProcessor = this.processors.stream()
				.filter(p -> p.accept(file.getContentType())).findFirst();
		if (optionalProcessor.isPresent()) {
			return optionalProcessor.get().process(file.getInputStream());
		} else {
			log.error("Error processing the {} file. Only application/xml and application/json are allowed.", originalFileName);
			throw new InvalidMediaTypeException(file.getContentType(), "Only application/xml and application/json are allowed.");
		}
	}

}
