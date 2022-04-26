package com.amaro.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amaro.dto.DataFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileProcessor {

	private final ObjectMapper objectMapper;

	public FileProcessor(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@SneakyThrows
	public Optional<DataFile> process(MultipartFile file) {
		final String originalFileName = file.getOriginalFilename();
		log.info("Processing the file {}.", originalFileName);
		DataFile dataFile = this.objectMapper.readValue(file.getInputStream(), DataFile.class);
		return Optional.ofNullable(dataFile);
	}

}
