package com.amaro.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Product {
	
	@Id
	private Integer id;
	@NotBlank
	private String name;
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Tag> tags;

}
