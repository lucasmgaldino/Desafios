package com.amaro.persistence;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amaro.entities.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
	
	@Cacheable(cacheNames = "tag", key = "#name", unless = "#result == null")
	Optional<Tag> findByNameIgnoreCase(String name);
	
	default Tag saveIfNotExist(Tag tag) {
		return this.findByNameIgnoreCase(tag.getName()).orElseGet(() -> this.save(tag));
	}
	
}
