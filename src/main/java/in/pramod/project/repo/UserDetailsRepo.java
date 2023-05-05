package in.pramod.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.pramod.project.entity.UserDetailsEntity;

public interface UserDetailsRepo extends JpaRepository<UserDetailsEntity, Integer> {

	public UserDetailsEntity findByEmail(String email);
	
//	public UserDetailsEntity findByPassword(String password);
	
	public UserDetailsEntity findByEmailAndPassword(String email,String password);
	
}
