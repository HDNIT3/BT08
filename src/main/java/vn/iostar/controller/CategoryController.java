package vn.iostar.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.iostar.entity.Category;
import vn.iostar.model.CategoryModel;
import vn.iostar.service.CategoryService;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {
	@Autowired
	CategoryService cateSer;

	@GetMapping("add")
	public String add(Model model) {
		CategoryModel cate = new CategoryModel();
		cate.setEdit(false);
		model.addAttribute("category", cate);
		return "admin/categories/addOrEdit";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(Model model, @Validated @ModelAttribute("category") CategoryModel cateModel,
			BindingResult result) {

		if (result.hasErrors()) {
			return new ModelAndView("admin/categories/addOrEdit");
		}

		Category category = new Category();
		BeanUtils.copyProperties(cateModel, category);
		cateSer.save(category);
		if (cateModel.getEdit() == true) {
			model.addAttribute("message", "Chỉnh Sửa thành công!");
		} else {
			model.addAttribute("message", "Lưu thành công!");
		}
		return new ModelAndView("redirect:/admin/categories/searchpaginated");
	}

	@GetMapping("edit/{categoryId}")
	public ModelAndView edit(ModelMap model, @PathVariable("categoryId") Long categoryId) {
		Optional<Category> optCategory = cateSer.findById(categoryId);
		CategoryModel cateModel = new CategoryModel();

		// kiểm tra sự tồn tại của category
		if (optCategory.isPresent()) {
			Category entity = optCategory.get();

			// copy từ entity sang cateModel
			BeanUtils.copyProperties(entity, cateModel);
			cateModel.setEdit(true);

			// đẩy dữ liệu ra view
			model.addAttribute("category", cateModel);

			return new ModelAndView("admin/categories/addOrEdit", model);
		}

		model.addAttribute("message", "Category is not existed!!!!");
		return new ModelAndView("forward:/admin/categories", model);
	}

}
