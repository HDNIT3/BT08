package vn.iostar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.iostar.entity.Category;

import java.awt.print.Pageable;
import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByNameContaining(String name);
	List<Category> findByNameContaining(String name,Pageable Pageable);
}
