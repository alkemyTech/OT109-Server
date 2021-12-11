package com.alkemy.ong.services;

import com.alkemy.ong.entities.Category;
import com.alkemy.ong.exceptions.CategoryServiceException;
import com.alkemy.ong.exceptions.NewsNotFoundException;
import com.alkemy.ong.entities.News;
import com.alkemy.ong.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CategoryService categoryService;

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

    public List<News> getAll(){
        return newsRepository.findAll();
    }

    public Optional<News> getById(Long id){
        Optional<News> news = newsRepository.findById(id);

        if(!news.isPresent()){
            throw new NewsNotFoundException("News with id " + id + " not found");
        }
        return news;
    }

    public void delete(Long id){
        Optional<News> news = newsRepository.findById(id);

        if(!news.isPresent()){
            throw new NewsNotFoundException("News with id " + id + " not found");
        }
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
