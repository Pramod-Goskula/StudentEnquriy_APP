package in.pramod.project.service;

import java.util.List;

import in.pramod.project.binding.DashboardResponse;
import in.pramod.project.binding.EnquiryForm;
import in.pramod.project.binding.EnquirySearchCriteria;
import in.pramod.project.entity.CourseEntity;
import in.pramod.project.entity.EnquiryStatusEntity;
import in.pramod.project.entity.StudentEnquiryEntity;

public interface EnquiryService {
	
	public List<String> getstatus();
	
	public List<String> getCourseNames();
	
	public DashboardResponse dashboard(Integer userId);
	
	public String upsertEnquiry(EnquiryForm enquiry );

	public List<StudentEnquiryEntity> Search(Integer userId, EnquirySearchCriteria search);

	public List<StudentEnquiryEntity> getEnquiry (Integer userId);
	
	public EnquiryForm getEditEnquiry(Integer enquiryId);
	
}
