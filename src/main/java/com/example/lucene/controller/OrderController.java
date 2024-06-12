package com.example.lucene.controller;

import com.example.lucene.modal.domain.Order;
import com.example.lucene.service.order.OrderService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/index")
    public String indexDocument(@RequestBody Order order) throws IOException {
        orderService.addDocument(order);
        return "Document Indexed Successfully";
    }
    //search for all indexed documents
    @GetMapping
    public List<Order> search(@RequestParam String query) throws IOException, ParseException {
        return orderService.search(query);
    }
}
