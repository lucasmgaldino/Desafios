package com.amaro.persistence;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amaro.entities.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

	@Transactional(readOnly = true)
	@Cacheable(cacheNames = "tag", key = "#name", unless = "#result == null")
	Optional<Tag> findByNameIgnoreCase(String name);

	@Cacheable(cacheNames = "tag", key = "#tag.name", unless = "#result == null")
	default Tag saveIfNotExist(Tag tag) {
		return this.findByNameIgnoreCase(tag.getName()).orElseGet(() -> this.save(tag));
	}

}
