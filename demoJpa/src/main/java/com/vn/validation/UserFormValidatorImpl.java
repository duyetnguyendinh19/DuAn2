package com.vn.validation;

import com.google.common.base.Strings;
import com.vn.jpa.AuthUser;
import com.vn.model.AuthUserModel;
import com.vn.service.AuthUserService;
import com.vn.validation.service.UserFormValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.regex.Pattern;

@Service(value="userFormValidator")
public class UserFormValidatorImpl implements Validator, UserFormValidator {

    @Resource
    private AuthUserService authUserService;

    @Override
    public void validateUserForm(Object var1, Errors var2) {
        this.validate(var1,var2);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AuthUserModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthUserModel user = (AuthUserModel) target;
        Long userId = user.getId();
        AuthUser authUser = null;
        if (userId != null) {
            authUser = (AuthUser)this.authUserService.findOne(userId);
        }

        this.firstNameValidate(user.getFirstName(), errors);
        this.middleNameValidate(user.getMiddleName(), errors);
        this.lastNameValidate(user.getLastName(), errors);
        this.emailValidate(user.getEmail(), errors, authUser);
        this.userNameValidate(user.getUserName(), errors, authUser);
        this.passwordValidate(user.getPassword(), user.getVerifyPassword(), errors, authUser);
        this.verifyPasswordValidate(user.getVerifyPassword(), user.getPassword(), errors, authUser);
        this.genderValidate(user.getGender(), errors);
    }

    private void firstNameValidate(String firstName, Errors errors) {
        
    	if(this.isBlank(firstName)) {
    		errors.reject("firstName" , "Họ không được để trống");
    	}
    	else if (this.checkLength(firstName, 15)) {
            errors.reject("firstName", "Họ không được quá 15 ký tự");
        }

    }

    private void middleNameValidate(String middleName, Errors errors) {
    	if(this.isBlank(middleName)) {
    		errors.reject("middleName" , "Tên không được để trống");
    	}
    	else if (this.checkLength(middleName, 15)) {
            errors.reject("middleName", "Tên đệm không được quá 15 ký tự");
        }

    }

    private void lastNameValidate(String lastName, Errors errors) {
    	if(this.isBlank(lastName)) {
    		errors.reject("lastName" , "Tên không được để trống");
    	}
    	else if (this.checkLength(lastName, 15)) {
            errors.reject("lastName", "Tên không được quá 15 ký tự");
        }

    }

    private void emailValidate(String email, Errors errors, AuthUser authUser) {
        String emailExist = null;
        if (authUser != null) {
            emailExist = authUser.getEmail();
        }

        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        if (this.isBlank(email)) {
            errors.reject("email", "Phải điền địa chỉ email.");
        } else if (!pattern.matcher(email.trim()).matches()) {
            errors.reject("email", "Email không đúng định dạng.");
        } else if (this.checkLength(email, 50)) {
            errors.reject("email", "Email vượt quá 50 ký tự");
        } else if (this.authUserService.findByEmail(email) != null && !email.equals(emailExist)) {
            errors.reject("email", "Email đã tồn tại");
        }

    }

    private void userNameValidate(String userName, Errors errors, AuthUser authUser) {
        String userNameExist = null;
        if (authUser != null) {
            userNameExist = authUser.getUserName();
        }

        if (this.isBlank(userName)) {
            errors.reject("userName", "Phải điền tên đăng nhập.");
        } else if (this.checkLength(userName, 45)) {
            errors.reject("userName", "Tên đăng nhập vượt quá 45 ký tự.");
        } else if (this.authUserService.findByUsername(userName) != null && !userName.equals(userNameExist)) {
            errors.reject("userName", "Tên đăng nhập đã tồn tại");
        }

    }

    private void passwordValidate(String password, String verifyPassword, Errors errors, AuthUser authUser) {
        if (this.isBlank(password) && (authUser == null || !Strings.isNullOrEmpty(verifyPassword))) {
            errors.reject("password", "Phải điền mật khẩu.");
        }

    }

    private void verifyPasswordValidate(String verifyPassword, String password, Errors errors, AuthUser authUser) {
        if (!this.isBlank(verifyPassword) || authUser != null && Strings.isNullOrEmpty(password)) {
            if (!Strings.isNullOrEmpty(password) && !this.isBlank(verifyPassword) && !verifyPassword.equals(password)) {
                errors.reject("verifyPassword", "Mật khẩu xác nhận không đúng.");
            }
        } else {
            errors.reject("verifyPassword", "Phải điền mục xác nhận mật khẩu.");
        }

    }
    
    private void genderValidate(String gender, Errors errors) {
    	if(this.isBlank(gender)) {
    		errors.reject("gender", "Phải chọn giới tính");
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
