package in.pramod.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.pramod.project.binding.DashboardResponse;
import in.pramod.project.binding.EnquiryForm;
import in.pramod.project.binding.EnquirySearchCriteria;
import in.pramod.project.entity.StudentEnquiryEntity;
import in.pramod.project.service.EnquiryService;

@Controller
public class EnquiryController {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private EnquiryService service;
	
	@GetMapping("/logout")
	public String logout() {
		
		session.invalidate();
		
		return "index";
	}
	
	@GetMapping("/addEnquiry")
	public String addEnquiryPage(Model model) {
		
		init(model);
		model.addAttribute("enquiryForm",new EnquiryForm());
		
		return"add-enquiry";
	}

	private void init(Model model) {
		List<String> courseNames = service.getCourseNames();
		List<String> getstatus = service.getstatus();
		model.addAttribute("course",courseNames );
		model.addAttribute("status",getstatus );
	}
	
	@PostMapping("/enquirySaved")
	public String saveEnquiry(@Validated @ModelAttribute("enquiryForm") EnquiryForm enquiry,BindingResult result , Model model) {
		init(model);
		if(result.hasErrors()) {
			return "add-enquiry";
		}
		
		String status = service.upsertEnquiry(enquiry);
		if(status.contains("success")) {
		model.addAttribute("success", "Data Saved Successfully");
			
		}
		return "add-enquiry";
	}
	
	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		DashboardResponse dashboard = service.dashboard(userId);
		
		model.addAttribute("dashboardData", dashboard);
		
		
		return "dashboard";
	}
	
	@GetMapping("/view")
	public String viewSearchPage(@RequestParam(value="courseName", required = false)String courseName,@RequestParam (value="enquiryStatus" ,required = false) String enquiryStatus, @RequestParam(value="classMode",required = false)String classMode, Model model){
		EnquirySearchCriteria searchCriteria = new EnquirySearchCriteria();
		searchCriteria.setCourseName(courseName);
		searchCriteria.setStatusName(enquiryStatus);
		searchCriteria.setClassMode(classMode);
		Integer userId = (Integer) session.getAttribute("userId");
		if(searchCriteria!=null) {
			List<StudentEnquiryEntity> search = service.Search(userId, searchCriteria);
			model.addAttribute("search", search);
	
		}
		return "viewSearchPage";
	}
	

	
	
	@GetMapping("/enquires")
	public String viewEnquiriesPage(Model model){
		init(model);
		Integer userId = (Integer) session.getAttribute("userId");
		if(userId!=null) {
			List<StudentEnquiryEntity> enquiry = service.getEnquiry(userId);
			model.addAttribute("enquiries", enquiry);
		}else {
			model.addAttribute("error", "UserId Not Found");
		}
		return"view-enquiries";
	}
	
	@GetMapping("/edit-enquiry")
	public String editEnquiry(@RequestParam(name="enquiryId") Integer enquiryId, EnquiryForm enquiry,Model model) {
		init(model);
		EnquiryForm editEnquiry = service.getEditEnquiry(enquiryId);
		model.addAttribute("enquiryForm", editEnquiry);
		return "add-enquiry";
	}

	
}
