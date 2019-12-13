package com.vn.validation;


import java.util.regex.Pattern;

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
		return InfomationModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InfomationModel infomation = (InfomationModel) target;
		if(infomation!=null) {
			this.phoneValidate(infomation.getPhone(), errors, infomation);
			if (this.isBlank(infomation.getFirstName())) {
				errors.reject("firstName", "Không được để trống họ !");
			}
			if (this.isBlank(infomation.getLastName())) {
				errors.reject("lastName", "Không được để trống tên !");
			}
			if(this.isBlank(infomation.getGender())) {
				errors.reject("gender", "Không được để trống giới tính !");
			}
		}
	}
	

	private boolean isBlank(String string) {
		return string == null || string.trim().isEmpty();
	}
	
	private void phoneValidate(String phone, Errors errors, InfomationModel infomation) {
        String phoneExist = infomation.getPhone();
     
        this.infomationService.findByPhone(phoneExist);
        
        String phonePattern = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
        Pattern pattern = Pattern.compile(phonePattern);
        if (this.isBlank(phone)) {
            errors.reject("phone", "Không được để trống số điện thoại !");
        }else if (!pattern.matcher(phone.trim()).matches()) {
            errors.reject("phone", "Số điện thoại không đúng định dạng !");
        }
        else if (this.infomationService.findByPhone(infomation.getPhone()) != null && !phone.equals(phoneExist)) {
            errors.reject("phone", "Số điện thoại đã tồn tại !");
        }

    }

}
