package com.alkemy.ong.controllers;

import com.alkemy.ong.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@CrossOrigin(origins = "*", maxAge = 3600)
public class S3Controller {

    private static final String MESSAGE_1 = "Uploaded the file successfully";
    private static final String FILE_NAME = "fileName";


    @Autowired
    S3Service s3Service;

    @GetMapping
    public ResponseEntity<Object> findByName(@RequestBody(required = false) Map<String, String> params) {
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.noCache())
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + params.get(FILE_NAME) + "\"")
                .body(new InputStreamResource(s3Service.findByName(params.get(FILE_NAME))));

    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestParam("file") MultipartFile multipartFile) {
        s3Service.save(multipartFile);
        return new ResponseEntity<>(MESSAGE_1,HttpStatus.OK);
    }
}
