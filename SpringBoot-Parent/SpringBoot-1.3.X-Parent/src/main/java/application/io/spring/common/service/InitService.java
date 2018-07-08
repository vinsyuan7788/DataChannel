package application.io.spring.common.service;

import application.io.spring.common.exception.CommonException;

public interface InitService {
	
	public int getInitOrder();

	public void init() throws CommonException;
}
