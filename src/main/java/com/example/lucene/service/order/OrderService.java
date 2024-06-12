package com.example.lucene.service.order;

import com.example.lucene.modal.domain.Order;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
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
public class OrderService {
    @Autowired
    private Analyzer analyzer;
    @Autowired
    private Directory directory;

    public void addDocument(Order order) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        Document doc = new Document();
        String fullTextSearch= order.getProductName() + " " + order.getSource() + " " + order.getDestination();
        doc.add(new StringField("id", order.getId(), Field.Store.YES));
        doc.add(new TextField("productName", order.getProductName(), Field.Store.YES));
        doc.add(new StoredField("status", order.getOrderRequestStatus()));
        doc.add(new StoredField("source", order.getSource()));
        doc.add(new StoredField("destination", order.getDestination()));
        doc.add(new TextField("content",fullTextSearch,Field.Store.NO));
        indexWriter.addDocument(doc);
        indexWriter.close();
    }

    public List<Order> search(String queryString) throws IOException, ParseException {
        List<Order> result = new ArrayList<>();

        try (IndexReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser("content", analyzer);
            Query query = parser.parse(queryString);
            TopDocs topDocs = searcher.search(query, 10);

            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                Order order =Order.builder().
                        id(doc.get("id")).
                        productName(doc.get("productName")).
                        orderRequestStatus(doc.get("status")).
                        source(doc.get("source")).
                        destination(doc.get("destination")).
                        build();
                result.add(order);
            }
        }
        return result;
    }
}
