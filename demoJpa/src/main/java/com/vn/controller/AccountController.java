package com.vn.controller;

import com.google.common.base.Strings;
import com.vn.common.Constants;
import com.vn.jpa.AuthUser;
import com.vn.jpa.Role;
import com.vn.model.AuthUserModel;
import com.vn.model.UserAccountSearchingForm;
import com.vn.service.AuthRoleService;
import com.vn.service.AuthUserService;
import com.vn.validation.service.UserFormValidator;
import org.apache.commons.collections4.map.HashedMap;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/account/")
public class AccountController {

	@Resource
	private AuthUserService authUserService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Resource
	private AuthRoleService authRoleService;

	@Resource
	private UserFormValidator userFormValidator;

	@RequestMapping(value = "list.html")
	@PreAuthorize("hasAnyAuthority('Administrators')")
	public String listUserAccount(Model model, HttpSession session, HttpServletRequest request, Pageable pageable,
			@RequestParam(value = "user_list_ss", defaultValue = "") String user_list_ss,
			@ModelAttribute(value = "userSearchingForm") @Valid UserAccountSearchingForm userSearchingForm,
			BindingResult bindingResult) {
		String not_found_message = "";
		model.addAttribute("userSearchingForm", userSearchingForm);
		if (bindingResult.hasErrors()) {
			Page<AuthUserModel> pageTop = new PageImpl<>(new ArrayList<AuthUserModel>());
			model.addAttribute("page", pageTop);
			return "admin/account/user_list";
		}

		if (request.getMethod().equalsIgnoreCase("GET")) {
			String userListSs = (String) session.getAttribute("user_list_ss");
			userSearchingForm = (UserAccountSearchingForm) session.getAttribute(userListSs);
			if (userSearchingForm == null) {
				userSearchingForm = new UserAccountSearchingForm();
			}
		}
		String userName = userSearchingForm.getUserName();
		String email = userSearchingForm.getEmail();
		Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "userName"));
		Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
		Page<AuthUserModel> pageTop = authUserService.listUsers(userName, email, _pageable);
		if (request.getMethod().equalsIgnoreCase("POST") && pageTop.getContent().size() == 0) {
			not_found_message = "Không tìm thấy dữ liệu.";
		}
		Map<Byte, String> user_status = new HashedMap<>();
		user_status.put((byte) 0, "Tạm khóa");
		user_status.put((byte) 1, "Hoạt động");

		Map<Byte, String> user_types = new HashedMap<>();
		user_types.put((byte) 0, "Admin");
		user_types.put((byte) 1, "Nhân viên");
		user_types.put((byte) 2, "Khách hàng");

		session.removeAttribute(user_list_ss);
		user_list_ss = UUID.randomUUID().toString();
		session.setAttribute("user_list_ss", user_list_ss);
		session.setAttribute(user_list_ss, userSearchingForm);
		request.getSession().setAttribute("pageIndex", pageable.getPageNumber());

		model.addAttribute("userSearchingForm", userSearchingForm);
		model.addAttribute("user_list_ss", user_list_ss);
		model.addAttribute("page", pageTop);
		model.addAttribute("user_status", user_status);
		model.addAttribute("user_types", user_types);
		model.addAttribute("not_found_message", not_found_message);
		return "admin/account/user_list";
	}

	@RequestMapping(value = "add.html", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('Administrators')")
	public String userAdd(Model model) {
		List<Role> allRoles = authRoleService.findAll();
		AuthUserModel user = new AuthUserModel();
		user.setStatus((byte) 1);
		user.setIsVerified((byte) 1);
		user.setPassword(Constants.DEFAULT_USER_PASS);
		user.setVerifyPassword(Constants.DEFAULT_USER_PASS);
		model.addAttribute("user", user);
		model.addAttribute("allRoles", allRoles);
		return "admin/account/user_add";
	}

	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@PreAuthorize("hasAnyAuthority('Administrators')")
	public String addUser(Model model, HttpSession session, @ModelAttribute(value = "user") @Valid AuthUserModel user,
			BindingResult result) {
		userFormValidator.validateUserForm(user, result);
		if (result.hasErrors()) {
			List<Role> allRoles = authRoleService.findAll();
			model.addAttribute("user", user);
			model.addAttribute("allRoles", allRoles);
			return "admin/account/user_add";
		}
		List<Long> roleIds = user.getRoles();
		List<Role> roles = new ArrayList<>();
		if (roleIds != null) {
			for (Long roleId : roleIds) {
				Role eachRole = authRoleService.findOne(roleId);
				roles.add(eachRole);
			}
		}
		Date createdDate = new DateTime().toDate();
		String password = user.getPassword();
		String salt = "5876695f8e4e1811";
		String encryptPassword = "";
		encryptPassword = passwordEncoder.encode(password);
		AuthUser authUser = new AuthUser();
		authUser.setCreatedDate(createdDate);
		authUser.setEmail(user.getEmail());
		authUser.setFirstName(user.getFirstName());
		authUser.setMiddleName(user.getMiddleName());
		authUser.setLastName(user.getLastName());
		authUser.setFullName(user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName());
		authUser.setGender(user.getGender());
		authUser.setIsVerified(user.getIsVerified());
		authUser.setModifiedDate(null);
		authUser.setUserName(user.getUserName());
		authUser.setSalt(salt);
		authUser.setPassword(encryptPassword);
		authUser.setStatus(user.getStatus());
		authUser.setUserType(user.getUserType());
		authUser.setAuthRoles(roles);
		authUserService.create(authUser);

		return "redirect:/account/list.html";
	}

	@RequestMapping(value = "{id}/update.html", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('Administrators')")
	public String updateAccount(Model model, @PathVariable("id") Long id) {

		AuthUser authUser = authUserService.findOne(id);
		if (authUser == null) {
			return "403";
		}
		List<Long> userRoleIds = authUserService.findRolesByUserId(id);
		AuthUserModel user = new AuthUserModel();
		user.setId(id);
		user.setFirstName(authUser.getFirstName());
		user.setMiddleName(authUser.getMiddleName());
		user.setLastName(authUser.getLastName());
		user.setGender(authUser.getGender());
		user.setEmail(authUser.getEmail());
		user.setUserName(authUser.getUserName());
		user.setIsVerified(authUser.getIsVerified());
		user.setStatus(authUser.getStatus());
		user.setUserType(authUser.getUserType());
		user.setRoles(userRoleIds);

		List<Role> allRoles = authRoleService.findAll();

		model.addAttribute("user", user);
		model.addAttribute("allRoles", allRoles);

		return "admin/account/user_update";
	}

	@RequestMapping(value = "{id}/update.html", method = RequestMethod.POST)
	@PreAuthorize("hasAnyAuthority('Administrators')")
	public String updateAccount(Model model, HttpSession session, @PathVariable("id") Long id,
			@ModelAttribute(value = "user") @Valid AuthUserModel user, BindingResult result) {
		AuthUser authUser = authUserService.findOne(id);
		if (authUser == null) {
			return "404";
		}
		user.setId(id);
		userFormValidator.validateUserForm(user, result);
		if (result.hasErrors()) {
			List<Role> allRoles = authRoleService.findAll();
			model.addAttribute("user", user);
			model.addAttribute("allRoles", allRoles);
			return "admin/account/user_update";
		}
		List<Long> listRole = user.getRoles();
		List<Role> roles = new ArrayList<>();
		if (listRole != null) {
			for (Long each : listRole) {
				Role eachRole = authRoleService.findOne(each);
				roles.add(eachRole);
			}
		}
		Date createdDate = new DateTime().toDate();
		authUser.setCreatedDate(createdDate);
		authUser.setEmail(user.getEmail());
		authUser.setFirstName(user.getFirstName());
		authUser.setMiddleName(user.getMiddleName());
		authUser.setLastName(user.getLastName());
		authUser.setFullName(user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName());
		authUser.setGender(user.getGender());
		authUser.setIsVerified(user.getIsVerified());
		authUser.setModifiedDate(null);
		authUser.setUserName(user.getUserName());
		authUser.setStatus(user.getStatus());
		authUser.setUserType(user.getUserType());
		authUser.setAuthRoles(roles);
		authUserService.update(authUser);

		return "redirect:/account/list.html";
	}

	@RequestMapping(value = "delete/{username}/list.html", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('Administrators')")
	public String deleteAccount(@PathVariable("username") String username) {
		AuthUser authUser = authUserService.findByUsername(username);
		if (authUser == null) {
			return "403";
		}
		authUserService.delete(authUser);
		return "redirect:/account/list.html";
	}

	@RequestMapping(value = "reset/{username}/list.html", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('Administrators')")
	public String resetAccount(@PathVariable("username") String username) {
		AuthUser authUser = authUserService.findByUsername(username);
		if (authUser == null) {
			return "403";
		}
		String passwordEncrypt = "";
		String password = Constants.DEFAULT_USER_PASS;
		if (!Strings.isNullOrEmpty(password)) {
			passwordEncrypt = passwordEncoder.encode(password);
		}
		authUser.setPassword(passwordEncrypt);
		authUserService.update(authUser);
		return "redirect:/account/list.html";
	}

	@RequestMapping(value = "change_password.html", method = RequestMethod.GET)
	public String changePassword(Model model) {
		AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", authUser.getUserName());
		model.addAttribute("old_password", "");
		model.addAttribute("new_password", "");
		model.addAttribute("verify_password", "");
		return "admin/change_password";
	}

	@RequestMapping(value = "change_password.html", method = RequestMethod.POST)
	public String changePassword(Model model, @RequestParam("username") String username,
			@RequestParam("old_password") String oldPass, @RequestParam("new_password") String newPass,
			@RequestParam("verify_password") String vertifyPass) {
		try {
			AuthUser authUser = authUserService.findByUsername(username);
			if (passwordEncoder.matches(oldPass, authUser.getPassword()) && newPass.equals(vertifyPass)) {
				String passwordNewEntrypt = passwordEncoder.encode(newPass);
				authUser.setPassword(passwordNewEntrypt);
				authUserService.update(authUser);
				return "redirect:/logout.html";
			} else {
				model.addAttribute("username", username);
				model.addAttribute("old_password", oldPass);
				model.addAttribute("new_password", newPass);
				model.addAttribute("verify_password", vertifyPass);
				return "admin/change_password";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "admin/login";
		}
	}

	@RequestMapping(value = "save.html", method = RequestMethod.POST)
	public String addUserCustomer(Model model, HttpSession session,
			@ModelAttribute(value = "athUser") @Valid AuthUserModel user, BindingResult result) {
		Map<String, String> mapError = new HashedMap<String, String>();
		try {
			userFormValidator.validateUserForm(user, result);
			if (result.hasErrors()) {
				model.addAttribute("athUser", user);
				for (Object obj : result.getAllErrors()) {
					if (obj instanceof ObjectError) {
						mapError.put(((ObjectError) obj).getCode(), ((ObjectError) obj).getDefaultMessage());
					}
				}
				model.addAttribute("mapError", mapError);
				return "/home/login";
			}
			List<Role> roles = new ArrayList<>();
			roles.add(authRoleService.findOne(2l));
			Date createdDate = new DateTime().toDate();
			String password = user.getPassword();
			String salt = "5876695f8e4e1811";
			String encryptPassword = "";
			encryptPassword = passwordEncoder.encode(password);
			AuthUser authUser = new AuthUser();
			authUser.setCreatedDate(createdDate);
			authUser.setEmail(user.getEmail());
			authUser.setFirstName(user.getFirstName());
			authUser.setMiddleName(user.getMiddleName());
			authUser.setLastName(user.getLastName());
			authUser.setFullName(user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName());
			authUser.setGender(user.getGender());
			authUser.setIsVerified((byte) 1);
			authUser.setModifiedDate(null);
			authUser.setUserName(user.getUserName());
			authUser.setSalt(salt);
			authUser.setPassword(encryptPassword);
			authUser.setStatus((byte) 1);
			authUser.setUserType((byte) 2);
			authUser.setAuthRoles(roles);
			authUserService.create(authUser);
			session.setAttribute("userLogin", authUser);
			return "redirect:/";
		} catch (Exception e) {
			mapError.put("errorUnknown","Lỗi không xác định");
			model.addAttribute("mapError", mapError);
			return null;
		}
	}

}
