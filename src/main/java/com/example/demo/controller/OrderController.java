package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/my-orders")
    public String myOrders(Authentication authentication, Model model) {

        String userEmail = authentication.getName();
        List<Order> orders = orderService.getOrdersByUser(userEmail);

        model.addAttribute("orders", orders);
        return "my-orders";
    }
}
