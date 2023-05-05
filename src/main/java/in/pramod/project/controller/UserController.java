package in.pramod.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.pramod.project.binding.LoginForm;
import in.pramod.project.binding.SignUpForm;
import in.pramod.project.binding.UnlockForm;
import in.pramod.project.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/login")
	public String loginPage(Model model) {
	
		model.addAttribute("login", new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("login") LoginForm login,Model model) {
		
		String status = service.login(login);
		if(status.contains("success")) {
			return "redirect:/dashboard";
		}else {
			model.addAttribute("error", status);
		}
		
		
		return "login";
	}
	
	
	

	@PostMapping("/signup")
	public String signUpSave(@Validated @ModelAttribute ("signup")  SignUpForm signup,BindingResult result ,Model model) {
		String email = signup.getEmail();
		
		if(result.hasErrors()) {
			
			return "signup";
		}
		
		boolean message = service.signup(signup);
	
		String SucessMsg= "Account Created :: Password Send To Your Email:("+email+")";
		
		if(message) {
			model.addAttribute("success", SucessMsg);
		}else {
			model.addAttribute("error", "Please Enter a Correct Email, Entered Email Exist");
	   }
		
	    return "signup";
	}
	
	@GetMapping("/signup")
	public String signupPage(SignUpForm signup,Model model) {
		
		model.addAttribute("signup", new SignUpForm());
		
	
		return "signup";
	}

	@GetMapping("/forgot")
	public String forgotPasswordPage( ) {
		
		
		return "forgotpwd";
	}
	
	@PostMapping("/forgot")
	public String forgotPassword(@RequestParam(name="email" , required = false) String email,Model model) {
		
		System.out.println(email);
		if(email!=null&&email!="") {
			boolean status = service.forgot(email);
			if(status) {
				model.addAttribute("success", "Password sent to your Email");
			}else {
				model.addAttribute("emailError","Enter Your Correct Email");
			}
			}else {
				model.addAttribute("error", "Enter email to recovery your password");
			}
		return "forgotpwd";
	}
	
	@PostMapping("/unlockAccount")
	public String unlockAccount(@ModelAttribute("unlock") UnlockForm unlock,Model model) {
		
		if(unlock.getNewPassword() != null && !unlock.getNewPassword().equals("") ) {
			if(unlock.getNewPassword().equals(unlock.getConfirmPassword())) {
				
				String status = service.unlock(unlock);
				
				model.addAttribute("status", status);
				
//					if(status) {
//						model.addAttribute("success", "Account Unlocked, Login into your Account");
//					}else {
//						model.addAttribute("error", "Enter Correct Temopary Password,Check your Email");
//					}
				
				}else {
					model.addAttribute("error", "New Password and Confirm Password Should be Same");
				}
				
		}else {
			model.addAttribute("empty", "Enter Password ");
		}
		
		
		
		return "unlock";
	}
	

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {
		
		UnlockForm unlockForm = new UnlockForm();
		unlockForm.setEmail(email);
		
		model.addAttribute("unlock",unlockForm);

		return "unlock";

	}

}
