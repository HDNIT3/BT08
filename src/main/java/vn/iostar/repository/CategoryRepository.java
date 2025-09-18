package vn.iostar.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.iostar.entity.Category;


import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByNameContaining(String name);
	Page<Category> findByNameContaining(String name,Pageable Pageable);
}
