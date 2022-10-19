package com.bmsoft.toolkit.minio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author llk
 * @date 2022-10-19 10:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponse {

    private String filename;

    private String originalFilename;

    private String fullName;

    private String contentType;

    private String url;

}
