/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi2023.mathproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
    
}
