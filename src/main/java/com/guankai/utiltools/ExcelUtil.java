package com.guankai.utiltools;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * Excel表格工具类
 * 表格解析、表格导出
 *
 * @author: guan.kai
 * @date: 2020/6/9 10:05
 **/
public class ExcelUtil {

    private ExcelUtil(){}

    private static final String MSG_CONTENT_NONE = "导出数据不能为空！";

    /** excel文件扩展名正则表达式 */
    private static final String EXCEL_REGEX_XLS = "^.+\\.(?i)(xls)$";
    private static final String EXCEL_REGEX_XLSX = "^.+\\.(?i)(xlsx)$";

    /**
     * 导出excel表格
     *
     * @param response 响应体
     * @param fileName 文件名
     * @param title 表格标题
     * @param headerArr 表头
     * @param contentArr 导出内容
     */
    public static void export(HttpServletResponse response, String fileName, String title, String[] headerArr, String[][] contentArr) throws IOException {
        fileName = fileName + ".xlsx";
        if (contentArr.length < 1){
            throw new NullPointerException(MSG_CONTENT_NONE);
        }

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();
        int rowSum = 0;

        // 生成标题
        if (StringUtils.isNotBlank(title)){
            XSSFFont font = getFont(wb, null, (short) 18, true, (short) 0);
            XSSFCellStyle callStyle = getCallStyle(wb, font, true);
            XSSFRow row = sheet.createRow(rowSum);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue(title);
            cell.setCellStyle(callStyle);
            //合并单元格
            CellRangeAddress cellRangeAddress = new CellRangeAddress(rowSum, rowSum, 0, contentArr.length-1);
            sheet.addMergedRegion(cellRangeAddress);
            RegionUtil.setBorderTop(BorderStyle.MEDIUM,cellRangeAddress,sheet);
            RegionUtil.setBorderBottom(BorderStyle.MEDIUM,cellRangeAddress,sheet);
            RegionUtil.setBorderLeft(BorderStyle.MEDIUM,cellRangeAddress,sheet);
            RegionUtil.setBorderRight(BorderStyle.MEDIUM,cellRangeAddress,sheet);
            rowSum++;
        }

        //生成表头
        if (headerArr.length > 0){
            XSSFFont font = getFont(wb, null, (short) 15, true, (short) 0);
            XSSFCellStyle callStyle = getCallStyle(wb, font, true);
            XSSFRow row = sheet.createRow(rowSum);
            for (int i = 0; i < headerArr.length; i++){
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(headerArr[i]);
                cell.setCellStyle(callStyle);
            }
            rowSum++;
        }

        // 生成导出数据
        XSSFFont font = getFont(wb, null, (short) 0, false, (short) 0);
        XSSFCellStyle callStyle = getCallStyle(wb, font, true);
        for (int i = rowSum; i < contentArr.length + rowSum; i++){
            XSSFRow row = sheet.createRow(i);
            for (int j = 0; j < contentArr[i].length; j++){
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(contentArr[i][j]);
                cell.setCellStyle(callStyle);
            }
        }

        // 客户端响应
        setResponseHeader(response,wb,fileName);
    }

    /**
     * 创建表格字体
     *
     * @param wb 工作表
     * @param fontName 字体名称
     * @param fontSize 字体大小
     * @param isBold 是否加粗
     * @param color 字体颜色
     * @return
     */
    public static XSSFFont getFont(XSSFWorkbook wb, String fontName, short fontSize, boolean isBold, short color){
        XSSFFont font = wb.createFont();
        font.setFontName((fontName == null)?"宋体":fontName);
        font.setFontHeightInPoints((fontSize == (short) 0)?(short) 12:fontSize);
        font.setColor((color == (short) 0)?Font.COLOR_NORMAL:color);
        font.setBold(isBold);
        return font;
    }

    /**
     * 获取单元格样式
     *
     * @param wb 工作表
     * @param font 字体
     * @param isWrapText 是否自动换行
     * @return
     */
    public static XSSFCellStyle getCallStyle(XSSFWorkbook wb, XSSFFont font, boolean isWrapText){
        XSSFCellStyle cellStyle = wb.createCellStyle();
        if (font != null){
            cellStyle.setFont(font);
        }
        cellStyle.setWrapText(isWrapText);
        //边框
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        //垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //居中对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    /**
     * 向客户端发送响应请求
     *
     * @param response 响应体
     * @param wb 工作表
     * @param fileName 文件名
     */
    public static void setResponseHeader(HttpServletResponse response, XSSFWorkbook wb, String fileName) throws IOException {
        //弹出下载框，并处理中文
        response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode(fileName,"utf-8"));
        //下载
        OutputStream out = response.getOutputStream();
        //写入
        wb.write(out);
        wb.close();
        out.close();
    }

    /**
     * 判断是否为Excel表格文件
     *
     * @param file 文件
     * @return
     */
    public static boolean isExcelFile(MultipartFile file){
        String fileName = file.getOriginalFilename();
        if (!fileName.matches(EXCEL_REGEX_XLS) && !fileName.matches(EXCEL_REGEX_XLSX)){
            return false;
        }
        return true;
    }

}
