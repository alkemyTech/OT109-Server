package com.alkemy.ong.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {
    private Page<T> page;
    private String prevPage;
    private String nextPage;
    
    public PageDTO(Page page, String url){
        this.page = page;
        prevPage = page.hasPrevious() ? url + page.previousOrFirstPageable().getPageNumber() : null;
        nextPage = page.hasNext() ? url + page.nextOrLastPageable().getPageNumber() : null;
    }
}
