package in.pramod.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.pramod.project.entity.StudentEnquiryEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnquiryEntity, Integer>{
	
	
}
