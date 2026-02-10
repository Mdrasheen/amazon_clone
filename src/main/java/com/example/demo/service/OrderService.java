package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.model.CartItem;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // ================= USER ORDER =================

    public Order placeOrder(
            String userEmail,
            List<CartItem> cartItems,
            String paymentMethod
    ) {

        double totalAmount = 0;
        for (CartItem item : cartItems) {
            totalAmount += item.getProduct().getPrice() * item.getQuantity();
        }

        Order order = new Order();
        order.setUserEmail(userEmail);
        order.setTotalAmount(totalAmount);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        order.setPaymentMethod(paymentMethod);

        // Payment status logic
        if ("COD".equals(paymentMethod)) {
            order.setPaymentStatus("PENDING");
        } else {
            order.setPaymentStatus("PAID"); // mock success
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem(
                    item.getProduct().getName(),
                    item.getProduct().getPrice(),
                    item.getQuantity(),
                    order
            );
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        return orderRepository.save(order);
    }

    // ================= USER VIEW =================

    public List<Order> getOrdersByUser(String userEmail) {
        return orderRepository.findByUserEmailOrderByOrderDateDesc(userEmail);
    }

    // ================= ADMIN =================

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }

    public void save(Order order) {
        orderRepository.save(order);
    }
}
