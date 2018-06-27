package application.io.spring.technique.shiro.api.model.permission;

import org.apache.shiro.authz.Permission;
import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 	This is a class to customize a permission
 * 
 * 	The rule of this permission is as following:
 * 	-- The composition of permission: |resourceString|permissionBit|instanceId
 *     -- Assuming: 0001(1) represents insert; 0010(2) represents update; 0100(4) represents delete; 1000(8) represents select
 *     -- Then:  
 *
 * @author vinsy
 *
 */
@Getter
@Setter
public class BitPermission implements Permission {

	// Necessary instance variables
	private String resourceString;
	private Integer permissionBit;
	private String instanceId;
	
	/**
	 * 	This is a public constructor
	 * 
	 * @param permissionString
	 */
	public BitPermission(String permissionString) {
		
        String[] array = permissionString.split("\\|");
        if (array.length > 1) {  
        	resourceString = array[1];  
        }  
        if (StringUtils.isEmpty(resourceString)) {  
        	resourceString = "*";  
        }  
        if (array.length > 2) {  
            permissionBit = Integer.valueOf(array[2]);  
        }  
        if (array.length > 3) {  
            instanceId = array[3];  
        }  
        if (StringUtils.isEmpty(instanceId)) {  
            instanceId = "*";  
        } 
	}
	
	/**
	 * 	This is a method to implement how the pass-in permission (i.e., the argument) should be checked
	 * 	-- Return true if pass-in permission passes the check
	 * 	-- Return false otherwise
	 */
	@Override
	public boolean implies(Permission p) {

		// If the permission is not the instance of BitPermission
		if (!(p instanceof BitPermission)) {  
			
			// return false: fail to pass the check
            return false;  
        
		// If the permission is the instance of BitPermission
		} else {
        
			// Convert the permission to BitPermission
			BitPermission passinBitPermission = (BitPermission) p;
			
			// Check the pass-in permission
	        if (!(this.resourceString.equals("*") || !this.resourceString.equals(passinBitPermission.resourceString))) {  
	            return false;  
	        }
	        if (!(this.permissionBit == 0 || (this.permissionBit & passinBitPermission.permissionBit) != 0)) {  
	            return false;  
	        }  
	        if (!(this.instanceId.equals("*") || this.instanceId.equals(passinBitPermission.instanceId))) {  
	            return false;  
	        }  
	        return true; 
        }
	}
}
