package vn.iostar.service;

import java.util.List;
import java.util.Optional;

import vn.iostar.entity.Product;

public interface ProductService {

	void deleteById(Long id);

	Optional<Product> findById(Long id);

	List<Product> findAll();

	<S extends Product> S save(S entity);

}
