package application.io.spring.technique.mybatis.api.model;

import java.util.Date;

import application.io.spring.core.base.api.model.Identifiable;
import lombok.Getter;
import lombok.Setter;

/**
 * 	This is a Java bean for encapsulation the information corresponding to "z_my_batis" table in the database
 * 
 * @author vinsy
 *
 */
@Getter
@Setter
public class MyBatis extends Identifiable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String version;
	
	private Date releaseTime;
	
	private String officialUrl;
	
	private String contributor;
	
	private String remark;
	
	private Object extendedField;

	@Override
	public String toString() {
		return "MyBatis [name=" + name + ", version=" + version + ", releaseTime=" + releaseTime + ", officialUrl="
				+ officialUrl + ", contributor=" + contributor + ", remark=" + remark + ", extendedField="
				+ extendedField + "]";
	}
}
