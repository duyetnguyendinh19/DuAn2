package com.vn.validation;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vn.jpa.Infomation;
import com.vn.model.InfomationModel;
import com.vn.service.InfomationService;
import com.vn.validation.service.InfomationFormValidator;

@Service(value = "infoFormValidator")
public class InfomationFormValidatorImpl implements Validator, InfomationFormValidator {

	@Resource
	private InfomationService infomationService;
	
	@Override
	public void validateReportForm(Object var1, Errors var2) {
		this.validate(var1, var2);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Infomation.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InfomationModel infomation = (InfomationModel) target;
		this.phoneValidate(infomation.getPhone(), errors, infomation);
	}
	

	private boolean isBlank(String string) {
		return string == null || string.trim().isEmpty();
	}
	
	private void phoneValidate(String phone, Errors errors, InfomationModel infomation) {
        String phoneExist = null;
        if (infomation != null) {
        	phoneExist = infomation.getPhone();
        }

//        String phonePattern = "";
//        Pattern pattern = Pattern.compile(phone);
        if (this.isBlank(phone)) {
            errors.reject("phone", "Không được để trống số điện thoại !");
        }
        else if (this.infomationService.findByPhone(infomation.getPhone()) != null && !phone.equals(phoneExist)) {
            errors.reject("phone", "Số điện thoại đã tồn tại !");
        }

    }

}
