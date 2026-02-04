package com.bs.boot.model.demo.service;

import java.util.List;

import com.bs.boot.model.dto.Demo;

public interface DemoService {

	List<Demo> searchDemoAll();
	Demo searchDemoById(Integer devNo);
	boolean insertDemo(Demo demo);
	List<Demo> findByDevName(String name);
	List<Demo> searchDemoByAgeGreater(Integer age);
}
