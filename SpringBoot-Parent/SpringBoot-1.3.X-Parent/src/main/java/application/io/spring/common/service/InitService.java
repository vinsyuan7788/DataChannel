package application.io.spring.common.service;

import application.io.spring.common.exception.CommonException;

public interface InitService {
	
	public int getOrder();

	public void init() throws CommonException;
}
