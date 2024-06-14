package com.tvelp.lucene.modal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trip {
    private String id;
    private String source;
    private String destination;
}