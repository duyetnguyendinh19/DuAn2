package com.vn.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vn.jpa.Category;
import com.vn.validation.service.CategoryFormValidator;

public class CategoryFormValidatorImpl implements Validator, CategoryFormValidator {

	@Override
	public void validateCategoryForm(Object var1, Errors var2) {
		this.validateCategoryForm(var1, var2);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Category.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
	}

}
