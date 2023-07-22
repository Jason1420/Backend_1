package com.studentmanagement.service.impl;

import com.studentmanagement.converter.StudentConverter;
import com.studentmanagement.dto.StudentDTO;
import com.studentmanagement.entity.StudentEntity;
import com.studentmanagement.filecsv.Helper;
import com.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements com.studentmanagement.service.StudentService {
    private final StudentRepository studentRepository;
    private final StudentConverter studentConverter;
    private final Helper helper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentConverter studentConverter, Helper helper) {
        this.studentRepository = studentRepository;
        this.studentConverter = studentConverter;
        this.helper = helper;
    }

    @Override
    public String save(StudentDTO dto) {
        if (dto.getId() != null) {
            StudentEntity oldEntity = studentRepository.findOneById(dto.getId());
            StudentEntity entity = studentConverter.toEntity(dto, oldEntity);
            studentRepository.save(entity);
            return "Successfully update!";
        } else {
            if (studentRepository.findByCode(dto.getCode()) != null) {
                return "Code existed!";
            }
            StudentEntity entity = studentConverter.toEntity(dto);
            studentRepository.save(entity);
            dto = studentConverter.toDTO(entity);
            return "new student was created with code : " + dto.getCode();
        }

    }

    @Override
    public String delete(long[] ids) {
        for (long item : ids) {
            studentRepository.deleteById(item);
        }
        return "Successfully delete!!";
    }

    @Override
    public List<StudentDTO> searchStudentsByFirstName(String firstName) {
        List<StudentDTO> listDTO = new ArrayList<>();
        List<StudentEntity> listEntity = studentRepository.findByFirstNameContainingIgnoreCase(firstName);
        for (StudentEntity item : listEntity) {
            listDTO.add(studentConverter.toDTO(item));
        }
        return listDTO;
    }

    @Override
    public List<StudentDTO> searchStudentsByPhoneNumber(String phoneNumber) {
        List<StudentDTO> listDTO = new ArrayList<>();
        List<StudentEntity> listEntity = studentRepository.findByPhoneNumberContaining(phoneNumber);
        for (StudentEntity item : listEntity) {
            listDTO.add(studentConverter.toDTO(item));
        }
        return listDTO;
    }
    @Override
    public ByteArrayInputStream getActualData() throws IOException {
        List<StudentEntity> all = studentRepository.findAll();
        ByteArrayInputStream byteArrayInputStream = helper.dataToExcel(all);
        return byteArrayInputStream;
    }

    @Override
    public void importStudentFromExcelFile(MultipartFile file) {
        try{
            List<StudentEntity> list = helper.convertFileExcelToListStudent(file.getInputStream());
            studentRepository.saveAll(list);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

}
