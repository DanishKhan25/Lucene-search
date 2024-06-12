package com.example.lucene.controller;

import com.example.lucene.modal.domain.Chat;
import com.example.lucene.modal.domain.Order;
import com.example.lucene.service.chat.ChatService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private  ChatService chatService;


    @PostMapping("/index")
    public String indexDocument(@RequestBody Chat chat) throws IOException {
        chatService.addDocument(chat);
        return "Document Indexed Successfully";
    }
    //search for all indexed documents
    @GetMapping
    public List<Chat> search(@RequestParam String query) throws IOException, ParseException {
        return chatService.search(query);
    }
}
