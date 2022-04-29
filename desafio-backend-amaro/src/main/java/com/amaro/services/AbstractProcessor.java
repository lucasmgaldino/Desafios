package com.amaro.services;

import java.util.HashSet;
import java.util.Set;

import com.amaro.entities.Tag;
import com.amaro.persistence.TagRepository;

public abstract class AbstractProcessor implements Processor {
	
	private final TagRepository tagRepository;
	
	protected AbstractProcessor(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}

	protected Set<Tag> processTags(Set<String> tagNames) {
		Set<Tag> tags = new HashSet<>();
		for (String name : tagNames) {
			Tag tag = this.tagRepository.saveIfNotExist(new Tag(name));
			tags.add(tag);
		}
		return tags;
	}

}
