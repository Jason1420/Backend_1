package com.studentmanagement.filecsv;

import com.studentmanagement.dto.Gender;
import com.studentmanagement.entity.StudentEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class Helper {
    /**
     * Export file excel
     */
    public static String[] HEADERS = {
            "id",
            "code",
            "firstName",
            "lastName",
            "gender",
            "dateOfBirth",
            "department",
            "phoneNumber",
            "email",
            "address"
    };

    public static String SHEET_NAME = "student_data";

    // Check File Format
    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        }
        return false;
    }

    //Convert file excel to List
    public static List<StudentEntity> convertFileExcelToListStudent(InputStream is) {
        List<StudentEntity> list = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);

            XSSFSheet sheet = workbook.getSheet(SHEET_NAME);

            int rowNumber = 0;
            Iterator<Row> rows = sheet.iterator();

            while (rows.hasNext()) {
                Row row = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();
                StudentEntity s = new StudentEntity();
                int cid = 0;

                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cid) {
                        // khong duoc
                        case 0:
                            s.setId((long) cell.getNumericCellValue());
                            break;
                        case 1:
                            s.setCode((long) cell.getNumericCellValue());
                            break;
                        case 2:
                            s.setFirstName(cell.getStringCellValue());
                            break;
                        case 3:
                            s.setLastName(cell.getStringCellValue());
                            break;
                        case 4:
                            s.setGender(Gender.valueOf(cell.getStringCellValue()));
                            break;
                        case 5:
                            s.setDateOfBirth(cell.getDateCellValue());
                            break;
                        case 6:
                            s.setDepartment(cell.getStringCellValue());
                            break;
                        case 7:
                            s.setPhoneNumber(cell.getStringCellValue());
                            break;
                        case 8:
                            s.setEmail(cell.getStringCellValue());
                            break;
                        case 9:
                            s.setAddress(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cid++;
                }
                list.add(s);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Can not read file");
        }
        return list;
    }

    public static ByteArrayInputStream dataToExcel(List<StudentEntity> list) throws IOException {
        //Create workbook
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {

            //Create sheet
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            //Create header row
            Row row = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            // Value rows
            int rowIndex = 1;
            for (StudentEntity student : list) {
                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;
                dataRow.createCell(0).setCellValue(student.getId());
                dataRow.createCell(1).setCellValue(student.getCode());
                dataRow.createCell(2).setCellValue(student.getFirstName());
                dataRow.createCell(3).setCellValue(student.getLastName());
                dataRow.createCell(4).setCellValue(student.getGender().toString());
                dataRow.createCell(5).setCellValue(student.getDateOfBirth());
                dataRow.createCell(6).setCellValue(student.getDepartment());
                dataRow.createCell(7).setCellValue(student.getPhoneNumber().toString());
                dataRow.createCell(8).setCellValue(student.getEmail());
                dataRow.createCell(9).setCellValue(student.getAddress());

            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("fail to import data to excel");
            return null;
        } finally {
            workbook.close();
            out.close();
        }
    }


}
