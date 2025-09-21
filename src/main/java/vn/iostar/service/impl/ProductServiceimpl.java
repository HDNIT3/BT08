package vn.iostar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iostar.entity.Product;
import vn.iostar.repository.ProductRepository;
import vn.iostar.service.ProductService;

@Service
public class ProductServiceimpl implements ProductService{
	@Autowired
	ProductRepository proRes;

	@Override
	public <S extends Product> S save(S entity) {
		return proRes.save(entity);
	}

	@Override
	public List<Product> findAll() {
		return proRes.findAll();
	}

	@Override
	public Optional<Product> findById(Long id) {
		return proRes.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		proRes.deleteById(id);
	}
}
