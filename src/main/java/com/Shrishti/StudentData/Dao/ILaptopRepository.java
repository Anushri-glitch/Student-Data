package com.Shrishti.StudentData.Dao;

import com.Shrishti.StudentData.Model.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILaptopRepository extends JpaRepository<Laptop, Integer> {
}
