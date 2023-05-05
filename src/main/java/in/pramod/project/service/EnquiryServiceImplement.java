package in.pramod.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pramod.project.binding.DashboardResponse;
import in.pramod.project.binding.EnquiryForm;
import in.pramod.project.binding.EnquirySearchCriteria;
import in.pramod.project.entity.CourseEntity;
import in.pramod.project.entity.EnquiryStatusEntity;
import in.pramod.project.entity.StudentEnquiryEntity;
import in.pramod.project.entity.UserDetailsEntity;
import in.pramod.project.repo.CourseRepo;
import in.pramod.project.repo.EnquiryStatusRepo;
import in.pramod.project.repo.StudentEnqRepo;
import in.pramod.project.repo.UserDetailsRepo;

@Service
public class EnquiryServiceImplement implements EnquiryService {

	@Autowired
	private UserDetailsRepo userRepo;
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnquiryStatusRepo statusRepo;
	@Autowired
	private StudentEnqRepo studentRepo;
	@Autowired
	private HttpSession session;

	@Override
	public List<String> getstatus() {

		List<EnquiryStatusEntity> findAll = statusRepo.findAll();
		List<String> status = new ArrayList();
		for (EnquiryStatusEntity statusEntity : findAll) {
			status.add(statusEntity.getStatusName());
		}

		return status;
	}

	@Override
	public List<String> getCourseNames() {

//		 List<String> names = courseRepo.getCourseNames(); System.out.println(names);
		List<CourseEntity> findAll = courseRepo.findAll();
		List<String> course = new ArrayList();
		for (CourseEntity courseEntity : findAll) {
			course.add(courseEntity.getCourseName());
		}

		return course;
	}

	@Override
	public DashboardResponse dashboard(Integer userId) {

		DashboardResponse response = new DashboardResponse();
		Optional<UserDetailsEntity> findById = userRepo.findById(userId);

		if (findById.isPresent()) {
			UserDetailsEntity userDetailsEntity = findById.get();

			List<StudentEnquiryEntity> enquiries = userDetailsEntity.getEnquiries();
			Integer totalCount = enquiries.size();

			Integer enrolledCount = enquiries.stream().filter(e -> e.getEnquiryStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			Integer lostCount = enquiries.stream().filter(e -> e.getEnquiryStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			response.setTotalEnquiry(totalCount);
			response.setEnrolledCount(enrolledCount);
			response.setLostCount(lostCount);
		}

		return response;
	}

	@Override
	public String upsertEnquiry(EnquiryForm enquiry) {

		StudentEnquiryEntity studentEntity = new StudentEnquiryEntity();
		BeanUtils.copyProperties(enquiry, studentEntity);

		Integer userId = (Integer) session.getAttribute("userId");
		UserDetailsEntity userDetailsEntity = userRepo.findById(userId).get();
		studentEntity.setUser(userDetailsEntity);
		studentRepo.save(studentEntity);

		return "success";
	}

	@Override
	public List<StudentEnquiryEntity> Search(Integer userId, EnquirySearchCriteria search) {

		Optional<UserDetailsEntity> findById = userRepo.findById(userId);

		if (findById.isPresent()) {
			UserDetailsEntity userDetailsEntity = findById.get();

			List<StudentEnquiryEntity> enquiries = userDetailsEntity.getEnquiries();

		/*	StudentEnquiryEntity studentEnquiryEntity = new StudentEnquiryEntity();
			if (search.getCourseName() != null && search.getCourseName() != "") {
				studentEnquiryEntity.setCourseName(search.getCourseName());
			}
			if (search.getClassMode() != null && search.getClassMode() != "") {
				studentEnquiryEntity.setClassMode(search.getClassMode());
			}
			if (search.getStatusName() != null && search.getStatusName() != "") {
				studentEnquiryEntity.setEnquiryStatus(search.getStatusName());
			}
			return studentRepo.findAll(Example.of(studentEnquiryEntity));
		*/
			if(null!=search.getCourseName()&!"".equals(search.getCourseName())) {
			enquiries = enquiries.stream()
			         .filter(e -> e.getCourseName().equals(search.getCourseName()))
			         .collect(Collectors.toList());
			}
			if(null!=search.getClassMode()&!"".equals(search.getClassMode())) {
				enquiries = enquiries.stream()
				         .filter(e -> e.getClassMode().equals(search.getClassMode()))
				         .collect(Collectors.toList());
			}
			if(null!=search.getStatusName()&!"".equals(search.getStatusName())) {
				enquiries = enquiries.stream()
				         .filter(e -> e.getEnquiryStatus().equals(search.getStatusName()))
				         .collect(Collectors.toList());
				 
			}
			return enquiries;
		}

		return null;
	}

	@Override
	public List<StudentEnquiryEntity> getEnquiry(Integer userId) {

		Optional<UserDetailsEntity> findById = userRepo.findById(userId);

		if (findById.isPresent()) {
			UserDetailsEntity userDetailsEntity = findById.get();

			List<StudentEnquiryEntity> enquiries = userDetailsEntity.getEnquiries();

			return enquiries;
		}

		return null;
	}

	@Override
	public EnquiryForm getEditEnquiry( Integer enquiryId) {
		EnquiryForm enquiryForm = new EnquiryForm();
		Optional<StudentEnquiryEntity> findById = studentRepo.findById(enquiryId);
		if(findById.isPresent()) {
			StudentEnquiryEntity studentEnquiryEntity = findById.get();
			BeanUtils.copyProperties( studentEnquiryEntity,enquiryForm);
			return enquiryForm;
		}
		
		return null;
		
	}
	
	
}
