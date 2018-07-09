package application.middleware.authorization.technique.shiro.utils;

import java.io.Serializable;

import org.apache.shiro.subject.Subject;

import lombok.Getter;
import lombok.Setter;

/**
 * 	This is a class to encapsulate the Shiro login information
 * 
 * @author vinsy
 *
 */
@Setter
@Getter
public class LoginInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// If a subject is logged-in successfully
	private Boolean isLogin;
	
	// What the subject is
	private Subject subject;
	
	// The message that signifies login status (success or exception message)
	private String msg;

	@Override
	public String toString() {
		return "LoginInfo [isLogin=" + isLogin + ", subject=" + subject + ", msg=" + msg + "]";
	}
}