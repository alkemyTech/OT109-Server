package com.alkemy.ong.services;

import com.alkemy.ong.dtos.requests.NewPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.NewDTO;
import com.alkemy.ong.entities.Category;
import com.alkemy.ong.exceptions.BadRequestException;
import com.alkemy.ong.exceptions.CategoryServiceException;
import com.alkemy.ong.exceptions.NewsNotFoundException;
import com.alkemy.ong.entities.News;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.repositories.CategoryRepository;
import com.alkemy.ong.repositories.NewsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService implements INewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public News save(String name, String content, String image, Category categoryId){
        News news = new News();

        news.setName(name);
        news.setContent(content);
        news.setImage(image);
        news.setCategory(categoryId);
        news.setCreatedAt(new Date());

        newsRepository.save(news);

        return news;
    }

    public News save(String name, String content, String image, String categoryName){
        News news = new News();
        Category category = null;

        try{
            category = categoryService.findByName(categoryName);
        }
        catch (CategoryServiceException ex){
            throw new CategoryServiceException(String.format("Category %s not found",categoryName));
        }
        category = keepId(category);

        news.setName(name);
        news.setContent(content);
        news.setImage(image);
        news.setCategory(category);
        news.setCreatedAt(new Date());

        newsRepository.save(news);

        return news;
    }

    public News save(News news){

        news.setCreatedAt(new Date());
        news = newsRepository.save(news);

        return news;
    }

    public Page<News> getAll(Pageable page){
        return newsRepository.findAll(page);
    }
    @Override
    public NewDTO getById(Long id) throws NotFoundException {
        News news = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("News Not found."));
        NewDTO newsDTO=modelMapper.map(news,NewDTO.class);
        return newsDTO;
    }

    @Override
    public NewDTO saveNews(NewPostPutRequestDTO newPostRequestDTO) throws NotFoundException {

        Category category =categoryRepository.findById(newPostRequestDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("News category not found."));
        if(newPostRequestDTO.getName().isBlank()){
            throw new BadRequestException("Name may not be empty");
        }
        if(newPostRequestDTO.getImage().isBlank()){
            throw new BadRequestException("Image may not be empty");
        }
        if(newPostRequestDTO.getContent().isBlank()){
            throw new BadRequestException("Content may not be empty");
        }
        News news = new News();
        news.setName(newPostRequestDTO.getName());
        news.setContent(newPostRequestDTO.getContent());
        news.setImage(newPostRequestDTO.getImage());
        news.setCategory(category);
        news.setCreatedAt(new Date());
        News newNews =  newsRepository.save(news);
        NewDTO newDTO = modelMapper.map(newNews, NewDTO.class );
        return newDTO;
    }

    @Override
    public NewDTO updateNews(Long id, NewPostPutRequestDTO newPutRequestDTO) throws NotFoundException {
        if(newPutRequestDTO.getName().isBlank()){
            throw new BadRequestException("Name may not be empty");
        }
        if(newPutRequestDTO.getImage().isBlank()){
            throw new BadRequestException("Image may not be empty");
        }
        if(newPutRequestDTO.getContent().isBlank()){
            throw new BadRequestException("Content may not be empty");
        }
        News news = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("News Not Found"));

        Category category =categoryRepository.findById(newPutRequestDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("News category Not Found"));

        news.setName(newPutRequestDTO.getName());
        news.setContent(newPutRequestDTO.getContent());
        news.setImage(newPutRequestDTO.getImage());
        news.setCategory(category);
        news.setUpdatedAt(new Date());
        News UptNews =  newsRepository.save(news);
        NewDTO UptDTO = modelMapper.map(UptNews, NewDTO.class );
        return UptDTO;

    }

    public void delete(Long id) throws NotFoundException {
        newsRepository.findById(id).orElseThrow(() -> new NotFoundException("News Not Found."));;
        newsRepository.deleteById(id);
    }

    public void update(Long id, News updated){
        Optional<News> news = newsRepository.findById(id);

        if(!news.isPresent()) throw new NewsNotFoundException("News with id " + id + " not found");

        news.get().setId(id);
        news.get().setName(updated.getName());
        news.get().setImage(updated.getImage());
        news.get().setContent(updated.getContent());
        news.get().setUpdatedAt(new Date());

    }

    private boolean categoryNameIsNullOrEmpty(String catName){
        return catName == null || catName.equals("");
    }

    private Category keepId(Category c){
        if(!categoryNameIsNullOrEmpty(c.getName())){
            c.setName(null);
            c.setImage(null);
            c.setCreatedAt(null);
            c.setDeletedAt(null);
            c.setUpdatedAt(null);
            c.setDescription(null);
        }
        return c;
    }
}
