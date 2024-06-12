package com.example.lucene.configration;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Paths;

@Configuration
public class LuceneConfig {

    @Bean
    public Directory luceneDirectory() throws IOException {
//        return new ByteBuffersDirectory(); this will not store index in memory
        return FSDirectory.open(Paths.get("C:\\Users\\Danish\\Downloads\\lucene\\lucene\\src\\main\\resources\\index"));
    }

    @Bean
    public Analyzer luceneAnalyzer() {
        return new StandardAnalyzer();
    }
}
