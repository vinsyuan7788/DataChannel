//package application.io.spring.technique.shiro.api.model;
//
//import java.util.Date;
//
//import application.io.spring.core.base.api.model.Identifiable;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class AuthorizationUser extends Identifiable {
//
//	private static final long serialVersionUID = 1L;
//	
//    // username
//    private String name;
//
//    // password
//    private String password;
//
//    // attr: currently unaware of its business meaning
//    private String attr;
//
//    // user's real name
//    private String realName;
//
//    // gender
//    private String gender;
//
//    // birthday
//    private Date birthday;
//
//    // office phone number
//    private String officePhone;
//
//    // mobile phone number
//    private String mobile;
//
//    // home phone number
//    private String homePhone;
//
//    // email
//    private String email;
//
//    // remark
//    private String remark;
//
//    // creator ID
//    private Long creator;
//
//    // status: active, suspended, deleted
//    private String status;
//
//    // create time
//    private Date createTime;
//
//    // user's code
//    private String code;
//
//    // if user is fingerprint login
//    private String fingerprintLogin;
//
//    // allowable login type: single-client login, multiple-clients login, etc.
//    private String allowLoginType;
//
//    // if user is an internal employee
//    private String isInternalEmployee;
//
//    /**
//     * 	For output display
//     */
//	@Override
//	public String toString() {
//		return "AuthorizationUser [name=" + name + ", password=" + password + ", attr=" + attr + ", realName="
//				+ realName + ", gender=" + gender + ", birthday=" + birthday + ", officePhone=" + officePhone + ", mobile="
//				+ mobile + ", homePhone=" + homePhone + ", email=" + email + ", remark=" + remark + ", creator="
//				+ creator + ", status=" + status + ", createTime=" + createTime + ", code=" + code
//				+ ", fingerprintLogin=" + fingerprintLogin + ", allowLoginType=" + allowLoginType
//				+ ", isInternalEmployee=" + isInternalEmployee + "]";
//	}
//}
