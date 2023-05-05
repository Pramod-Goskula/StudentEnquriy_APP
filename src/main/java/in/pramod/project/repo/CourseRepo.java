package in.pramod.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.pramod.project.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer> {

//	@Query("Select courseName from Courses_Names")
//	public List<String> getCourseNames();
}
