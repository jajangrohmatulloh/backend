package com.company.util;

import com.company.dto.EmployeeDto;
import com.company.dto.DepartmentDto;
import com.company.dto.LocationDto;
import com.company.dto.TierDto;
import com.opencsv.CSVWriter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class CsvExporter {

    public static void exportEmployeesToCsv(List<EmployeeDto> employees, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=employees.csv");

        ServletOutputStream outputStream = response.getOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
        CSVWriter csvWriter = new CSVWriter(streamWriter);

        // Write header
        String[] headers = {"Employee No", "Name", "Tier Code", "Location Code", "Department Code", "Supervisor Code", "Salary", "Entry Date"};
        csvWriter.writeNext(headers);

        // Write data
        for (EmployeeDto employee : employees) {
            String[] data = new String[8];
            data[0] = employee.getEmpno() != null ? employee.getEmpno().toString() : "";
            data[1] = employee.getEmpname() != null ? employee.getEmpname() : "";
            data[2] = employee.getTiercode() != null ? employee.getTiercode().toString() : "";
            data[3] = employee.getLocationcode() != null ? employee.getLocationcode() : "";
            data[4] = employee.getDepartmentcode() != null ? employee.getDepartmentcode() : "";
            data[5] = employee.getSupervisorcode() != null ? employee.getSupervisorcode().toString() : "";
            data[6] = employee.getSalary() != null ? employee.getSalary().toString() : "";
            data[7] = employee.getEntrydate() != null ? employee.getEntrydate().toString() : "";
            csvWriter.writeNext(data);
        }
        
        csvWriter.flush();
        csvWriter.close();
    }
    
    public static void exportDepartmentsToCsv(List<DepartmentDto> departments, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=departments.csv");

        ServletOutputStream outputStream = response.getOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
        CSVWriter csvWriter = new CSVWriter(streamWriter);

        // Write header
        String[] headers = {"Department Code", "Department Name"};
        csvWriter.writeNext(headers);

        // Write data
        for (DepartmentDto department : departments) {
            String[] data = new String[2];
            data[0] = department.getDepartmentcode() != null ? department.getDepartmentcode() : "";
            data[1] = department.getDepartmentname() != null ? department.getDepartmentname() : "";
            csvWriter.writeNext(data);
        }
        
        csvWriter.flush();
        csvWriter.close();
    }
    
    public static void exportLocationsToCsv(List<LocationDto> locations, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=locations.csv");

        ServletOutputStream outputStream = response.getOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
        CSVWriter csvWriter = new CSVWriter(streamWriter);

        // Write header
        String[] headers = {"Location Code", "Location Name", "Location Address"};
        csvWriter.writeNext(headers);

        // Write data
        for (LocationDto location : locations) {
            String[] data = new String[3];
            data[0] = location.getLocationcode() != null ? location.getLocationcode() : "";
            data[1] = location.getLocationname() != null ? location.getLocationname() : "";
            data[2] = location.getLocationaddress() != null ? location.getLocationaddress() : "";
            csvWriter.writeNext(data);
        }
        
        csvWriter.flush();
        csvWriter.close();
    }
    
    public static void exportTiersToCsv(List<TierDto> tiers, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=tiers.csv");

        ServletOutputStream outputStream = response.getOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
        CSVWriter csvWriter = new CSVWriter(streamWriter);

        // Write header
        String[] headers = {"Tier Code", "Tier Name"};
        csvWriter.writeNext(headers);

        // Write data
        for (TierDto tier : tiers) {
            String[] data = new String[2];
            data[0] = tier.getTiercode() != null ? tier.getTiercode().toString() : "";
            data[1] = tier.getTiername() != null ? tier.getTiername() : "";
            csvWriter.writeNext(data);
        }
        
        csvWriter.flush();
        csvWriter.close();
    }
}