package com.bs.boot.model.demo.service;

import java.util.List;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bs.boot.model.demo.repository.DemoRepository;
import com.bs.boot.model.dto.Demo;
import com.bs.boot.model.entity.DemoEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DemoServiceImpl implements DemoService {
	private final DemoRepository demoRepository;

	@Override
	public List<Demo> searchDemoAll() {
		// TODO Auto-generated method stub
//		Pageable pageable=PageRequest.of(0, 5);
		Pageable pageable=PageRequest.of(0, 5,Sort.by("devAge").descending());
		//정렬해서 리스트 가져오기
		return demoRepository.findAll(pageable).stream().map(DemoEntity::convert).toList();
	}

	@Override
	public Demo searchDemoById(Integer devNo) {
		// TODO Auto-generated method stub
		return demoRepository.findById(devNo).orElseThrow().convert();
	}
	
	
	//애는 반환형이 보이드
	@Override
	public boolean insertDemo(Demo demo) {
		try {
			demoRepository.save(demo.convert());
			return true;
		} catch (RuntimeException e) {
			// TODO: handle exception
			return false;
		}
		
	}
	@Transactional(readOnly = true)
	@Override
	public List<Demo> findByDevName(String name) {
		// TODO Auto-generated method stub
		return demoRepository.findByDevName(name).map(DemoEntity::convert).toList();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Demo> searchDemoByAgeGreater(Integer age) {
		// TODO Auto-generated method stub
		return demoRepository.findByDevAgeGreaterThanEqual(age).map(DemoEntity::convert).toList();
	}



}
