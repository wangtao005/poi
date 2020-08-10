package com.mypoi.poi.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * excel导出数据后台生成
 */
public class PoiExcelOut {

//
//    /**
//     * 单人答题导出详细
//     */
//    @RequestMapping("/single/listDate")
//    public void fileDownload(HttpServletRequest request, HttpServletResponse response, String ids) {
//        ids = ids.replaceAll(" ", "");
//        String[] split = ids.split(",");
//        List<File> waitDel = new ArrayList<>();
//        String basePath = request.getSession().getServletContext().getRealPath("");
//        File fileDir = new File(basePath + "waitDel");//创建一个文件夹用来装创建的excel文件
//        File[] files = fileDir.listFiles();//看看这个文件是否有数据,有就清空
//        if (files != null) {
//            for (File file : files) {
//                file.delete();
//            }
//        }
//        for (String s : split) {//生成excel在waitDel文件夹中
//            if (StringUtils.isBlank(s)) {
//                continue;
//            }
//            MyDataInfo myDataInfo = service.getById(s);
//            String fileName = System.currentTimeMillis() + ".xls";
//            if (!fileDir.isDirectory() && !fileDir.exists()) {
//                //创建单层目录
//                fileDir.mkdir();
//            }
//            String path = basePath + "/waitDel/" + fileName;
//            HSSFWorkbook wb = this.fileDownloadBook(myDataInfo);
//            File file = new File(path);
//            try {
//                /*生成excel 在服务器*/
//                FileOutputStream os = new FileOutputStream(path);
//                wb.write(os);
//                os.close();
//                waitDel.add(file);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            OutputStream out = response.getOutputStream();
//            ZipUtils.toZip(waitDel, out);
//            /*下载excel*/
////            response.addHeader("Content-Disposition", "attachment;filename=" + new String("123" + ".zip"));
////             response.setContentType("application/octet-stream");
////            InputStream in = new FileInputStream(fileDir);
////            int b;
////            while ((b = in.read()) != -1) out.write(b);
////            in.close();
//            out.close();
//            /*删除生成的excel文件*/
//            for (File fl : waitDel) {
//                fl.delete();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    /*构建一个excel表格*/
//    public static HSSFWorkbook fileDownloadBook(MyDataInfo myDataInfo) {
//
//        HSSFWorkbook wb = new HSSFWorkbook();/*首先创建字体样式*/
//        HSSFFont font = wb.createFont();/*创建字体样式*/
//        font.setFontName("宋体");/*使用宋体*/
//        font.setFontHeightInPoints((short) 12);/*字体大小*/
//        font.setBold(true);/* 加粗*/
//        HSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);/*居中*/
//        style.setVerticalAlignment(VerticalAlignment.CENTER);/*垂直居中*/
//        style.setFont(font);/*设置字体样式*/
//        style.setFillForegroundColor((short) 41);
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setBorderTop(BorderStyle.MEDIUM);/*上边框*/
//        style.setBorderBottom(BorderStyle.MEDIUM);/*下边框*/
//        style.setBorderLeft(BorderStyle.MEDIUM);/*左边框*/
//        style.setBorderRight(BorderStyle.MEDIUM);/*右边框*/
//
//        HSSFCellStyle styleInfo = wb.createCellStyle();
//        styleInfo.setVerticalAlignment(VerticalAlignment.CENTER);/*垂直居中*/
//        styleInfo.setFont(font);/*设置字体样式*/
//        styleInfo.setFillForegroundColor((short) 41);
//        styleInfo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        styleInfo.setBorderTop(BorderStyle.MEDIUM);/*上边框*/
//        styleInfo.setBorderBottom(BorderStyle.MEDIUM);/*下边框*/
//        styleInfo.setBorderLeft(BorderStyle.MEDIUM);/*左边框*/
//        styleInfo.setBorderRight(BorderStyle.MEDIUM);/*右边框*/
//        HSSFSheet sheet = wb.createSheet("被试作答详情");
//        sheet.setDisplayGridlines(false);
//        HSSFRow rowinfo = sheet.createRow(0);
//        rowinfo.setHeight((short) (24 * 20));
////合并单元格
//        CellRangeAddress cra = new CellRangeAddress(0, 0, 6, 7); // 起始行, 终止行, 起始列, 终止列
//        sheet.addMergedRegion(cra);
//// 使用RegionUtil类为合并后的单元格添加边框
//        RegionUtil.setBorderBottom(BorderStyle.THIN, cra, sheet); // 下边框
//        RegionUtil.setBorderLeft(BorderStyle.THIN, cra, sheet); // 左边框
//        RegionUtil.setBorderRight(BorderStyle.THIN, cra, sheet); // 有边框
//        RegionUtil.setBorderTop(BorderStyle.THIN, cra, sheet); // 上边框
//        String[] arrinfo = {"姓名", "性别", "年龄", "受教育年限", "性取向", "身高", "体重"};
//        for (int w = 0; w < arrinfo.length; w++) {
//            HSSFCell cell = rowinfo.createCell(w);
//            String value = "";
//            if (w == 0) {
//                value = myDataInfo.getName();
//            } else if (w == 1) {
//                value = myDataInfo.getSex();
//            } else if (w == 2) {
//                value = myDataInfo.getAge();
//            } else if (w == 3) {
//                value = myDataInfo.getYearsOfEducation();
//            } else if (w == 4) {
//                value = myDataInfo.getSexualOrientation();
//            } else if (w == 5) {
//                value = myDataInfo.getHeight();
//            } else if (w == 6) {
//                value = myDataInfo.getBodyWeight();
//            }
//            cell.setCellValue(arrinfo[w] + " : " + value);
//            cell.setCellStyle(styleInfo);
//        }
//        HSSFRow row = sheet.createRow(1);
//        row.setHeight((short) (24 * 20));
//        String[] arr = {"ID", "创建时间", "试次", "回答", "试次呈现的时间点", "反应时", "试次间呈现内容", "试次间呈现内容时间点"};
//        for (int i = 0; i < arr.length; i++) {
//            HSSFCell cell = row.createCell(i);
//            cell.setCellValue(arr[i]);
//            cell.setCellStyle(style);
//            sheet.setColumnWidth(i, i == 0 ? (25 * 256) : (30 * 256));/*设置第一列的宽度是31个字符宽度*/
//        }
//        HSSFCellStyle styledata = wb.createCellStyle();
//        styledata.setBorderTop(BorderStyle.MEDIUM);/*上边框*/
//        styledata.setBorderBottom(BorderStyle.MEDIUM);/*下边框*/
//        styledata.setBorderLeft(BorderStyle.MEDIUM);/*左边框*/
//        styledata.setBorderRight(BorderStyle.MEDIUM);/*右边框*/
//        List<AnswerContent> answerContents = myDataInfo.getAnswerContents();
//        for (int n = 0; n < answerContents.size(); n++) {
//            HSSFRow rows = sheet.createRow(n + 2);
//            rows.setHeight((short) (20 * 20));
//            AnswerContent ac = answerContents.get(n);
//            for (int j = 0; j < arr.length; j++) {
//                HSSFCell cell = rows.createCell(j);
//                String values = "";
//                if (j == 0) {
//                    values = ac.getId();
//                } else if (j == 1) {
//                    values = ac.getCreateDate().toString();
//                } else if (j == 2) {
//                    values = ac.getTopic();
//                } else if (j == 3) {
//                    values = ac.getAnswer();
//                } else if (j == 4) {
//                    values = ac.getTitlePresentationTime();
//                } else if (j == 5) {
//                    values = ac.getSpentTime();
//                } else if (j == 6) {
//                    values = ac.getBetweenContent();
//                } else if (j == 7) {
//                    values = ac.getBetweenContentTime();
//                }
//                cell.setCellValue(values);
//                cell.setCellStyle(styledata);
//            }
//        }
//        return wb;
//    }

}
