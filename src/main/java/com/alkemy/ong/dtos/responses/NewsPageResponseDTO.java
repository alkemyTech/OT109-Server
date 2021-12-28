package com.alkemy.ong.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewsPageResponseDTO {
    private List<NewsDTO> newsDTOS;
    private String previousPage;
    private String nextPage;
}
