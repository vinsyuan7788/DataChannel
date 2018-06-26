package application.io.spring.common.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

public class CustomShiroLogoutFilter extends LogoutFilter {

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		
		Subject currentUser = getSubject(request, response);
		
		String redirectUrl = getRedirectUrl(request, response, currentUser);
		
		try {  
            currentUser.logout();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		
        issueRedirect(request, response, redirectUrl);  
        return false;
	}
}
