package com.bmsoft.toolkit.minio.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import com.bmsoft.toolkit.core.utils.IdUtils;
import com.bmsoft.toolkit.minio.DownloadResponse;
import com.bmsoft.toolkit.minio.UploadResponse;
import com.bmsoft.toolkit.minio.config.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * https://www.jianshu.com/p/c0bf5facde51
 *
 * @author llk
 * @date 2022-10-19 10:09
 */
@Slf4j
public class MinioUtil {

    public static final String ORIGINAL_FILENAME_HEADER = "x-amz-meta-original-filename";

    public static final String CONTENT_LENGTH_HEADER = "Content-Length";

    private MinioClient minioClient;

    private MinioProperties minioProperties;


    public MinioUtil(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    @SneakyThrows
    public UploadResponse upload(InputStream stream, String filename, String bucketName) {
        if (null == stream) {
            return null;
        }
        String directory = DateUtil.format(new Date(), "yyyyMM/dd");
        String newFilename = IdUtils.get32UUID() + filename.substring(filename.lastIndexOf("."));

        String objectName = directory + "/" + newFilename;
        String contentType = getMimeType(filename);

        // 保存原文件名
        HashMap<String, String> userMetadata =
                MapUtil.of(ORIGINAL_FILENAME_HEADER, filename);
        int size = stream.available();
        String url =
                this.putObject(bucketName, objectName, stream, size, contentType, userMetadata);
        log.info("the file [{}] contentType [{}] upload success, url is [{}]", filename, contentType, url);
        return new UploadResponse(newFilename, filename, objectName, size, contentType, url);
    }

    private String getMimeType(String filename) {
        String contentType = FileUtil.getMimeType(filename);

        // 默认文本
        if (null == contentType) {
            contentType = "text/plain";
        }
        return contentType;
    }

    /**
     * @param file
     * @param bucketName
     * @return
     */
    @SneakyThrows
    public UploadResponse upload(File file, String bucketName) {
        if (null == file || 0 == file.length()) {
            return null;
        }

        String filename = file.getName();
        BufferedInputStream stream = FileUtil.getInputStream(file);

        return this.upload(stream, filename, bucketName);
    }

    public UploadResponse upload(File file) {
        return this.upload(file, minioProperties.getBucketName());
    }

    public UploadResponse upload(InputStream stream, String filename) {
        return this.upload(stream, filename, minioProperties.getBucketName());
    }


        /**
         * 上传⽂件
         *
         * @param bucketName bucket名称
         * @param objectName ⽂件名称
         * @param stream     ⽂件流
         * @return 上传后的地址
         * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
         */
    @SneakyThrows
    public String putObject(String bucketName, String objectName, InputStream stream) {
        long size = stream.available();
        String contextType = FileUtil.getMimeType(objectName);
        return this.putObject(bucketName, objectName, stream, size, contextType);
    }


    /**
     * 上传⽂件
     *
     * @param bucketName  bucket名称
     * @param objectName  ⽂件名称
     * @param stream      ⽂件流
     * @param size        ⼤⼩
     * @param contextType 类型
     * @return 上传后的地址
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    @SneakyThrows
    public String putObject(String bucketName, String objectName, InputStream stream, long
            size, String contextType) {
        return putObject(bucketName, objectName, stream, size, contextType, new HashMap<>());
    }

    @SneakyThrows
    public String putObject(String bucketName, String objectName, InputStream stream, long
            size, String contextType, Map<String, String> userMetadata) {
        PutObjectArgs objectArgs = PutObjectArgs
                .builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(stream, size, -1)
                .contentType(contextType)
                .userMetadata(userMetadata)
                .build();
        minioClient.putObject(objectArgs);
        return minioProperties.getUrl(bucketName, objectName);
    }


    /**
     * 获取⽂件外链
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @param expires    过期时间
     * @return url
     */
    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        GetPresignedObjectUrlArgs objectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .method(Method.GET)
                .expiry(expires)
                .build();
        return minioClient.getPresignedObjectUrl(objectUrlArgs);
    }

    /**
     * 获取⽂件外链
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @return url
     */
    public String getObjectURL(String bucketName, String objectName) {
        return this.getObjectURL(bucketName, objectName, GetPresignedObjectUrlArgs.DEFAULT_EXPIRY_TIME);
    }

    /**
     * 获取⽂件
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @return ⼆进制流
     */
    @SneakyThrows
    public GetObjectResponse getObject(String bucketName, String objectName) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName).object(objectName).build();
        return minioClient.getObject(objectArgs);
    }

    /**
     *
     * @param bucketName
     * @param objectName
     * @return
     *
     *
     * name = Accept-Ranges, value = bytes
     * name = Content-Length, value = 3674098
     * name = Content-Security-Policy, value = block-all-mixed-content
     * name = Content-Type, value = image/jpeg
     * name = Date, value = Wed, 19 Oct 2022 14:48:34 GMT
     * name = ETag, value = "bb234731308058426e6565bb64343dc0"
     * name = Last-Modified, value = Wed, 19 Oct 2022 14:47:45 GMT
     * name = Server, value = MinIO
     * name = Strict-Transport-Security, value = max-age=31536000; includeSubDomains
     * name = Vary, value = Origin
     * name = x-amz-meta-original-filename, value = aaa.jpg
     * name = X-Amz-Request-Id, value = 171F7FCF21AC82DF
     * name = X-Content-Type-Options, value = nosniff
     * name = X-Xss-Protection, value = 1; mode=block
     *
     */
    public DownloadResponse download(String bucketName, String objectName) {
        GetObjectResponse clientObject = this.getObject(bucketName, objectName);
        Headers headers = clientObject.headers();
        String originalFilename = headers.get(ORIGINAL_FILENAME_HEADER);
        String contentLength = headers.get(CONTENT_LENGTH_HEADER);
        String filename = FileUtil.getName(objectName);
        String contentType = this.getMimeType(filename);
        return new DownloadResponse(filename, originalFilename, objectName, bucketName, contentType, Integer.valueOf(contentLength), clientObject);
    }

    public DownloadResponse download(String objectName) {
        return this.download(minioProperties.getBucketName(), objectName);
    }


    /**
     * 获取全部bucket
     *
     * @return
     */
    @SneakyThrows
    public List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public Optional<Bucket> getBucket(String bucketName) {
        return this.getAllBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void removeBucket(String bucketName) {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 判断bucket是否存在
     *
     * @param bucketName
     * @return boolean
     */
    @SneakyThrows
    public boolean existBucket(String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建bucket
     */
    @SneakyThrows
    public void createBucket(String bucketName) {
        if (!this.existBucket(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

}
