package com.pm.controller;


import com.pm.exception.NotFoundPostException;
import com.pm.model.PostModel;
import com.pm.model.ResponseMessage;
import com.pm.service.FileService;
import com.pm.service.PostRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class PostRestController {
    @Autowired
    private PostRestService restService;
    @Autowired
    private FileService fileService;

    @PostMapping("/posts")
    public ResponseEntity createPost(@RequestBody PostModel postModel) {
        ResponseMessage message = restService.createPost(postModel);
        return ResponseEntity.ok().body(message);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity deletePost(@PathVariable(name = "id") Integer id) {
        ResponseMessage message = restService.deletePostById(id);
        return ResponseEntity.ok().body(message);
    }

    @PutMapping("/posts")
    public ResponseEntity updatePost(@RequestBody PostModel postModel) throws NotFoundPostException {
        ResponseMessage message = restService.updatePostById(postModel);
        return ResponseEntity.ok().body(message);
    }

    @PutMapping("/posts/{id}/files")
    public ResponseEntity updatePostFile(@RequestParam MultipartFile[] files, @PathVariable Integer id) {
        ResponseMessage msg = fileService.saveFiles(files, id);
        return ResponseEntity.ok().body(msg);
    }

    @DeleteMapping("/posts/{id}/files")
    public ResponseEntity deletePostFile(@PathVariable Integer id, @RequestBody List<Integer> fileIds)
            throws IOException {
        ResponseMessage msg = fileService.deleteFile(id, fileIds);
        return ResponseEntity.ok().body(msg);
    }

    @GetMapping("/posts/{postId}/files/{fileId}")
    public ResponseEntity getPostFile(@PathVariable Integer postId,
                                      @PathVariable Integer fileId)
            throws NotFoundPostException, MalformedURLException {
        Map<String, Object> map = fileService.loadFile(postId, fileId);
        String name = (String) map.get("name");
        Resource file = (Resource) map.get("file");
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", name)).body(file);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity getPost(@PathVariable(name = "id") String id) {
        Pattern pattern = Pattern.compile("\\d+");
        if (!pattern.matcher(id).matches()) {
            return ResponseEntity.ok().body(new ResponseMessage("404", "Post is not found"));
        }
        int postId = Integer.parseInt(id);
        return ResponseEntity.ok().body(restService.getPostById(postId));
    }

    @GetMapping("/search")
    public ResponseEntity searchPost(@RequestParam(name = "q") String query) {

        return ResponseEntity.ok()
                .header("Cache-Control", "max-age=120")
                .body(restService.searchPost(query));
    }

}
