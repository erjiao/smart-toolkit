package com.bmsoft.toolkit.excel.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.bmsoft.toolkit.core.exception.BusinessException;
import com.bmsoft.toolkit.excel.ExcelDataRef;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author liaozili
 * @date 2022-06-10 15:00
 */
@Slf4j
public class ExcelUtil {

    public static final String XLS_SUFFIX = "xls";

    public static final String XLS_CONTENT_TYPE = "application/vnd.ms-excel";

    public static final String XLSX_SUFFIX = "xlsx";

    public static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


    public static final Map<String, String> CONTENT_TYPE_MAP = new HashMap<String, String>() {{
        put(XLS_SUFFIX, XLS_CONTENT_TYPE);
        put(XLSX_SUFFIX, XLSX_CONTENT_TYPE);
    }};

    /**
     * excel 导出输出流定义
     *
     * @param fileName 输出文件名
     * @param response 构建响应体
     * @return 输出流
     * @throws Exception 编码异常
     */
    public static OutputStream getRespOutputStream(HttpServletResponse response, String fileName) {
        Objects.requireNonNull(fileName);
        try {
            String extName = FileUtil.extName(fileName);
            String encodeFileName = URLEncoder.encode(fileName, "UTF-8");
            // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8
            response.setContentType(CONTENT_TYPE_MAP.get(extName));
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", String.format("attachment;filename=%s", encodeFileName));
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Content-Transfer-Encoding", "binary");
            return response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            throw new BusinessException("导出文件失败");
        }
    }

    /**
     * 导出excel
     *
     * @param response      请求响应体
     * @param list          需导出的数据
     * @param fileName      导出文件名
     * @param sheetName     导出sheet名
     * @param clazz         导出数据对应java实体的class文件
     * @param <T>           导出实体
     * @param writeHandlers 写处理器
     */
    public static <T> void writeExcel(List<T> list,
                                      Class<T> clazz,
                                      String fileName,
                                      String sheetName,
                                      List<WriteHandler> writeHandlers,
                                      HttpServletResponse response) {
        fileName = getFileName(fileName);

        ExcelWriterBuilder builder = EasyExcel.write(getRespOutputStream(response, fileName), clazz);
        writeHandlers.forEach(builder::registerWriteHandler);
        builder.sheet(sheetName).doWrite(list);
    }

    private static String getFileName(String fileName) {
        if (StrUtil.isBlank(fileName)) {
            fileName = IdUtil.fastSimpleUUID() + StrUtil.DOT + XLSX_SUFFIX;
            log.info("auto generate filename [{}]", fileName);
        } else {
            String extName = FileUtil.extName(fileName);
            if (StrUtil.isBlank(extName)) {
                fileName = fileName + StrUtil.DOT + XLSX_SUFFIX;
            }
        }
        return fileName;
    }


    /**
     * 导出excel
     *
     * @param response  请求响应体
     * @param list      需导出的数据
     * @param fileName  导出文件名
     * @param sheetName 导出sheet名
     * @param clazz     导出数据对应java实体的class文件
     * @param <T>       导出实体
     */
    public static <T> void writeExcel(List<T> list,
                                      Class<T> clazz,
                                      String fileName,
                                      String sheetName,
                                      HttpServletResponse response) {
        writeExcel(list, clazz, fileName, sheetName, Collections.singletonList(new LongestMatchColumnWidthStyleStrategy()), response);
    }

    public static <T> void writeExcel(List<T> list,
                                      Class<T> clazz,
                                      String fileName,
                                      HttpServletResponse response) {
        writeExcel(list, clazz, fileName, null, Collections.singletonList(new LongestMatchColumnWidthStyleStrategy()), response);
    }

    public static <T> void writeExcel(List<T> list,
                                      Class<T> clazz,
                                      HttpServletResponse response) {
        writeExcel(list, clazz, null, null, Collections.singletonList(new LongestMatchColumnWidthStyleStrategy()), response);
    }


    public static <T> void writeExcel(ExcelDataRef<T> dataRef, String fileName, String sheetName, HttpServletResponse response) {
        List<T> list = dataRef.getList();
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) dataRef.getType();
        writeExcel(list, clazz, fileName, sheetName, response);
    }

    public static <T> void writeExcel(ExcelDataRef<T> dataRef, String fileName, HttpServletResponse response) {
        writeExcel(dataRef, fileName, null, response);
    }

    public static <T> void writeExcel(ExcelDataRef<T> dataRef, HttpServletResponse response) {
        writeExcel(dataRef, null, null, response);
    }

}
