package com.pm.service;

import com.pm.config.Constants;
import com.pm.entity.FileEntity;
import com.pm.entity.PostEntity;
import com.pm.exception.NotFoundPostException;
import com.pm.model.ResponseMessage;
import com.pm.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class FileService {
    private final Path root = Paths.get(Constants.FOLDER_UPLOAD);
    @Autowired
    private FileRepository fileRepository;

    public void initFolder() {
        try {
            if (Files.exists(root)) return;
            Files.createDirectory(root);
        } catch (IOException ex) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Transactional
    public ResponseMessage saveFiles(MultipartFile[] files, Integer idPost) {
        PostEntity postEntity = new PostEntity();
        postEntity.setId(idPost);
        Arrays.stream(files).forEach(file -> {
            try {
                FileEntity fileEntity = new FileEntity();
                fileEntity.setPost(postEntity);
                fileEntity.setName(file.getOriginalFilename());
                fileEntity.setPath(root.toString());
                fileRepository.save(fileEntity);
                Files.copy(file.getInputStream(), this.root.resolve(String.valueOf(fileEntity.getId())));
            } catch (IOException ioException) {
                throw new RuntimeException("Could not store file " + ioException.getMessage());
            }
        });
        return new ResponseMessage("200", "Upload File successfully");
    }

    public Map loadFile(Integer postId, Integer fileId) throws NotFoundPostException, MalformedURLException {
        Optional<FileEntity> opt = fileRepository.findByIdAndPost_Id(fileId, postId);
        if (!opt.isPresent()) throw new NotFoundPostException();
        FileEntity file = opt.get();
        Path path = root.resolve(String.valueOf(file.getId()));
        if (!Files.exists(path) || !Files.isReadable(path)) throw new NotFoundPostException();
        Resource resource = new UrlResource(path.toUri());
        Map<String, Object> data = new HashMap<>();
        data.put("name", file.getName());
        data.put("file", resource);
        return data;
    }

    @Transactional
    public ResponseMessage deleteFile(Integer postId, List<Integer> fileIds) throws IOException {
        List<FileEntity> files = fileRepository.getAllByIds(fileIds,postId);
        for (FileEntity file : files) {
            fileRepository.delete(file);
            Path path = root.resolve(String.valueOf(file.getId()));
            Files.deleteIfExists(path);
        }
        return new ResponseMessage("200","Delete "+files.size()+" Files successfully");
    }

}
