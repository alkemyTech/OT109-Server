package com.alkemy.ong.dtos.responses;

import com.alkemy.ong.entities.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewsPageResponseDTO {
    private List<NewDTO> newDTOS;
    private String previousPage;
    private String nextPage;
}
