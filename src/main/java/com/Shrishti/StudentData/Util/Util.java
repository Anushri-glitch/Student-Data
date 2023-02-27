package com.Shrishti.StudentData.Util;

import com.Shrishti.StudentData.Dao.IStudentRepository;
import com.Shrishti.StudentData.Model.Course;
import com.Shrishti.StudentData.Model.Student;
import lombok.experimental.UtilityClass;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Util {
    @Autowired
    IStudentRepository studentRepository;

    public Course StringJsonToCourse(String courseStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(courseStr);
        Course course = new Course();
        course.setTitle(jsonObject.getString("title"));
        course.setDescription(jsonObject.getString("description"));
        course.setDuration(jsonObject.getString("duration"));

        String studentList = jsonObject.getString("studentList");
        String[] studentListArray = studentList.split(",");
        List<Student> studentListList = new ArrayList<>();
        for (String studentId : studentListArray) {
            if (studentRepository.existsById(Integer.valueOf(studentId))) {
                studentListList.add(studentRepository.findById(Integer.valueOf(studentId)).get());
            }
        }
        course.setStudentList(studentListList);

        return course;
    }
}
