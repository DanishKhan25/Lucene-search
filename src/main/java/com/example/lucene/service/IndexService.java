package com.example.lucene.service;

import com.example.lucene.modal.domain.Order;
import jakarta.annotation.PostConstruct;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class IndexService {
     @Autowired
     private Analyzer analyzer;
     @Autowired
     private Directory directory;

   public void addDocument(String id, String content) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        System.out.println("stored: " + directory);
        Document doc = new Document();
        doc.add(new StringField("id", id, Field.Store.YES));
        doc.add(new TextField("content", content, Field.Store.YES));
        indexWriter.addDocument(doc);
        indexWriter.close();
   }

     public void addDocument(Order order) throws IOException{
         IndexWriterConfig config = new IndexWriterConfig(analyzer);
         IndexWriter indexWriter = new IndexWriter(directory, config);
         System.out.println("stored: " + directory);
         Document doc = new Document();
         doc.add(new StringField("id", order.getId(), Field.Store.YES));
         doc.add(new TextField("content", order.getProductName(), Field.Store.YES));
         doc.add(new StoredField("status", order.getOrderRequestStatus()));
         indexWriter.addDocument(doc);
         indexWriter.close();
     }
}
