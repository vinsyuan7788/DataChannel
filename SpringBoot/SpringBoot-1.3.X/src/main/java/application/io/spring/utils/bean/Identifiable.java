package application.io.spring.utils.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 	This is a class to serve as a base class for all identifiable Java beans (or models, POJO, etc.)
 * 
 * @author vinsy
 *
 */
@Getter
@Setter
public class Identifiable implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
}
