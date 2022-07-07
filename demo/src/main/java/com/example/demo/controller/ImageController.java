package com.example.demo.controller;
import com.example.demo.application.imageApplication.CreateOrUpdateImageDTO;
import com.example.demo.application.imageApplication.ImageApplication;
import com.example.demo.application.imageApplication.ImageDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageController {
    public final ImageApplication imageApplication;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ImageDTO> upload(@RequestParam("image") MultipartFile file) throws IOException {      
        return this.imageApplication.add(new CreateOrUpdateImageDTO(file.getBytes()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
    public Mono<ResponseEntity<ImageDTO>> getImage(@PathVariable String id) {
        return this.imageApplication.getImageRedis(id).map(image -> ResponseEntity.ok(image)).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
