package com.Shrishti.StudentData.Service;

import com.Shrishti.StudentData.Dao.ILaptopRepository;
import com.Shrishti.StudentData.Dao.IStudentRepository;
import com.Shrishti.StudentData.Model.Laptop;
import com.Shrishti.StudentData.Model.Student;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaptopService {
    @Autowired
    IStudentRepository StudentRepository;
    @Autowired
    ILaptopRepository laptopRepository;
    public String saveLaptop(Laptop laptop, Integer studentId) {
        String response = null;
        if(StudentRepository.existsById(studentId)) {
            List<Laptop> laptopList = laptopRepository.findAll();
            for(Laptop laptops: laptopList)
                if(laptops.getStudent().getStudentId().equals(studentId))
                    return "";

            Student student = StudentRepository.findById(studentId).get();
            laptop.setStudent(student);
            laptopRepository.save(laptop);
            response = laptop.getName();
        }
        return response;
    }
    public JSONArray getLaptop(Integer laptopId, Integer studentId) throws JSONException {
        JSONArray laptops = new JSONArray();
        if(studentId == null && laptopId != null && laptopRepository.existsById(laptopId)){
            Laptop laptop = laptopRepository.findById(laptopId).get();
            JSONObject laptopObj = this.jsonObjectToLaptop(laptop);
            laptops.put(laptopObj);
        } else if(studentId != null) {
            List<Laptop> laptopList = laptopRepository.findAll();
            for(Laptop laptop: laptopList) {
                if(laptop.getStudent().getStudentId().equals(studentId)){
                    JSONObject laptopObj = this.jsonObjectToLaptop(laptop);
                    laptops.put(laptopObj);
                }
            }
        } else{
            List<Laptop> laptopList = laptopRepository.findAll();
            for(Laptop laptop: laptopList) {
                JSONObject laptopObj = this.jsonObjectToLaptop(laptop);
                laptops.put(laptopObj);
            }
        }
        return laptops;
    }
    private JSONObject jsonObjectToLaptop(Laptop laptop) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("laptopId", laptop.getLaptopId());
        obj.put("name", laptop.getName());
        obj.put("brand", laptop.getBrand());
        obj.put("price", laptop.getPrice());
        return obj;
    }
    public String deleteLaptop(Integer laptopId) {
        String response = null;
        if(laptopRepository.existsById(laptopId)){
            response = laptopRepository.findById(laptopId).get().getName();
            laptopRepository.deleteById(laptopId);
        }
        return response;
    }

    public String updateLaptop(Laptop newLaptop, Integer laptopId) {
        String response = null;
        if(laptopRepository.existsById(laptopId)){
            Laptop oldLaptop = laptopRepository.findById(laptopId).get();
            this.update(oldLaptop, newLaptop);
            laptopRepository.save(oldLaptop);
            response = oldLaptop.getName();
        }
        return response;
    }
    private void update(Laptop oldLaptop, Laptop newLaptop) {
        if(newLaptop.getPrice() != null) oldLaptop.setPrice(newLaptop.getPrice());
        if(newLaptop.getName() != null) oldLaptop.setName(newLaptop.getName());
        if(newLaptop.getBrand() != null) oldLaptop.setBrand(newLaptop.getBrand());
    }
}

