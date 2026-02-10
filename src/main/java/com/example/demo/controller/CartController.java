package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductRepository productRepository;

    public CartController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ================= VIEW CART =================
    @GetMapping
    public String viewCart(HttpSession session, Model model) {

        List<CartItem> cart = getCart(session);

        double total = cart.stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);

        return "cart";
    }

    // ================= ADD TO CART =================
    @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            HttpSession session
    ) {
        List<CartItem> cart = getCart(session);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + 1);
                session.setAttribute("cart", cart);
                return "redirect:/cart";
            }
        }

        cart.add(new CartItem(product, 1));
        session.setAttribute("cart", cart);

        return "redirect:/cart";
    }

    // ================= REMOVE FROM CART =================
    @GetMapping("/remove/{productId}")
    public String removeFromCart(
            @PathVariable Long productId,
            HttpSession session
    ) {
        List<CartItem> cart = getCart(session);
        cart.removeIf(item -> item.getProduct().getId().equals(productId));
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    // ================= HELPER =================
    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
