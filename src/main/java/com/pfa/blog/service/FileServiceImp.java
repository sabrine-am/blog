package com.pfa.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImp implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //fileName
        String name= file.getOriginalFilename();
        //random name generated file
        String randomId= UUID.randomUUID().toString();
        String fileName1= randomId.concat(name.substring(name.lastIndexOf(".")));
        //fullpath
        String filePath = path + File.separator + fileName1;
        //created folder if not created
        File f = new File(path);
        if(!f.exists())
        {
            f.mkdir();
        }
        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return name;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
       String fullPath = path+File.separator + fileName;
       InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
