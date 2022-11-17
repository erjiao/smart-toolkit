package com.bmsoft.toolkit.minio;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author llk
 * @date 2022-10-19 10:41
 */
@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponse {

    @ApiModelProperty("文件名")
    private String filename;

    @ApiModelProperty("原文件名")
    private String originalFilename;

    @ApiModelProperty("文件全名(可在下载时使用)")
    private String fullName;

    @ApiModelProperty("文件大小")
    private Integer size;

    @ApiModelProperty("文件类型")
    private String contentType;

    @ApiModelProperty("文件访问地址(可在下载时使用)")
    private String url;

}
