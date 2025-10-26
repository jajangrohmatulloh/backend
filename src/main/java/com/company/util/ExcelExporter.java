package com.company.util;

import com.company.dto.EmployeeDto;
import com.company.dto.DepartmentDto;
import com.company.dto.LocationDto;
import com.company.dto.TierDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class ExcelExporter {

    public static void exportEmployeesToExcel(List<EmployeeDto> employees, HttpServletResponse response) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Employees");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Employee No", "Name", "Tier Code", "Location Code", "Department Code", "Supervisor Code", "Salary", "Entry Date"};
            
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderStyle(workbook));
            }
            
            // Create data rows
            int rowNum = 1;
            for (EmployeeDto employee : employees) {
                Row row = sheet.createRow(rowNum++);
                
                row.createCell(0).setCellValue(employee.getEmpno() != null ? employee.getEmpno() : 0);
                row.createCell(1).setCellValue(employee.getEmpname() != null ? employee.getEmpname() : "");
                row.createCell(2).setCellValue(employee.getTiercode() != null ? employee.getTiercode() : 0);
                row.createCell(3).setCellValue(employee.getLocationcode() != null ? employee.getLocationcode() : "");
                row.createCell(4).setCellValue(employee.getDepartmentcode() != null ? employee.getDepartmentcode() : "");
                row.createCell(5).setCellValue(employee.getSupervisorcode() != null ? employee.getSupervisorcode() : 0);
                row.createCell(6).setCellValue(employee.getSalary() != null ? employee.getSalary().doubleValue() : 0);
                row.createCell(7).setCellValue(employee.getEntrydate() != null ? employee.getEntrydate().toString() : "");
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
            
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
        }
    }
    
    public static void exportDepartmentsToExcel(List<DepartmentDto> departments, HttpServletResponse response) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Departments");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Department Code", "Department Name"};
            
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderStyle(workbook));
            }
            
            // Create data rows
            int rowNum = 1;
            for (DepartmentDto department : departments) {
                Row row = sheet.createRow(rowNum++);
                
                row.createCell(0).setCellValue(department.getDepartmentcode() != null ? department.getDepartmentcode() : "");
                row.createCell(1).setCellValue(department.getDepartmentname() != null ? department.getDepartmentname() : "");
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=departments.xlsx");
            
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
        }
    }
    
    public static void exportLocationsToExcel(List<LocationDto> locations, HttpServletResponse response) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Locations");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Location Code", "Location Name", "Location Address"};
            
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderStyle(workbook));
            }
            
            // Create data rows
            int rowNum = 1;
            for (LocationDto location : locations) {
                Row row = sheet.createRow(rowNum++);
                
                row.createCell(0).setCellValue(location.getLocationcode() != null ? location.getLocationcode() : "");
                row.createCell(1).setCellValue(location.getLocationname() != null ? location.getLocationname() : "");
                row.createCell(2).setCellValue(location.getLocationaddress() != null ? location.getLocationaddress() : "");
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=locations.xlsx");
            
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
        }
    }
    
    public static void exportTiersToExcel(List<TierDto> tiers, HttpServletResponse response) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Tiers");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Tier Code", "Tier Name"};
            
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderStyle(workbook));
            }
            
            // Create data rows
            int rowNum = 1;
            for (TierDto tier : tiers) {
                Row row = sheet.createRow(rowNum++);
                
                row.createCell(0).setCellValue(tier.getTiercode() != null ? tier.getTiercode() : 0);
                row.createCell(1).setCellValue(tier.getTiername() != null ? tier.getTiername() : "");
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=tiers.xlsx");
            
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
        }
    }
    
    private static CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
}