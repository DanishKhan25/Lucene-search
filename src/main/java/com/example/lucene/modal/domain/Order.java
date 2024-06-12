package com.example.lucene.modal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private String id;
    private String productName;
    private String orderRequestStatus;
    private String source;
    private String destination;
}