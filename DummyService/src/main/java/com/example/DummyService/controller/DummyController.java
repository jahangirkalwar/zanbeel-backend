    package com.example.DummyService.controller;


    import com.example.DummyService.model.dto.request.AddDummyRequestDto;
    import com.example.DummyService.model.entity.Dummy;
    import com.example.DummyService.service.DummyService;
    import com.zanbeel.customUtility.exception.GlobalExceptionHandler;
    import com.zanbeel.customUtility.model.CustomResponseEntity;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping(value = "/v1/student")
    public class DummyController extends GlobalExceptionHandler {
        @Autowired
        private DummyService dummyService;

        @PostMapping()
        public CustomResponseEntity<Dummy> addStudent(@RequestBody AddDummyRequestDto addDummyRequestDto) {
            return dummyService.addStudent(addDummyRequestDto);
        }
        @PutMapping(value = "/{studentId}")
        public CustomResponseEntity<Dummy> updateStudent(@PathVariable Long studentId, @RequestBody AddDummyRequestDto addDummyRequestDto) {
            return dummyService.updateStudent(studentId, addDummyRequestDto);
        }

        @GetMapping(value = "/{studentId}")
        public CustomResponseEntity<Dummy> getStudentById(@PathVariable Long studentId) {
            return dummyService.getStudentById(studentId);
        }

        @GetMapping()
        public CustomResponseEntity<List<Dummy>> getAllStudent() {
            return dummyService.getAllStudents();
        }
        @DeleteMapping(value = "/{studentId}")
        public CustomResponseEntity<String> deleteStudentById(@PathVariable Long studentId) {
            return dummyService.deleteStudentById(studentId);
        }

    }
