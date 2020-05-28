package com.example.poi.controller;


import com.example.poi.common.ExcelFileUtils;
import com.example.poi.entity.Test;
import com.example.poi.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  前端控制器
 */
@Controller
@RequestMapping("/")
public class BaseController {

    @Resource
    private TestService service;

    @RequestMapping("/")
    public String index(Model model, HttpServletResponse response,HttpServletRequest request) {
        model.addAttribute("name", "simonsfan");
        return "/html/index";
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
            Test entity = new Test();
            List<Object> objectList = ExcelFileUtils.fileUpload(file, entity, "text", "static/xml/textXml.xml");
            for (int i = 0; i < objectList.size(); i++) {
                Object o =  objectList.get(i);
                System.out.println(o);
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
        ExcelFileUtils.fileDownload(request,response,"公司职工","text","static/xml/textXml.xml");
    }

}

