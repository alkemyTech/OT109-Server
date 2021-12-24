package com.alkemy.ong.services;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.web.multipart.MultipartFile;


public interface S3Service {
    
    S3ObjectInputStream findByName(String fileName);
    
    String save(MultipartFile multipartFile);
    
}
