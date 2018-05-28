package com.cuit.boke.api.files.service;

import com.cuit.boke.config.FileConfig;
import com.yinjk.web.core.exception.BizException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private FileConfig fileConfig;

    public String uploadPic(HttpServletRequest request) throws BizException {
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multiRequest.getFile("editormd-image-file");
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        LocalDateTime nowTime = LocalDateTime.now();
        String dayPath = nowTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String relativePath = dayPath + "/" + nowTime.toEpochSecond(ZoneOffset.UTC) + suffix;
        String filePath = fileConfig.getRootPath() + relativePath;
        try (InputStream inputStream = file.getInputStream()) {
            File dir = new File(new File(filePath).getParentFile().getAbsolutePath());
            if (!(dir.exists() && dir.isDirectory())) {
                boolean mkdirs = dir.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
            IOUtils.copy(inputStream, fileOutputStream);
            return fileConfig.getDownload() + relativePath;
        } catch (IOException e) {
            throw new BizException(e.getMessage());
        }
    }

    public static void main(String[] args){
        LocalDateTime nowTime = LocalDateTime.now();
        String dayPath = nowTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        long l = nowTime.toEpochSecond(ZoneOffset.UTC);
        String fileName = "zhonggho.jpg";
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(fileType);
        System.out.println(dayPath);
    }
}
