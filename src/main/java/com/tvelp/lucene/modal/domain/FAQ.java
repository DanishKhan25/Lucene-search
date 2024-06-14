package com.tvelp.lucene.modal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FAQ {
    private String id;
    private String question;
    private String answer;
}
