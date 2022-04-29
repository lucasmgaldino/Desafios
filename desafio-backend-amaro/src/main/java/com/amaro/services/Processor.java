package com.amaro.services;

import java.io.InputStream;
import java.util.Set;

import com.amaro.entities.Product;

public interface Processor {
	
	boolean accept(String contentType);
	
	Set<Product> process(InputStream inputStream);

}
