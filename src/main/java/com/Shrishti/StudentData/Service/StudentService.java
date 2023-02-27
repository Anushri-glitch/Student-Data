package com.Shrishti.StudentData.Service;

import com.Shrishti.StudentData.Dao.IBookRepository;
import com.Shrishti.StudentData.Dao.ICourseRepository;
import com.Shrishti.StudentData.Dao.ILaptopRepository;
import com.Shrishti.StudentData.Model.Address;
import com.Shrishti.StudentData.Model.Book;
import com.Shrishti.StudentData.Model.Laptop;
import com.Shrishti.StudentData.Model.Student;
import com.Shrishti.StudentData.Dao.IStudentRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    IBookRepository bookRepository;

    @Autowired
    ILaptopRepository laptopRepository;

    @Autowired
    ICourseRepository courseRepository;

    @Autowired
    IStudentRepository studRepository;

    public String saveStudent(Student student) {
        studRepository.save(student);
        return student.getStudentName();
    }

    public JSONArray getStudent(Integer studentId) throws JSONException {
        JSONArray students = new JSONArray();
        if(studentId != null){
            if (studRepository.existsById(studentId)){
                JSONObject studentJsonObject = this.StudentToJsonObject(studRepository.findById(studentId).get());
                students.put(studentJsonObject);
            }else return null;
        }else{
            List<Student> studentList = studRepository.findAll();
            for (Student student : studentList) {
                JSONObject studentJsonObject = StudentToJsonObject(student);
                students.put(studentJsonObject);
            }
        }
        return students;
    }
    private JSONObject StudentToJsonObject(Student student) throws JSONException {
        JSONObject objStudent = new JSONObject();

        objStudent.put("studentId", student.getStudentId());
        objStudent.put("name", student.getStudentName());
        objStudent.put("age", student.getAge());
        objStudent.put("phoneNumber", student.getPhoneNumber());
        objStudent.put("branch", student.getBranch());
        objStudent.put("department", student.getDepartment());

        JSONObject objStudentAddress = new JSONObject();

        objStudentAddress.put("landmark", student.getAddress().getLandMark());
        objStudentAddress.put("zipcode", student.getAddress().getZipcode());
        objStudentAddress.put("district", student.getAddress().getDistrict());
        objStudentAddress.put("state", student.getAddress().getState());
        objStudentAddress.put("country", student.getAddress().getCountry());

        objStudent.put("address",objStudentAddress);

        return objStudent;
    }
    public JSONObject updateStudent(Student newStudent, Integer studentId) throws JSONException {
        if (studRepository.existsById(studentId)){
            Student oldStudent = studRepository.findById(studentId).get();
            this.update(newStudent, oldStudent);
            return this.StudentToJsonObject(oldStudent);
        }else return null;
    }
    private void update(Student newStudent, Student oldStudent) {
        if(newStudent.getStudentName() != null) oldStudent.setStudentName(newStudent.getStudentName());
        if(newStudent.getAge() != null) oldStudent.setAge(newStudent.getAge());
        if(newStudent.getBranch() != null) oldStudent.setBranch(newStudent.getBranch());
        if(newStudent.getPhoneNumber() != null) oldStudent.setPhoneNumber(newStudent.getPhoneNumber());
        if(newStudent.getDepartment() != null) oldStudent.setDepartment(newStudent.getDepartment());
        if (newStudent.getAddress() != null){
            Address address = new Address();

            if(newStudent.getAddress().getLandMark() != null) address.setLandMark(newStudent.getAddress().getLandMark());
            else address.setLandMark(oldStudent.getAddress().getLandMark());

            if(newStudent.getAddress().getZipcode() != null) address.setZipcode(newStudent.getAddress().getZipcode());
            else address.setZipcode(oldStudent.getAddress().getZipcode());

            if(newStudent.getAddress().getDistrict() != null) address.setDistrict(newStudent.getAddress().getDistrict());
            else address.setDistrict(oldStudent.getAddress().getDistrict());

            if(newStudent.getAddress().getState() != null) address.setState(newStudent.getAddress().getState());
            else address.setState(oldStudent.getAddress().getState());

            if(newStudent.getAddress().getCountry() != null) address.setCountry(newStudent.getAddress().getCountry());
            else address.setCountry(oldStudent.getAddress().getCountry());

            oldStudent.setAddress(address);
        }
        studRepository.save(oldStudent);
    }
    public String deleteStudent(Integer studentId) {
        String response = null;
        if (studRepository.existsById(studentId)){
            Student student = studRepository.findById(studentId).get();
            response = student.getStudentName();
            this.deleteBook(studentId);
            this.deleteLaptop(studentId);
            studRepository.deleteById(studentId);
        }
        return response;
    }
    private void deleteLaptop(Integer studentId) {
        List<Laptop> laptopList =  laptopRepository.findAll();
        for(Laptop laptop: laptopList)
            if(laptop.getStudent().getStudentId().equals(studentId))
                laptopRepository.delete(laptop);
    }
    private void deleteBook(Integer studentId){
        List<Book> bookList =  bookRepository.findAll();
        for(Book book: bookList)
            if(book.getStudent().getStudentId().equals(studentId))
                bookRepository.delete(book);
    }

    public List<Student> getAllStudent() {
        return studRepository.findAll();
    }
}
