package com.amaro.persistence;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amaro.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT p FROM Product p JOIN p.tags t WHERE (:name is null or upper(p.name) like upper(concat('%',:name,'%'))) AND (:tag is null or upper(t.name) = upper(:tag))")
	Set<Product> findProductByNameAndTag(@Param("name") String name, @Param("tag") String tag);

}
