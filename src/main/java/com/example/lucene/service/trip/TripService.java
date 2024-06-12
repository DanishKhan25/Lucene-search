package com.example.lucene.service.trip;

import com.example.lucene.modal.domain.Trip;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {
    @Autowired
    private final Analyzer analyzer;
    @Autowired
    private final Directory directory;

    public TripService(Analyzer analyzer, Directory directory) {
        this.analyzer = analyzer;
        this.directory = directory;
    }

    public void addDocument(Trip trip) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        Document doc = new Document();
        doc.add(new StringField("id", trip.getId(), Field.Store.YES));
        doc.add(new TextField("source", trip.getSource(), Field.Store.YES));
        doc.add(new TextField("destination", trip.getDestination(),Field.Store.YES));
        indexWriter.addDocument(doc);
        indexWriter.close();
    }

    public List<Trip> search(String queryString) throws IOException, ParseException {
        List<Trip> result = new ArrayList<>();

        try (IndexReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"source", "destination"}, analyzer);
            Query query = parser.parse(queryString);
            TopDocs topDocs = searcher.search(query, 10);

            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                Trip trip =Trip.builder().
                        id(doc.get("id")).source(doc.get("source")).destination(doc.get("destination")).build();
                result.add(trip);
            }
        }
        return result;
    }
}
