package com.philips.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.philips.backend.common.CategoriesWithSub;
import com.philips.backend.common.CategoriesWithSubResponse;
import com.philips.backend.common.SubCategory;
import com.philips.backend.dao.Category;
import com.philips.backend.repository.CategoryRepository;
import com.philips.backend.repository.SubCategoryRepository;

@RestController
public class CategoriesWithSubController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	SubCategoryRepository subCategoryRepository;

	private static final Logger LOGGER = Logger.getLogger(CategoriesWithSubController.class.getName());

	@Value("${com.philips.backend.categories.sub.path}")
	private String categoriesWithSubPath;

	// should be updated to return error code.
	@RequestMapping(method = RequestMethod.GET, value = "/categoriesWithSub")
	public CategoriesWithSubResponse getCategoriesWithSub() {
		CategoriesWithSubResponse categoriesWithSubResponse = new CategoriesWithSubResponse();
		List<CategoriesWithSub> categoriesWithSubList = new ArrayList<>();
		for (Category category : categoryRepository.findAll()) {
			List<SubCategory> subCategories = new ArrayList<SubCategory>();
			CategoriesWithSub categoriesWithSub = new CategoriesWithSub();
			categoriesWithSub.setCategoryName(category.getCategoryName());
			categoriesWithSub.setArCategoryName(category.getArCategoryName());
			for (com.philips.backend.dao.SubCategory subCat : subCategoryRepository.findByCategory(category)) {
				SubCategory subCategory = new SubCategory();
				subCategory.setSubCategoryName(subCat.getSubCategoryName());
				subCategory.setArSubCategoryName(subCat.getArSubCategoryName());
				subCategories.add(subCategory);
			}
			categoriesWithSub.setSubCategories(subCategories);
			categoriesWithSubList.add(categoriesWithSub);
		}
		categoriesWithSubResponse.setCategoriesWithSubs(categoriesWithSubList);
		return categoriesWithSubResponse;
	}
}
