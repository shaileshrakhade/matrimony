package com.matrimony.biodata.service;

import com.matrimony.biodata.model.BioData;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public interface AsyncService {
    void uploadFile(String path, String name, MultipartFile multipartFile) throws IOException;

    void save(BioData bioData);

    void deleteByRegisterAtBefore(Date date);
}
