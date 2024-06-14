package com.tvelp.lucene.modal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chat {
    private String chatId;
    private Integer status;
    private String buyerId;
    private String travelerId;
    private String tripId;
    private Long expiryAt;
    private String orderId;
    private String productName;
    private UserSummaryDto user;
    private Long createdAt;
    private Address source;
    private Address destination;
    private  String receiverTripId;
    private  String receiverId;
    private ChatTypeEnum chatType;
}
