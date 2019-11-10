package com.vn.validation.service;

import org.springframework.validation.Errors;

public interface ReportFormValidator {
	 void validateReportForm(Object var1, Errors var2);
	 
	 void validateReportFormInFooter(Object var1, Errors var2);
}
