package vn.iostar.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.iostar.entity.Category;


import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByNameContaining(String name);
	Page<Category> findByNameContaining(String name,Pageable Pageable);
	
	Optional<Category> findByName(String name);
}
