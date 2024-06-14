package com.tvelp.lucene.service;

import com.tvelp.lucene.modal.domain.Order;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
