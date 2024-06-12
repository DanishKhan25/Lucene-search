package com.example.lucene.controller;
import com.example.lucene.modal.domain.Order;
import com.example.lucene.service.IndexService;
import com.example.lucene.service.SearchService;
import jakarta.validation.Valid;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    private IndexService indexService;

    @Autowired
    private SearchService searchService;


    //indexing for all documents
    @PostMapping("/index")
    public String indexDocument(@RequestParam String id, @RequestParam String content) throws IOException {
        indexService.addDocument(id, content);
        return "Document Indexed Successfully";
    }

    @PostMapping("/index/order")
    public String indexDocument(@RequestBody  Order order) throws IOException {
        indexService.addDocument(order);
        return "Document Indexed Successfully";
    }
    //search for all indexed documents
    @GetMapping
    public List<Document> search(@RequestParam String query) throws IOException, ParseException {
        return searchService.search(query);
    }
}
