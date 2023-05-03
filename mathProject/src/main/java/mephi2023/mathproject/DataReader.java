/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi2023.mathproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 *
 * @author Kseny
 */
public class DataReader {
    
    public DataReader (){
    } 
    
    public void ReadXLSX(DataStorage ds, String fileName, int variant) throws FileNotFoundException, IOException{
        ArrayList<ArrayList<Double>> samples = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        File file = new File(fileName);
        FileInputStream fis;
        fis = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet worksheet = workbook.getSheetAt(variant-1);
        int cols = worksheet.getRow(0).getLastCellNum();
        for (int i = 0; i < cols; ++i){
            samples.add(new ArrayList<>());
        }
        Iterator<Row> ri = worksheet.rowIterator();
        XSSFRow row1 = (XSSFRow) ri.next();
        Iterator<Cell> ci1 = row1.cellIterator();
        while(ci1.hasNext()) {
            XSSFCell cell = (XSSFCell) ci1.next();
            names.add(cell.getStringCellValue());
        }
        
        while(ri.hasNext()) {
            XSSFRow row = (XSSFRow) ri.next();
            Iterator<Cell> ci = row.cellIterator();
            int i = 0;
            while(ci.hasNext()) {
                XSSFCell cell = (XSSFCell) ci.next();
                System.out.println(cell.getNumericCellValue());
                samples.get(i).add(cell.getNumericCellValue());
                i++;
            }                
        }        
        ds.setNames(names);
        ds.setSamples(samples);       
    }
    
    private void AddNamesRow(Row row, ArrayList<String> namesSample){
        row.createCell(0).setCellValue("Parameters");
        int numbCell = 1;
        for (String s : namesSample){
            Cell nameTemp = row.createCell(numbCell++);
            nameTemp.setCellValue(s);        
        }
        
        
        
    }
    
    
    
    public void ExportXLSX(DataStorage ds, String fileName) throws FileNotFoundException, IOException{
        FileOutputStream fous;
        fous = new FileOutputStream(fileName);
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Results");
        String[] names = ds.getNamesParameters();
        String[] names2 = ds.getNamesParameters2();
        int names_length = names.length;
        int n = names_length + names2.length;
        ArrayList<ArrayList<Object>> results = ds.getResults();
        
        if (results.size() <= 0){
            throw new IOException("Данные не выгружены, т.к. они отсутствуют");
        }
        ArrayList<String> namesSample = ds.getNames();
        ArrayList<String> namesCorSample = ds.getNames2();

        int shift = 0;
        for (int i = 0; i < n; ++i){
            Row row = sheet.createRow(i+shift);
            if (i == 0){
                AddNamesRow(row, namesSample);
                shift++;
                row = sheet.createRow(i+shift);
            } 
            if (i == names_length){                
                AddNamesRow(row, namesCorSample);
                shift++;
                row = sheet.createRow(i+shift);
            }
            
            int j = 0;
            
            Cell param = row.createCell(j++);
            if (i < names_length){
                param.setCellValue(names[i]);
            } else {
                param.setCellValue(names2[i-names_length]);
            }
            
            for (ArrayList<Object> r : results){
                Cell value = row.createCell(j++);
        
                value.setCellValue(((Number) r.get(i)).doubleValue());
            }
        }
        sheet.autoSizeColumn(1);
        book.write(fous);
        book.close();   
    
    
    }
    
}
