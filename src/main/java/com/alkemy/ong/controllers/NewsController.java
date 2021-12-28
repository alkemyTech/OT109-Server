package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.createAndUpdate.NewPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.NewsDTO;
import com.alkemy.ong.dtos.responses.NewsPageResponseDTO;
import com.alkemy.ong.entities.News;
import com.alkemy.ong.exceptions.InvalidParameterException;
import com.alkemy.ong.exceptions.NotFoundException;
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

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private INewsService newsService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDTO create(@Valid @RequestBody NewPostPutRequestDTO newPostRequestDTO) throws NotFoundException {
       // try{
        return newsService.saveNews(newPostRequestDTO);
        //return ResponseEntity.status(HttpStatus.CREATED).body(newsDTO);
            /*
        }catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (BadRequestException exception){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }*/
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody NewPostPutRequestDTO newPostRequestDTO) throws NotFoundException {
       //try{
        newsService.updateNews(id,newPostRequestDTO);
        //return ResponseEntity.status(HttpStatus.OK).body(newsDTO);
           /*
        }catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (BadRequestException exception){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }*/
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) throws NotFoundException {
        //try{
        newsService.delete(id);
//        return ResponseEntity.status(HttpStatus.OK).build();
            /*
        }catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        */
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDTO getById(@PathVariable Long id) throws NotFoundException {
        //try{
            return newsService.getById(id);
            //return ResponseEntity.status(HttpStatus.OK).body(newsDTO);
            /*
        }catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
*/
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public NewsPageResponseDTO getPage(@RequestParam("page") Integer page, @RequestParam(value = "size",required = false, defaultValue = "10") Integer size){
        if(page < 0) throw new InvalidParameterException("Page number must be equal or higher than 0");
        if(size <= 0) throw new InvalidParameterException("Size number must be higher than 0");

        Pageable pageRequest = PageRequest.of(page,size);
        Page<News> news = newsService.getAll(pageRequest);
        return preparePageResponse(news);

//        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public NewsPageResponseDTO preparePageResponse(Page<News> page){
        String url = "localhost:9800/news?page=";

        String previousOrFirstPage = Integer.toString(page.previousOrFirstPageable().getPageNumber());
        String nextOrLastPage = Integer.toString(page.nextOrLastPageable().getPageNumber());

        NewsPageResponseDTO response = new NewsPageResponseDTO();
        response.setNewsDTOS(convertToDto(page.getContent()));
        response.setPreviousPage(url + previousOrFirstPage);
        response.setNextPage(url + nextOrLastPage);

        return response;
    }

    private List<NewsDTO> convertToDto(List<News> newsList){
        return newsList.stream()
                .map(news -> modelMapper.map(news, NewsDTO.class))
                .collect(Collectors.toList());
    }
}
