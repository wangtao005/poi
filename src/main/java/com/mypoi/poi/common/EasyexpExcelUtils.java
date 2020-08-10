package com.mypoi.poi.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.mics.lang.file.ExcelReadWriteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * excel工具类 
 * @author mics
 * @date 2018年7月3日
 * @version 1.0
 */
public class EasyexpExcelUtils {
	
	
	
	
	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * @param inputStream 读取数据的源Excel
	 * @param ignoreRows 读取数据忽略的行数，比如行头不需要读入 忽略的行数为1
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String[][] getData( InputStream inputStream,  int ignoreRows){
		if(inputStream == null){
			throw new IllegalArgumentException("读取文件错误，文件不能为空！");
		}
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		try{
			BufferedInputStream in = new BufferedInputStream(inputStream);
			POIFSFileSystem fs = new POIFSFileSystem(in);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFCell cell = null;

			HSSFSheet titleInfo = wb.getSheetAt(0);
			HSSFRow rowFirst = titleInfo.getRow(1);//获取第一行
			int lastCellNum = rowFirst.getLastCellNum();//有多少标题列

//			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
				HSSFSheet st = wb.getSheetAt(0);
				// 第一行为标题，不取
				for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
					HSSFRow row = st.getRow(rowIndex);
					if (row == null) {
						continue;
					}
					int tempRowSize = lastCellNum;
					if (tempRowSize > rowSize) {
						rowSize = tempRowSize;
				}
					String[] values = new String[lastCellNum];
					Arrays.fill(values, "");
					for (int columnIndex = 0; columnIndex < lastCellNum; columnIndex++) {
						String value = "";
						cell = row.getCell(columnIndex);
						if (cell != null) {
							// 注意：一定要设成这个，否则可能会出现乱码
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									Date date = cell.getDateCellValue();
									if (date != null) {
										value = new SimpleDateFormat("yyyy-MM-dd").format(date);
									} else {
										value = "";
									}
								} else {
									value = new DecimalFormat("0").format(cell.getNumericCellValue());
								}
								break;
							case HSSFCell.CELL_TYPE_FORMULA:
								// 导入时如果为公式生成的数据则无值
								if (!cell.getStringCellValue().equals("")) {
									value = cell.getStringCellValue();
								} else {
									value = cell.getNumericCellValue() + "";
								}
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								value="";
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								value = "";
								break;
							case HSSFCell.CELL_TYPE_BOOLEAN:
								value = (cell.getBooleanCellValue() == true ? "Y" : "N");
								break;
							default:
								value = "";
							}
						}
						value =  value.replaceAll(" ", "");
						value =  value.replaceAll("，", ",");
						values[columnIndex] = value;
					}
						result.add(values);
				}
//			}
			in.close();
			String[][] returnArray = new String[result.size()][rowSize];
			for (int i = 0; i < returnArray.length; i++) {
				returnArray[i] = (String[]) result.get(i);
			}
			return returnArray;
		}catch(Exception e){
			e.printStackTrace();
			throw new ExcelReadWriteException("打开文件错误！");
		}

	}

	/**
	 * 简单的自定义需要导出的数据列
	 * @param datas 数据集合
	 * @param title 表格标题(使用英文,连接)
	 * @param key  表格列名(key)(使用英文,连接)
	 * @param name 导出excle的名称(最好不要使用特殊符号)
	 * @param response
	 */
	public void exportData( List<Map<String,Object>> datas,String title, String key, String name, HttpServletResponse response){
		if(datas == null || datas.size()== 0){
			throw new IllegalArgumentException("导出表格数据不能为空！");
		}
		if(StringUtils.isBlank(key)){
			throw new IllegalArgumentException("表格标题不能为空！");
		}
		if( StringUtils.isBlank(title)){
			throw new IllegalArgumentException("表格列名不能为空！");
		}
        String [] titles = title.split(",");
        
        String [] keys = key.split(",");
        
        List<ExcelExportEntity> entitys = new ArrayList<ExcelExportEntity>();
        for (int i = 0; i < titles.length; i++) {
        	entitys.add(new ExcelExportEntity(titles[i], keys[i]));
		}
	    HSSFWorkbook workbook = (HSSFWorkbook) ExcelExportUtil.exportExcel(new ExportParams(name, name), entitys, datas);
	    response.setContentType("application/x-download");  
	    //设置导出文件名称  
	    try {
			response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(name, "UTF-8")+".xls");
			OutputStream os = response.getOutputStream();
			workbook.write(os);
			os.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出指定路径excel
	 * @author mics
	 * @date 2019年11月9日
	 * @version  1.0
	 */
	public void downloadExcel(HttpServletResponse response, HttpServletRequest request, String excelPath, String fileName) {
        try {
            //获取文件的路径
        	 File path =   new File(this.getClass().getClassLoader().getResource("classpath:").getPath());  
            // 读到流中
            InputStream inStream = new FileInputStream(path+excelPath);//文件的存放路径
            // 设置输出的格式
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 循环取出流中的数据
            byte[] b = new byte[2048];
            int len;

            while ((len = inStream.read(b)) > 0){
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
