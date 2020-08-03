package com.mypoi.poi.controller;


import com.mypoi.poi.PoiApplication;
import com.mypoi.poi.common.ExcelFileUtils;
import com.mypoi.poi.entity.ReturnData;
import com.mypoi.poi.entity.Test;
import com.mypoi.poi.service.TestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * 前端控制器
 */
@CrossOrigin(origins = "*",maxAge = 3600,allowCredentials="true")//跨域*代表所有都能访问,如果设置还是提示跨域,需要设置allowCredentials=true,允许携带验证信息
@Controller
@RequestMapping("/")
public class BaseController {

    @Resource
    private TestService service;

    private static String fileUploadPath;

    @Value("${file_upload_path}")//取值application.properties
    public void setFileUploadPath(String fileUploadPath) {
        BaseController.fileUploadPath = fileUploadPath;
    }

    @RequestMapping("/")
    public String index(Model model, HttpServletResponse response, HttpServletRequest request) {
        model.addAttribute("name", "simonsfan");
        return "html/index";
    }
    @RequestMapping("/getData")
    @ResponseBody
    public ReturnData getData(HttpServletResponse response, HttpServletRequest request) {
        List<Test> list = service.getData();
        ReturnData data = new ReturnData();
        data.setData(list);
        return data;
    }


    /**
     * 实现文件上传 @throws IOException
     */
    @RequestMapping("/fileUpload")
    @ResponseBody
    public Map<String, Object> fileUpload(MultipartFile file, HttpServletRequest request) throws IOException {
        String fileName = file.getOriginalFilename();
        int lastIndexOf = fileName.lastIndexOf(".");
        String fileType = fileName.substring(lastIndexOf + 1);
        double fileSize = (double) Math.round(file.getSize() / 1024);/* kb*/
        Map<String, Object> map = new HashMap<String, Object>();
        if (file.isEmpty()) {
            map.put("status", false);
            map.put("msg", "上传文件为空");
            map.put("fileType", "");
            map.put("fileName", "");
            return map;
        }
        try {
            List<Test> list = new ArrayList<>();

            Test entity = new Test();
            List<Object> objectList = ExcelFileUtils.fileUpload(file, entity, "text", "static/xml/textXml.xml");
            for (int i = 0; i < objectList.size(); i++) {
                Test test = (Test) objectList.get(i);
                service.save(test);
            }


//            int num = message.indexOf("true");
//            if (num > -1) {
//                map.put("status", true);
//                map.put("msg", "导入成功");
//            } else {
//                map.put("status", false);
//                map.put("msg", message);
//            }
            map.put("fileType", fileType);
            map.put("fileSize", fileSize);
            map.put("fileName", fileName);
            return map;
        } catch (Exception e) {
            map.put("status", false);
            map.put("msg", e.getMessage());
            map.put("fileType", "");
            map.put("fileName", fileName);
        }
        throw new RuntimeException("upload error");
    }

    @RequestMapping("/fileDownload")
    @ResponseBody
    public void fileDownload(HttpServletRequest request, HttpServletResponse response) {

        ExcelFileUtils.fileDownload(request, response, "公司职工", "text", "static/xml/textXml.xml");
    }

    /**
     * 实现文件上传
     *
     * @throws IOException
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map<String, Object> uploadFile(MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, Object> upload = upload(file, request);
        Object status = upload.get("status");
        if (status.equals(true)) {
            String path = upload.get("path").toString();
            //组装成本地网络静态地址,"/upload"添加是因为配置了webConfig,当访问的时候webConfig会把"/upload"替换成我们的具体配置的地址,,在application.properties中
            String url2 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/upload" + path;//
            upload.put("path", url2);
        }
        return upload;
    }

    /**
     * 实现文件上传,返回文件保存位置地址包括新创建的文件夹,如  D:/file/202071/xxx.jpg      D:/file 早就存在  返回的就是/202071/xxx.jpg
     *
     * @throws IOException
     */
    public static Map<String, Object> upload(MultipartFile file, HttpServletRequest request) throws IOException {
        String fileName = file.getOriginalFilename();
        int lastIndexOf = fileName.lastIndexOf(".");
        String fileType = fileName.substring(lastIndexOf + 1);

        double fileSize = (double) Math.round(file.getSize() / 1024);// kb

        Map<String, Object> map = new HashMap<String, Object>();
        if (file.isEmpty()) {
            map.put("status", false);
            map.put("msg", "上传文件为空");
            map.put("path", "");
            map.put("fileType", "");
            map.put("fileSize", "");
            map.put("fileName", "");
            return map;
        }
        String separator = File.separator;

        Calendar cal = Calendar.getInstance();
        String dstr = "/" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DATE);
        File dir = new File(fileUploadPath + dstr);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        fileName = fileName.substring(fileName.lastIndexOf(separator) + 1);
        if (fileName.lastIndexOf(".") == -1) {
            map.put("status", false);
            map.put("msg", "上传文件为空");
            map.put("path", "");
            map.put("fileType", "");
            map.put("fileSize", "");
            map.put("fileName", "");
            return map;
        }
        String realPath2 = fileUploadPath + dstr + File.separator + fileName;
        File dest = new File(realPath2);
        File parent = dest.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        try {

            try (RandomAccessFile randomFile = new RandomAccessFile(realPath2, "rw")) {// 防止断点续传
                randomFile.seek(randomFile.length());
                randomFile.write(file.getBytes());
                randomFile.close();
            }


            map.put("status", true);
            map.put("msg", "上传文件成功");
            map.put("fileType", fileType);
            map.put("fileSize", fileSize);
            map.put("path", dstr + "/" + fileName);
            map.put("fileName", fileName);
            return map;
        } catch (IOException e) {
            map.put("status", false);
            map.put("msg", e.getMessage());
            map.put("path", "");
            map.put("fileType", "");
            map.put("fileSize", "");
            map.put("fileName", fileName);
        }
        throw new RuntimeException("upload error");
    }
    /**
     * 下载   windows还是linux 下载方式不一样
     *
     * @throws IOException
     */
    @RequestMapping("/downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InputStream inputStream = null;
        byte[] buffer = null;
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {//判断系统是windows还是linux
            URL url = new ClassPathResource("static/excelFile/easyxp.xls").getURL();
            File file = new File(url.getFile());
            inputStream = new FileInputStream(file);
        } else {
            InputStream inst = PoiApplication.class.getClassLoader().getResourceAsStream("static/excelFile/easyxp.xls");
            buffer = new byte[inst.available()];
            inst.read(buffer);
            inst.close();
            // 清空response
            response.reset();
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download;charset=utf-8");
        response.setHeader("Content-disposition", URLEncoder.encode("easyxp", "UTF-8"));
        //mime类型
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("easyxp", "UTF-8"));
        response.setHeader("Pragma", "No-cache");
        //从内存中写出来
        OutputStream outputStream = response.getOutputStream();
        if(System.getProperty("os.name").toLowerCase().startsWith("win")){
            int len = 0;
            byte[] b = new byte[1024 * 100];
            while ((len = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }
        }else{
            outputStream.write(buffer);
        }
        //释放inputstream
        try {
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        outputStream.flush();
        outputStream.close();
    }

}

