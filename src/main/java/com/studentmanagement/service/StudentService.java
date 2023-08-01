package com.studentmanagement.service;

import com.studentmanagement.dto.StudentDTO;
import com.studentmanagement.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface StudentService {
    String save(StudentDTO dto);

    String delete(long[] ids);

    List<StudentDTO> searchStudentsByFirstName(String name);

    List<StudentDTO> searchStudentsByPhoneNumber(String phoneNumber);

    ByteArrayInputStream getActualData() throws IOException;

    void importStudentFromExcelFile(MultipartFile file);

    List<StudentDTO> showAllStudentWithSort(String field);

    Page<StudentEntity> showAllStudentWithPagination(int offset, int size);

    Page<StudentEntity> showAllStudentWithPaginationAndSort(int offset, int size, String field);
}
