package in.pramod.project.service;

import in.pramod.project.binding.LoginForm;
import in.pramod.project.binding.SignUpForm;
import in.pramod.project.binding.UnlockForm;

public interface UserService {

	public String login(LoginForm login);

	public boolean signup(SignUpForm signup);

	public boolean forgot(String email);

	public String unlock(UnlockForm unlock);

}
