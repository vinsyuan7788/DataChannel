package application.io.spring.technique.springboot.api.model;

import java.util.Date;

import application.io.spring.core.base.api.model.Identifiable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpringBoot extends Identifiable {

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
		return "SpringBoot [name=" + name + ", version=" + version + ", releaseTime=" + releaseTime + ", officialUrl="
				+ officialUrl + ", contributor=" + contributor + ", remark=" + remark + ", extendedField="
				+ extendedField + "]";
	}
}
