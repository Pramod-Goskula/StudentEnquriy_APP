package in.pramod.project.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.pramod.project.entity.CourseEntity;
import in.pramod.project.entity.EnquiryStatusEntity;
import in.pramod.project.repo.CourseRepo;
import in.pramod.project.repo.EnquiryStatusRepo;
@Component
public class DataLoader implements ApplicationRunner {
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnquiryStatusRepo statusRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		
		
//
//		CourseEntity e1 = new CourseEntity();
//		e1.setCourseName("Java");
//		
//		CourseEntity e2 = new CourseEntity();
//		e2.setCourseName(".Net");
//		
//		CourseEntity e3 = new CourseEntity();
//		e3.setCourseName("Java FullStack");
//		
//		CourseEntity e4 = new CourseEntity();
//		e4.setCourseName("Python");
//		
//		CourseEntity e5 = new CourseEntity();
//		e5.setCourseName("Angular");
//		
//		CourseEntity e6 = new CourseEntity();
//		e6.setCourseName("Python FullStack");
//		
//		 courseRepo.saveAll(Arrays.asList( e1,e2,e3,e4,e5,e6));
//		
//
//		EnquiryStatusEntity s1 = new EnquiryStatusEntity();
//		s1.setStatusName("New");
//
//		EnquiryStatusEntity s2 = new EnquiryStatusEntity();
//		s2.setStatusName("Enrolled");
//		
//		EnquiryStatusEntity s3 = new EnquiryStatusEntity();
//		s3.setStatusName("Lost");
//		
//		List<EnquiryStatusEntity> list = Arrays.asList( s1,s2,s3);
//		 statusRepo.saveAll(list);
//
	}
}
