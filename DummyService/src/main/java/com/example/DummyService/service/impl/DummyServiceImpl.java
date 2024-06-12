package com.example.DummyService.service.impl;


import com.example.DummyService.enums.DummyMessage;
import com.example.DummyService.model.dto.request.AddDummyRequestDto;
import com.example.DummyService.model.entity.Dummy;
import com.example.DummyService.repository.DummyRepository;
import com.example.DummyService.service.DummyService;

import com.zanbeel.customUtility.exception.ServiceException;
import com.zanbeel.customUtility.model.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DummyServiceImpl implements DummyService {
    @Autowired
    private DummyRepository dummyRepository;
    @Override
    public CustomResponseEntity<Dummy> getStudentById(Long studentId) {
        Optional<Dummy> student = dummyRepository.findById(studentId);
        if (!student.isPresent()) {
            throw new ServiceException(DummyMessage.USER_NOT_FOUND.getValue());
        }
        return CustomResponseEntity.<Dummy>builder().data(student.get()).build();
    }

    @Override
    public CustomResponseEntity<List<Dummy>> getAllStudents() {
        return CustomResponseEntity.<List<Dummy>>builder().data(dummyRepository.findAll()).build();
    }

    @Override
    public CustomResponseEntity<String> deleteStudentById(Long studentId) {
        Optional<Dummy> student = dummyRepository.findById(studentId);
        if (!student.isPresent()) {
            throw new ServiceException(DummyMessage.USER_NOT_FOUND.getValue());
        }
        dummyRepository.delete(student.get());
        return CustomResponseEntity.<String>builder().data(DummyMessage.USER_DELETED.getValue()).build();
    }

    @Override
    public CustomResponseEntity<Dummy> addStudent(AddDummyRequestDto addDummyRequestDto) {
        if ((addDummyRequestDto.getAge() == null || addDummyRequestDto.getAge() <= 0) || addDummyRequestDto.getName() == null) {
            throw new ServiceException(DummyMessage.INPUT_FIELD_NOT_PRESENT.getValue());
        }
        Dummy dummy = Dummy.builder().age(addDummyRequestDto.getAge()).name(addDummyRequestDto.getName()).build();
        dummyRepository.save(dummy);
        return CustomResponseEntity.<Dummy>builder().data(dummy).build();
    }

    @Override
    public CustomResponseEntity<Dummy> updateStudent(Long studentId, AddDummyRequestDto addDummyRequestDto) {
        Optional<Dummy> student = dummyRepository.findById(studentId);
        if (!student.isPresent()) {
            throw new ServiceException(DummyMessage.USER_NOT_FOUND.getValue());
        }
        if ((addDummyRequestDto.getAge() == null || addDummyRequestDto.getAge() <= 0) || addDummyRequestDto.getName() == null) {
            throw new ServiceException(DummyMessage.INPUT_FIELD_NOT_PRESENT.getValue());
        }
        student.get().setName(addDummyRequestDto.getName());
        student.get().setAge(addDummyRequestDto.getAge());
        dummyRepository.save(student.get());
        return CustomResponseEntity.<Dummy>builder().data(student.get()).build();
    }
}
