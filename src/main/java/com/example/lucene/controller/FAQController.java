package com.example.lucene.controller;

import com.example.lucene.modal.domain.FAQ;
import com.example.lucene.modal.domain.Order;
import com.example.lucene.service.faq.FAQService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/faq")
public class FAQController {
    @Autowired
    private final FAQService faqService;
    public FAQController(FAQService faqService) {
        this.faqService = faqService;
    }
    @PostMapping("/index")
    public String indexDocument(@RequestBody FAQ faq) throws IOException {
        faqService.addDocument(faq);
        return "Document Indexed Successfully";
    }
    //search for all indexed documents
    @GetMapping
    public List<FAQ> search(@RequestParam String query) throws IOException, ParseException {
        return faqService.search(query);
    }
}
