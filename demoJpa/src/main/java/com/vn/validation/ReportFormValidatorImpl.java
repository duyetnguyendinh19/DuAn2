package com.vn.validation;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vn.jpa.Report;
import com.vn.validation.service.ReportFormValidator;

@Service(value="reportFormValidator")
public class ReportFormValidatorImpl implements Validator, ReportFormValidator {

	int INDEX = -1;
	
	@Override
	public void validateReportForm(Object var1, Errors var2) {
		INDEX = 1;
		this.validate(var1, var2);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Report.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Report report = (Report) target;
		if (this.isBlank(report.getName())) {
			errors.reject("name", "Không được để trống tên !");
		}
		if (this.isBlank(report.getOpinion())) {
			errors.reject("opinion", "Không được để trống ý kiến !");
		}
		this.emailValidate(report.getEmail(),errors);
		if(INDEX == 1) {
			if (this.isBlank(report.getMobile())) {
				errors.reject("mobile", "Không được để trống số điện thoại !");
			}
			if (this.isBlank(report.getProblem())) {
				errors.reject("problem", "Không được để trống vấn đề !");
			}
		}
	}
	

	@Override
	public void validateReportFormInFooter(Object var1, Errors var2) {
		INDEX = 2;
		this.validate(var1, var2);
	}

	private boolean isBlank(String string) {
		return string == null || string.trim().isEmpty();
	}

	private void emailValidate(String email, Errors errors) {
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(emailPattern);
		if (this.isBlank(email)) {
			errors.reject("email", "Không được để trống Email !");
		} else if (!pattern.matcher(email.trim()).matches()) {
			errors.reject("email", "Email không đúng định dạng !");
		}
	}


}
