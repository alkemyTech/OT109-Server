package com.alkemy.ong.services;

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

    public void save(String name, String content, String image){
        News news = new News();

        news.setName(name);
        news.setContent(content);
        news.setImage(image);
        news.setCreatedAt(new Date());

        newsRepository.save(news);
    }

    public List<News> getAll(){
        return newsRepository.findAll();
    }

    public Optional<News> getById(Long id){
        Optional<News> news = newsRepository.findById(id);

        if(news.isEmpty()){
            throw new NewsNotFoundException("News with id " + id + " not found");
        }
        return news;
    }

    public void delete(Long id){
        Optional<News> news = newsRepository.findById(id);

        if(news.isEmpty()){
            throw new NewsNotFoundException("News with id " + id + " not found");
        }
        newsRepository.deleteById(id);
    }

    public void update(Long id, News updated){
        Optional<News> news = newsRepository.findById(id);

        if(news.isEmpty()) throw new NewsNotFoundException("News with id " + id + " not found");

        news.get().setId(id);
        news.get().setName(updated.getName());
        news.get().setImage(updated.getImage());
        news.get().setContent(updated.getContent());
        news.get().setUpdatedAt(new Date());

    }

}
