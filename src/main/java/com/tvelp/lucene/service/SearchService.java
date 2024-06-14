package com.tvelp.lucene.service;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.springframework.stereotype.Service;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class SearchService {

    @Autowired
    private Analyzer analyzer;
    @Autowired
    private Directory directory;
    public List<Document> search(String queryString) throws IOException, ParseException {
        List<Document> result = new ArrayList<>();
        Analyzer analyzer = new StandardAnalyzer();
        try (IndexReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser("content", analyzer);
            Query query = parser.parse(queryString);
            TopDocs topDocs = searcher.search(query, 10);

            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                System.out.println("Found: " + doc.get("content"));
                //can we get data back in Order class fromat here

                result.add(doc);
            }
        }

        return result;
    }
}
