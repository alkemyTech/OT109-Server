package com.alkemy.ong.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SlideCompact {
    private String imageUrl;
    private int orderNum;
}
