package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.NewsPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.NewsDTO;
import com.alkemy.ong.dtos.responses.PageDTO;
import com.alkemy.ong.entities.News;
import com.alkemy.ong.exceptions.InvalidParameterException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.services.INewsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private INewsService newsService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDTO create(@Valid @RequestBody NewsPostPutRequestDTO newsPostRequestDTO) throws NotFoundException {
        return newsService.saveNews(newsPostRequestDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody NewsPostPutRequestDTO newsPostPutRequestDTO) throws NotFoundException {
        newsService.updateNews(id, newsPostPutRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) throws NotFoundException {
        newsService.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDTO getById(@PathVariable Long id) throws NotFoundException {
        return newsService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<NewsDTO> getPage(@RequestParam("page") Integer page, @RequestParam(value = "size",required = false, defaultValue = "10") Integer size){
        if(page < 0) throw new InvalidParameterException("Page number must be equal or higher than 0");
        if(size <= 0) throw new InvalidParameterException("Size number must be higher than 0");

        Pageable pageRequest = PageRequest.of(page,size);
        Page<News> news = newsService.getAll(pageRequest);
        return preparePageResponse(news);
    }

    public PageDTO<NewsDTO> preparePageResponse(Page<News> page){
        String url = "localhost:9800/news?page=";

        Page<NewsDTO> outputPage = new PageImpl(convertToDto(page.getContent()), PageRequest.of(page.getNumber(), page.getSize()), page.getTotalElements());
        return new PageDTO<>(outputPage, url);
    }

    private List<NewsDTO> convertToDto(List<News> newsList){
        return newsList.stream()
                .map(news -> modelMapper.map(news,NewsDTO.class))
                .collect(Collectors.toList());
    }
}
