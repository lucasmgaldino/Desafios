package com.amaro.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.amaro.entities.Product;
import com.amaro.entities.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class DTOProduct {

	@NotNull
	@Positive
	private Integer id;
	@NotBlank
	private String name;
	@NotEmpty
	private Set<String> tags;

	public static DTOProduct from(Product product) {
		return new DTOProduct(product.getId(), product.getName(),
				new HashSet<>(product.getTags().stream().map(Tag::getName).toList()));
	}

}
