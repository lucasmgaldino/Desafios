package com.amaro.services;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.amaro.entities.Product;
import com.amaro.entities.Tag;
import com.amaro.persistence.ProductRepository;
import com.amaro.persistence.TagRepository;

import lombok.SneakyThrows;

@Component
public class XMLFileProcessor extends AbstractProcessor {
	
	private final ProductRepository productRepository;
	
	public XMLFileProcessor(ProductRepository productRepository, TagRepository tagRepository) {
		super(tagRepository);
		this.productRepository = productRepository;
	}
	
	@Override
	public final boolean accept(String contentType) {
		if (!StringUtils.hasText(contentType)) {
			return false;
		}
		return MediaType.APPLICATION_XML_VALUE.equalsIgnoreCase(contentType);
	}

	@SneakyThrows
	@Override
	public Set<Product> process(InputStream inputStream) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		document.getDocumentElement().normalize();
		
		Element root = document.getDocumentElement();
		NodeList elementsList = root.getChildNodes();

		Set<Product> products = new HashSet<>();
		for (int temp = 0; temp < elementsList.getLength(); temp++) {
			Node node = elementsList.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				Integer id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
				String name = element.getElementsByTagName("name").item(0).getTextContent();
				Set<String> tagsName = extractTagsName(element.getElementsByTagName("tags").item(0));
				Set<Tag> tags = processTags(tagsName);
				Product persistedProduct = this.productRepository.save(new Product(id, name, tags));
				products.add(persistedProduct);
			}
		}
		return products;
	}
	
	private Set<String> extractTagsName(Node tagElement) {
		Set<String> tags = new HashSet<>();
		NodeList tagsList = tagElement.getChildNodes();
		for (int i = 0; i < tagsList.getLength(); i++) {
			Node node = tagsList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				tags.add(element.getTextContent());
			}
		}
		return tags;
	}

}
