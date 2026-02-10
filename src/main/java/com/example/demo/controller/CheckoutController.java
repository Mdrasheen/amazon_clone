package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Order;
import com.example.demo.model.CartItem;
import com.example.demo.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CheckoutController {

    private final OrderService orderService;

    public CheckoutController(OrderService orderService) {
        this.orderService = orderService;
    }

    // SHOW CHECKOUT PAGE
    @GetMapping("/checkout")
    public String showCheckout(HttpSession session, Model model) {

        @SuppressWarnings("unchecked")
        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        double total = cart.stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);

        return "checkout";
    }

    // PLACE ORDER
    @PostMapping("/checkout")
    public String placeOrder(
            @RequestParam String paymentMethod,
            Principal principal,
            HttpSession session
    ) {
        String userEmail = principal.getName();

        @SuppressWarnings("unchecked")
        List<CartItem> cartItems =
                (List<CartItem>) session.getAttribute("cart");

        if (cartItems == null || cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        Order order = orderService.placeOrder(
                userEmail,
                cartItems,
                paymentMethod
        );

        // store last order for success page
        session.setAttribute("lastOrder", order);

        // clear cart
        session.removeAttribute("cart");

        return "redirect:/order-success";
    }

    // ORDER SUCCESS PAGE
    @GetMapping("/order-success")
    public String orderSuccess(HttpSession session, Model model) {
        Order order = (Order) session.getAttribute("lastOrder");
        model.addAttribute("order", order);
        session.removeAttribute("lastOrder");
        return "order-success";
    }
}
