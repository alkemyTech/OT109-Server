package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.NewPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.NewDTO;
import com.alkemy.ong.entities.News;
import com.alkemy.ong.exceptions.BadRequestException;
import com.alkemy.ong.exceptions.InvalidParameterException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.pojos.output.PageDTO;
import com.alkemy.ong.services.INewsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageImpl;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private INewsService newService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody NewPostPutRequestDTO newPostRequestDTO) throws NotFoundException {
        try{
            NewDTO newDTO = newService.saveNews(newPostRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newDTO);
        }catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (BadRequestException exception){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody NewPostPutRequestDTO newPostRequestDTO) throws NotFoundException {

        try{
            NewDTO newDTO = newService.updateNews(id,newPostRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(newDTO);
        }catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (BadRequestException exception){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws NotFoundException {
        try{
            newService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }


    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws NotFoundException {
        try{
            NewDTO newDTO=newService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(newDTO);
        }catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<NewDTO> getPage(@RequestParam("page") Integer page, @RequestParam(value = "size",required = false, defaultValue = "10") Integer size){
        if(page < 0) throw new InvalidParameterException("Page number must be equal or higher than 0");
        if(size <= 0) throw new InvalidParameterException("Size number must be higher than 0");

        Pageable pageRequest = PageRequest.of(page,size);
        Page<News> news = newService.getAll(pageRequest);

        return preparePageResponse(news);
    }

    public PageDTO<NewDTO> preparePageResponse(Page<News> page){
        String url = "localhost:9800/news?page=";

        Page<NewDTO> outputPage = new PageImpl(convertToDto(page.getContent()), PageRequest.of(page.getNumber(), page.getSize()), page.getTotalElements());
        return new PageDTO<>(outputPage, url);
    }

    private List<NewDTO> convertToDto(List<News> newsList){
        return newsList.stream()
                .map(news -> modelMapper.map(news,NewDTO.class))
                .collect(Collectors.toList());
    }
}
