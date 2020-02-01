package application.io.spring.business.user.api.model;

import application.io.spring.core.base.api.model.Identifiable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends Identifiable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private String password;
	
	private String uid;
	
	private String telephone;

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", uid=" + uid + ", telephone=" + telephone
				+ "]";
	}
}
