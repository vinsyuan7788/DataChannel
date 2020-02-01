package application.io.spring.core.base.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 	This is a class to serve as a bean to be extended for identification and serialization
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
