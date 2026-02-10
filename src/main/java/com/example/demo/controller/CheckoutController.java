package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.CartItem;
import com.example.demo.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CheckoutController {

    private final OrderService orderService;

    public CheckoutController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/products";
        }

        double total = 0;
        for (CartItem item : cart) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);

        return "checkout";
    }

    @PostMapping("/place-order")
    public String placeOrder(HttpSession session, Authentication authentication, Model model) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/products";
        }

        String userEmail = authentication.getName();

        orderService.placeOrder(userEmail, cart);

        session.removeAttribute("cart");

        model.addAttribute("message", "Order placed successfully!");

        return "order-success";
    }
}
