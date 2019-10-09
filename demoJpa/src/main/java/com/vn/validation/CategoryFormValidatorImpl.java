package com.vn.validation;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vn.jpa.Category;
import com.vn.validation.service.CategoryFormValidator;

@Service("categoryFormValidator")
public class CategoryFormValidatorImpl implements Validator, CategoryFormValidator {

	@Override
	public void validateCategoryForm(Object var1, Errors var2) {
		this.validate(var1, var2);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Category.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Category category = (Category) target;
		if (this.isBlank(category.getName())) {
			errors.reject("name", "Tên danh mục không được để trống !");
		} else if (this.checkLength(category.getName(), 64)) {
			errors.reject("name", "Tên danh mục không được quá 64 ký tự !");
		}
	}

	private boolean isBlank(String string) {
		return string == null || string.trim().isEmpty();
	}

	private boolean checkLength(String field, int length) {
		if (!this.isBlank(field)) {
			field = field.trim();
			return field.length() > length;
		} else {
			return false;
		}
	}

}
