package com.example.lucene.service.faq;

import com.example.lucene.modal.domain.FAQ;
import com.example.lucene.modal.domain.Trip;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
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
public class FAQService {
    @Autowired
    private final Analyzer analyzer;
    @Autowired
    private final Directory directory;

    public FAQService(Analyzer analyzer, Directory directory) {
        this.analyzer = analyzer;
        this.directory = directory;
    }

    public void addDocument(FAQ faq) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        Document doc = new Document();
        doc.add(new StringField("id", faq.getId(), Field.Store.YES));
        doc.add(new TextField("question", faq.getQuestion(), Field.Store.YES));
        doc.add(new StoredField("answer", faq.getAnswer()));
        indexWriter.addDocument(doc);
        indexWriter.close();
    }

    public List<FAQ> search(String queryString) throws IOException, ParseException {
        List<FAQ> result = new ArrayList<>();

        try (IndexReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser("question", analyzer);
            Query query = parser.parse(queryString);
            TopDocs topDocs = searcher.search(query, 10);

            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                FAQ faq =FAQ.builder().
                        id(doc.get("id")).
                        question(doc.get("question")).
                        answer(doc.get("answer")).
                        build();
                result.add(faq);
            }
        }
        return result;
    }
}
