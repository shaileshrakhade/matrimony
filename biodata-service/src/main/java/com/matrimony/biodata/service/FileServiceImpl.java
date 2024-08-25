package com.matrimony.biodata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private AsyncService asyncServices;

    @Override
    public String uploadFile(String basePath, String fileName, MultipartFile multipartFile) throws IOException {
        asyncServices.uploadFile(basePath, fileName, multipartFile);
        return "file uploaded";
    }

    @Override
    public InputStream getResource(String basePath, String fileName) throws FileNotFoundException {
        File dir = new File(basePath + fileName);
        if (!dir.exists())
            throw new FileNotFoundException(fileName + " Not Exist");

        return new FileInputStream(dir);
    }
}
