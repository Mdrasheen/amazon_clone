package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Product;
import com.example.demo.model.CartItem;
import com.example.demo.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        Product product = productService.getProductById(id);

        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(id)) {
                item.increaseQuantity();
                session.setAttribute("cart", cart);
                return "redirect:/cart";
            }
        }

        cart.add(new CartItem(product, 1));
        session.setAttribute("cart", cart);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        model.addAttribute("cart", cart);
        return "cart";
    }

    // ➕ Increase quantity
    @GetMapping("/cart/increase/{id}")
    public String increaseQuantity(@PathVariable Long id, HttpSession session) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getProduct().getId().equals(id)) {
                    item.increaseQuantity();
                    break;
                }
            }
        }

        return "redirect:/cart";
    }

    // ➖ Decrease quantity
    @GetMapping("/cart/decrease/{id}")
    public String decreaseQuantity(@PathVariable Long id, HttpSession session) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getProduct().getId().equals(id)) {
                    item.decreaseQuantity();
                    break;
                }
            }
        }

        return "redirect:/cart";
    }

    // ❌ Remove item
    @GetMapping("/cart/remove/{id}")
    public String removeItem(@PathVariable Long id, HttpSession session) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            Iterator<CartItem> iterator = cart.iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.getProduct().getId().equals(id)) {
                    iterator.remove();
                    break;
                }
            }
        }

        return "redirect:/cart";
    }
}
