package vn.iostar.controller.api;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import vn.iostar.entity.Product;
import vn.iostar.model.Response;
import vn.iostar.service.CategoryService;
import vn.iostar.service.IStorageService;
import vn.iostar.service.ProductService;

@RestController
@RequestMapping(path = "/api/product")
public class ProductAPIController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private IStorageService storageService;

	@GetMapping
	public ResponseEntity<?> getAllProducts() {
		return new ResponseEntity<>(new Response(true, "Thành công", productService.findAll()), HttpStatus.OK);
	}

	@GetMapping("/get")
	public ResponseEntity<?> getProductById(@RequestParam("id") Long id) {
		Optional<Product> product = productService.findById(id);
		if (product.isPresent()) {
			return new ResponseEntity<>(new Response(true, "Thành công", product.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response(false, "Không tìm thấy sản phẩm", null), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<?> addProduct(@Validated @RequestParam("productName") String productName,
			@Validated @RequestParam("quantity") int quantity, @Validated @RequestParam("unitPrice") double unitPrice,
			@Validated @RequestParam("description") String description,
			@Validated @RequestParam("discount") double discount, @Validated @RequestParam("status") short status,
			@RequestParam(value = "image", required = false) MultipartFile image,
			@Validated @RequestParam("idCate") long idcate) {
		Product product = new Product();
		product.setProductName(productName);
		product.setQuantity(quantity);
		product.setUnitPrice(unitPrice);
		product.setDescription(description);
		product.setDiscount(discount);
		product.setStatus(status);
		product.setCreateDate(new Date());
		product.setCategory(categoryService.findById(idcate).get());

		if (image != null && !image.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String fileName = storageService.getSorageFilename(image, uuid.toString());
			storageService.store(image, fileName);
			product.setImages(fileName);
		}

		productService.save(product);
		return new ResponseEntity<>(new Response(true, "Thêm sản phẩm thành công", product), HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestParam("id") Long id,
			@Validated @RequestParam("productName") String productName,
			@Validated @RequestParam("quantity") int quantity, @Validated @RequestParam("unitPrice") double unitPrice,
			@Validated @RequestParam("description") String description,
			@Validated @RequestParam("discount") double discount, @Validated @RequestParam("status") short status,
			@RequestParam(value = "image", required = false) MultipartFile image,
			@Validated @RequestParam("idCate") long idcate
			) {
		Optional<Product> optProduct = productService.findById(id);
		if (optProduct.isEmpty()) {
			return new ResponseEntity<>(new Response(false, "Không tìm thấy sản phẩm", null), HttpStatus.NOT_FOUND);
		}

		Product product = optProduct.get();
		product.setProductName(productName);
		product.setQuantity(quantity);
		product.setUnitPrice(unitPrice);
		product.setDescription(description);
		product.setDiscount(discount);
		product.setStatus(status);
		product.setCategory(categoryService.findById(idcate).get());
		

		if (image != null && !image.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String fileName = storageService.getSorageFilename(image, uuid.toString());
			storageService.store(image, fileName);
			product.setImages(fileName);
		}

		productService.save(product);
		return new ResponseEntity<>(new Response(true, "Cập nhật sản phẩm thành công", product), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteProduct(@RequestParam("id") Long id) {
		Optional<Product> optProduct = productService.findById(id);
		if (optProduct.isEmpty()) {
			return new ResponseEntity<>(new Response(false, "Không tìm thấy sản phẩm", null), HttpStatus.NOT_FOUND);
		}
		productService.deleteById(id);
		return new ResponseEntity<>(new Response(true, "Xóa sản phẩm thành công", optProduct.get()), HttpStatus.OK);
	}
}
