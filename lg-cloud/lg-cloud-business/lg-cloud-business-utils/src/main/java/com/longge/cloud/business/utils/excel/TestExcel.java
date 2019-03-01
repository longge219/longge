package com.longge.cloud.business.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestExcel {
    public static void main(String[] args)  throws FileNotFoundException {
        File file = new File("D:\\数据.xlsx");
        InputStream inputStream = new FileInputStream(file);
        // 从excel中获取数据, 封装入ExcelData
        List<ExcelColumnTypeEnum> cellTypes = Arrays.asList(ExcelColumnTypeEnum.STRING, ExcelColumnTypeEnum.STRING,
                ExcelColumnTypeEnum.STRING, ExcelColumnTypeEnum.STRING, ExcelColumnTypeEnum.STRING, ExcelColumnTypeEnum.STRING,
                ExcelColumnTypeEnum.STRING, ExcelColumnTypeEnum.STRING, ExcelColumnTypeEnum.STRING, ExcelColumnTypeEnum.STRING,
                ExcelColumnTypeEnum.STRING, ExcelColumnTypeEnum.STRING);
        ExcelData excelData = ExcelUtils.readExcel(inputStream, ".xlsx", cellTypes);
        System.out.println(excelData.getRows());
        List<List<Object>> rows = excelData.getRows();
        List<String> strings = new ArrayList<>();
        for (List<Object> row : rows) {
            String collect = row.stream().map(String::valueOf).collect(Collectors.joining(","));
            String dateString = "'"+collect.substring(collect.lastIndexOf(",")+1)+"'";
            strings.add("("+collect.substring(0,collect.lastIndexOf(","))+","+dateString+")");
        }
        System.out.println(strings);
    }
}
