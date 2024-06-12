package com.example.lucene.service.chat;

import com.example.lucene.modal.domain.Address;
import com.example.lucene.modal.domain.Chat;
import com.example.lucene.modal.domain.ChatTypeEnum;
import com.example.lucene.modal.domain.UserSummaryDto;
import com.example.lucene.utils.JsonUtil;
import org.apache.lucene.analysis.Analyzer;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class ChatService {


    private final Analyzer analyzer;

    private final Directory directory;

    public ChatService(Analyzer analyzer, Directory directory) {
        this.analyzer = analyzer;
        this.directory = directory;
    }


    public void addDocument(Chat chat) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        Document doc = new Document();
        String userJson = JsonUtil.toJson(chat.getUser());
        String sourceJson = JsonUtil.toJson(chat.getSource());
        String destinationJson = JsonUtil.toJson(chat.getDestination());

        String fulltextSearch = chat.getProductName() + " " +
                chat.getSource().getCity() + " " +
                chat.getSource().getState() + " " +
                chat.getSource().getCountry() + " " +
                chat.getSource().getAirportName() + " " +
                chat.getSource().getStateShort() + " " +
                chat.getDestination().getCity() + " " +
                chat.getDestination().getState() + " " +
                chat.getDestination().getCountry() + " " +
                chat.getDestination().getAirportName() + " " +
                chat.getDestination().getStateShort() + " " +
                chat.getUser().getDisplayName();
        doc.add(new StringField("id", chat.getChatId(), Field.Store.YES));
        doc.add(new StoredField("status", chat.getStatus()));
        doc.add(new StoredField("buyerId", chat.getBuyerId()));
        doc.add(new StoredField("travelerId", chat.getTravelerId()));
        doc.add(new StoredField("tripId", chat.getTripId()));
        doc.add(new StoredField("expiryAt", chat.getExpiryAt()));
        doc.add(new StoredField("orderId", chat.getOrderId()));
        doc.add(new StoredField("productName", chat.getProductName()));
        doc.add(new StoredField("user", userJson));
        doc.add(new StoredField("createdAt", chat.getCreatedAt()));
        doc.add(new StoredField("source", sourceJson));
        doc.add(new StoredField("destination", destinationJson));
        doc.add(new StoredField("receiverTripId", chat.getReceiverTripId()));
        doc.add(new StoredField("receiverId", chat.getReceiverId()));
        doc.add(new StoredField("chatType", chat.getChatType().toString()));
        doc.add(new TextField("content", fulltextSearch, Field.Store.NO));
        indexWriter.addDocument(doc);
        indexWriter.close();
    }

    public List<Chat> search(String queryString) throws IOException, ParseException {
        List<Chat> result = new ArrayList<>();
        try(IndexReader reader= DirectoryReader.open(directory)) {
            IndexSearcher searcher= new IndexSearcher(reader);
            QueryParser parser=new QueryParser("content", analyzer);
            Query query= parser.parse(queryString);
            TopDocs topDocs= searcher.search(query, 10);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc= searcher.doc(scoreDoc.doc);
                UserSummaryDto user = JsonUtil.fromJson(doc.get("user"), UserSummaryDto.class);
                Address source = JsonUtil.fromJson(doc.get("source"), Address.class);
                Address destination = JsonUtil.fromJson(doc.get("destination"), Address.class);
                Chat chat= Chat.builder()
                        .chatId(doc.get("id"))
                        .status(Integer.valueOf(doc.get("status")))
                        .buyerId(doc.get("buyerId"))
                        .travelerId(doc.get("travelerId"))
                        .tripId(doc.get("tripId"))
                        .expiryAt(Long.valueOf(doc.get("expiryAt")))
                        .orderId(doc.get("orderId"))
                        .productName(doc.get("productName"))
                        .user(user)
                        .createdAt(Long.valueOf(doc.get("createdAt")))
                        .source(source)
                        .destination(destination)
                        .receiverTripId(doc.get("receiverTripId"))
                        .receiverId(doc.get("receiverId"))
                        .chatType(ChatTypeEnum.valueOf(doc.get("chatType")))
                        .build();
                result.add(chat);
            }
        }
        return result;
    }
}
