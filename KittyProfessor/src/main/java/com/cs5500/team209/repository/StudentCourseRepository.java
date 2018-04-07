package com.cs5500.team209.repository;

import com.cs5500.team209.model.StudentCourse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by mengtao on 3/18/18.
 *
 * java repo for Course in mongodb Course table
 */
public interface StudentCourseRepository extends MongoRepository<StudentCourse, String> {
    /**
     * find course by id
     * @param id the courseId for query
     * @return the course found
     */
    StudentCourse findStudentCourseByStudentCourseId(@Param("studentCourseId") String id);

    List<StudentCourse> findStudentCourseByCourseId(@Param("courseId") String id);

    List<StudentCourse> findStudentCourseByUserName(@Param("userName") String userName);

}
