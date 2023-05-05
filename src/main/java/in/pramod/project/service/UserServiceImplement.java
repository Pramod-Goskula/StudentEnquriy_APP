package in.pramod.project.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pramod.project.binding.LoginForm;
import in.pramod.project.binding.SignUpForm;
import in.pramod.project.binding.UnlockForm;
import in.pramod.project.entity.UserDetailsEntity;
import in.pramod.project.repo.UserDetailsRepo;
import in.pramod.project.utils.EmailUtils;
import in.pramod.project.utils.PasswordUtils;

@Service
public class UserServiceImplement implements UserService {

	@Autowired
	private UserDetailsRepo userRepo;

	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private HttpSession session;

	@Override
	public String login(LoginForm login) {
		UserDetailsEntity byEmailAndPassword = userRepo.findByEmailAndPassword(login.getEmail(), login.getPassword());
		if (byEmailAndPassword == null) {
			return "Invalid Login Credential";
		}

		if (byEmailAndPassword.getAccStatus().equals("Locked")) {

			return "Your account is Locked,Please Unlock Your Account And Try Login";
		}

		Integer userId = byEmailAndPassword.getUserId();
		session.setAttribute("userId",userId );
		
		return "success";
	}

	@Override
	public boolean signup(SignUpForm signup) {

		UserDetailsEntity byEmail = userRepo.findByEmail(signup.getEmail());
		if (byEmail != null) {

			return false;

		}
		// TODO :: Copy Form data into entity`object
		UserDetailsEntity entity = new UserDetailsEntity();
		BeanUtils.copyProperties(signup, entity);

		System.out.println(entity);

		// TODO :: Set AccStatus as Locked
		entity.setAccStatus("Locked");

		// TODO :: Generate Random Password and Store to entity
		String generatePwd = PasswordUtils.generatePwd();
		entity.setPassword(generatePwd);

		// TODO :: Save Data into Database
		userRepo.save(entity);

		// TODO :: Send Email With Password as attachment
		String to = signup.getEmail();

		String subject = "This is your Password||ASHOK IT";

		StringBuffer body = new StringBuffer("");

		body.append("<h2>Use This Temporary Password To unlock Your Account </h2>");
		body.append("Temporary Password :: " + generatePwd + "<br>");
		body.append("Use below Link  to unlock your account.<br>");
		body.append("<a href=\"http://localhost:8081/unlock?email=" + to + "\">Unlock Your Account</a>");

		emailUtils.sendMail(to, subject, body.toString());

		return true;
	}

	@Override
	public boolean forgot(String email) {

		System.out.println(email);

		UserDetailsEntity byEmail = userRepo.findByEmail(email);
		if (byEmail.getEmail() != null) {

			String to = byEmail.getEmail();
			String password = byEmail.getPassword();
			String subject = "Recovery Password";
			String body = "<h6>Login to your account using below  Password ::</h6><h4>" + password + "</h4>";

			emailUtils.sendMail(to, subject, body);

		} else {
			return false;
		}

		return true;
	}

	@Override
	public String unlock(UnlockForm unlock) {

		/*
		 * UserDetailsEntity findByPassword =
		 * userRepo.findByPassword(unlock.getTemporaryPassword());
		 * 
		 * if (unlock.getTemporaryPassword().equals(findByPassword.getPassword())) {
		 * 
		 * if (findByPassword != null) {
		 * 
		 * findByPassword.setAccStatus("Unlocked");
		 * findByPassword.setPassword(unlock.getNewPassword());
		 * userRepo.save(findByPassword);
		 * 
		 * } return true; }
		 */
		UserDetailsEntity byEmail = userRepo.findByEmail(unlock.getEmail());

		if (!byEmail.getAccStatus().equals("Unlocked")) {

			if (unlock.getTemporaryPassword().equals(byEmail.getPassword())) {
				byEmail.setAccStatus("Unlocked");
				byEmail.setPassword(unlock.getNewPassword());
				userRepo.save(byEmail);

			} else {
				return "Please Enter valid Temporary Password ";
			}

		} else {
			return "Account Already Unlocked :: Login to Your Account ";
		}

		return "Account Unlocked ::  Login to Your Account ";

	}
}
