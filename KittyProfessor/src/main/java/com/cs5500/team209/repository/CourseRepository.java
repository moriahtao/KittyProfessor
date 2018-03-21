package com.cs5500.team209.repository;

import com.cs5500.team209.model.Course;
import com.cs5500.team209.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by mengtao on 3/18/18.
 *
 * java repo for Course in mongodb Course table
 */
public interface CourseRepository extends MongoRepository<Course, String> {
    /**
     * find course by id
     * @param id the courseId for query
     * @return the course found
     */
    Course findCourseById(@Param("id") String id);
}
