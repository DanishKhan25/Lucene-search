package com.tvelp.lucene.controller;

import com.tvelp.lucene.modal.domain.Trip;
import com.tvelp.lucene.service.trip.TripService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/trip")
public class TripController {
    @Autowired
    private TripService tripService;
    @PostMapping("/index")
    public String indexDocument(@RequestBody Trip trip) throws IOException {
        tripService.addDocument(trip);
        return "Document Indexed Successfully";
    }
    //search for all indexed documents
    @GetMapping
    public List<Trip> search(@RequestParam String query) throws IOException, ParseException {
        return tripService.search(query);
    }
}
