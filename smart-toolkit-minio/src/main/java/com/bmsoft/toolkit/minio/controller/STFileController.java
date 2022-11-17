package com.bmsoft.toolkit.minio.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.bmsoft.toolkit.core.Result;
import com.bmsoft.toolkit.core.exception.BusinessException;
import com.bmsoft.toolkit.minio.DownloadResponse;
import com.bmsoft.toolkit.minio.UploadResponse;
import com.bmsoft.toolkit.minio.config.MinioProperties;
import com.bmsoft.toolkit.minio.util.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author llk
 * @date 2022-10-19 16:19
 */
@Slf4j
@Api(tags = "文件服务接口-st")
@RestController
@RequestMapping("/st/file")
public class STFileController {

    @Autowired
    MinioUtil minioUtil;

    @Autowired
    MinioProperties minioProperties;

    // TODO: 11/1/22 llk 限制文件上传类型
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result<UploadResponse> upload(@RequestPart("file") MultipartFile file) throws IOException {
        UploadResponse upload = minioUtil.upload(file.getInputStream(), file.getOriginalFilename());
        return Result.success(upload);
    }

    @ApiOperation("多文件上传")
    @PostMapping("/multi-upload")
    public Result<List<UploadResponse>> multiUpload(@RequestPart("files") MultipartFile[] files) throws IOException {
        List<UploadResponse> list = new ArrayList<>();
        for (MultipartFile file : files) {
            UploadResponse upload = minioUtil.upload(file.getInputStream(), file.getOriginalFilename());
            list.add(upload);
        }
        return Result.success(list);
    }

    @ApiOperation("文件预览, 可根据fullName或url预览(仅支持图片,txt,pdf)")
    @GetMapping("/preview")
    public ResponseEntity<byte[]> preview(@RequestParam(value = "fullName", required = false) String fullName,
                                          @RequestParam(value = "url", required = false) String url) throws IOException {
        fullName = getFullName(fullName, url);
        DownloadResponse downloadResponse = minioUtil.download(fullName);
        try (InputStream inputStream = downloadResponse.getInputStream()) {
            byte[] bytes = IoUtil.readBytes(inputStream);
            HttpHeaders headers = buildHttpHeaders(downloadResponse, "inline");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType(downloadResponse.getContentType()))
                    .body(bytes);
        }
    }



    @ApiOperation("文件下载, 可根据fullName或url下载")
    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam(value = "fullName", required = false) String fullName,
                                           @RequestParam(value = "url", required = false) String url) throws IOException {

        fullName = getFullName(fullName, url);
        DownloadResponse downloadResponse = minioUtil.download(fullName);
        try (InputStream inputStream = downloadResponse.getInputStream()) {
            byte[] bytes = IoUtil.readBytes(inputStream);
            HttpHeaders headers = buildHttpHeaders(downloadResponse, "attachment");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(bytes);
        }
    }

    private HttpHeaders buildHttpHeaders(DownloadResponse downloadResponse, String dispositionType) throws UnsupportedEncodingException {
        String filename = URLEncoder.encode(downloadResponse.getOriginalFilename(), "UTF-8");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Ranges", "bytes");
        headers.add("Content-Length", downloadResponse.getContentLength() + "");
        headers.add("Content-Disposition", String.format("%s; filename=%s", dispositionType, filename));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }

    private String getFullName(String fullName, String url) {
        log.info("fullName: {}, url: {}", fullName, url);
        if (StrUtil.isBlank(fullName)) {
            if (StrUtil.isNotBlank(url)) {
                fullName = minioProperties.wipeUrl(url);
            } else {
                throw new BusinessException("fullName, url 至少传一个");
            }
        }
        return fullName;
    }

}
