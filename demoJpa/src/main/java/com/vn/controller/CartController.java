package com.vn.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.jpa.Product;
import com.vn.model.Cart;
import com.vn.service.ProductService;

@Controller
@RequestMapping(value = "/cart/")
public class CartController {

	@Resource
	private ProductService productSerivce;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "add/{productId}.html", method = RequestMethod.GET)
	public @ResponseBody
	void viewAdd(ModelMap mm, HttpSession session, @PathVariable("productId") long productId,@RequestParam(value = "quantity", defaultValue = "1") int quantity) {
		HashMap<Long, Cart> cartItems = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
		if (cartItems == null) {
			cartItems = new HashMap<>();
		}
		Product product = productSerivce.findOne(productId);
		if (product != null) {
			if (cartItems.containsKey(productId)) {
				Cart item = cartItems.get(productId);
				item.setProduct(product);
				item.setQuantity(item.getQuantity() + 1);
				cartItems.put(productId, item);
			} else {
				Cart item = new Cart();
				item.setProduct(product);
				item.setQuantity(1);
				cartItems.put(productId, item);
			}
		}
		String size;
		if (cartItems.size() < 10) {
			size = "0" + cartItems.size();
		} else {
			size = String.valueOf(cartItems.size());
		}
		session.setAttribute("myCartItems", cartItems);
		session.setAttribute("myCartTotal", totalPrice(cartItems));
		session.setAttribute("myCartNum", size);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "sub/{productId}.html", method = RequestMethod.GET)
	public String viewUpdate(ModelMap mm, HttpSession session, @PathVariable("productId") long productId) {
		HashMap<Long, Cart> cartItems = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
		if (cartItems == null) {
			cartItems = new HashMap<>();
		}
		Product product = productSerivce.findOne(productId);
		if (product != null) {
			if (cartItems.containsKey(productId)) {
				Cart item = cartItems.get(productId);
				if(item.getQuantity() > 1) {
					item.setProduct(product);
					item.setQuantity(item.getQuantity() - 1);
					cartItems.put(productId, item);
				}else {
					cartItems.remove(productId);
				}
			} 
		}
		String size;
		if (cartItems.size() < 10) {
			size = "0" + cartItems.size();
		} else {
			size = String.valueOf(cartItems.size());
		}
		session.setAttribute("myCartItems", cartItems);
		session.setAttribute("myCartTotal", totalPrice(cartItems));
		session.setAttribute("myCartNum", size);
		return "redirect:/";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "remove/{productId}.html", method = RequestMethod.GET)
	public String viewRemove(ModelMap mm, HttpSession session, @PathVariable("productId") long productId) {
		HashMap<Long, Cart> cartItems = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
		if (cartItems == null) {
			cartItems = new HashMap<>();
		}
		if (cartItems.containsKey(productId)) {
			cartItems.remove(productId);
		}
		String size;
		if (cartItems.size() < 10) {
			size = "0" + cartItems.size();
		} else {
			size = String.valueOf(cartItems.size());
		}
		session.setAttribute("myCartItems", cartItems);
		session.setAttribute("myCartTotal", totalPrice(cartItems));
		session.setAttribute("myCartNum", size);
		return "redirect:/";
	}

	public double totalPrice(HashMap<Long, Cart> cartItems) {
		int count = 0;
		for (Map.Entry<Long, Cart> list : cartItems.entrySet()) {
			count += list.getValue().getProduct().getPrice() * list.getValue().getQuantity();
		}
		return count;
	}

}
