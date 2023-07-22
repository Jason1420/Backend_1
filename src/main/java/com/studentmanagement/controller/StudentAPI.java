package com.studentmanagement.controller;

import com.studentmanagement.dto.StudentDTO;
import com.studentmanagement.exception.Exception404;
import com.studentmanagement.filecsv.Helper;
import com.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StudentAPI {
    private final StudentService studentService;
    public StudentAPI(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student")
    private List<StudentDTO> showStudent(@RequestParam(value = "firstName", required = false) String firstName,
                                         @RequestParam(value = "phoneNumber", required = false) String phoneNumber) {
        List<StudentDTO> listResult = null;
        if (firstName != null) {
            listResult = studentService.searchStudentsByFirstName(firstName);
            if (listResult.size() > 0) return listResult;
            throw new Exception404("not found student");
        } else if (phoneNumber != null) {
            return studentService.searchStudentsByPhoneNumber(phoneNumber);
        } else {
            throw new Exception404();
        }
    }

    @GetMapping("/student/export")
    private ResponseEntity<Resource> exportStudent() throws IOException {
        String fileName = "student.xlsx";
        ByteArrayInputStream actualData = studentService.getActualData();
        InputStreamResource file = new InputStreamResource(actualData);
        ResponseEntity<Resource> body = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
        return body;
    }

    @PostMapping("/student")
    private String createStudent(@RequestBody @Valid StudentDTO dto) {

        return studentService.save(dto);
    }

    @PostMapping("/student/import")
    public ResponseEntity<?> importDataFromFile(@RequestParam("file")MultipartFile file){
        if(Helper.checkExcelFormat(file)){
            studentService.importStudentFromExcelFile(file);
            return ResponseEntity.ok(Map.of("Message","File is uploaded and saved to db"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload file excel");
    }
    @PutMapping("/student/import")
    public ResponseEntity<?> importDataFromFileput(@RequestParam("file")MultipartFile file){
        if(Helper.checkExcelFormat(file)){
            studentService.importStudentFromExcelFile(file);
            return ResponseEntity.ok(Map.of("Message","File is uploaded and saved to db"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload file excel");
    }

    @PutMapping("/student/{id}")
    private String updateStudent(@RequestBody @Valid StudentDTO dto, @PathVariable("id") Long id) {
        dto.setId(id);
        return studentService.save(dto);
    }

    @DeleteMapping("/student")
    private String deleteStudent(@RequestBody long[] ids) {
        return studentService.delete(ids);
    }


}
