package com.alkemy.ong.controllers;

import com.alkemy.ong.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/** Amazon Web Services S3 Bucket integration for uploading and downloading images.*/
@RestController
@RequestMapping("/s3/images")
@CrossOrigin(origins = "*", maxAge = 3600)
public class S3Controller {

    private static final String FILE_NAME = "fileName";

    @Autowired
    S3Service s3Service;

    @GetMapping(params = "fileName")
    public ResponseEntity<Object> findByName2(@RequestParam("fileName") String fileName) {
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.noCache())
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(new InputStreamResource(s3Service.findByName(fileName)));
    }

    @RequestMapping(method = RequestMethod.POST,
                    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> save(@RequestPart(value = "file") MultipartFile multipartFile) {
        String filePath = s3Service.save(multipartFile);
        return new ResponseEntity<>(filePath,HttpStatus.OK);
    }

}
