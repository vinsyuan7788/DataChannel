package application.io.spring.technique.springboot.utils;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * 	This is a class to serve as a base class for all identifiable Java beans (or models, POJOs, etc.)
 * 
 * @author vinsy
 *
 */
@Getter
@Setter
@Component
public class Identifiable implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
}
