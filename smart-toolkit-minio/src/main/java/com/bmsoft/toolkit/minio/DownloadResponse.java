package com.bmsoft.toolkit.minio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * @author llk
 * @date 2022-10-19 21:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadResponse {

    private String filename;

    private String originalFilename;

    private String fullName;

    private String bucketName;

    private String contentType;

    private Integer contentLength;

    private InputStream inputStream;

}
