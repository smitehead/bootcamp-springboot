package com.bs.boot.model.demo.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bs.boot.model.entity.DemoEntity;

@Repository//이놈이 dao역활을 하는 놈으로 extends jap만 해주면 자동으로 세션부터 기본 ddl을 전부 넣을 수 있다
public interface DemoRepository extends JpaRepository<DemoEntity,Integer>{
	
	//쿼리메소드만들기
	//이름에 매개변수값이랑 일치하는 튜플 찾기 demo조회하기
	//방식 : findBy필드명[연산자][논리연산자][필드명][연산자]...[Orderby필드명 ace/dece] 규칙으로 사용함
	
	Stream<DemoEntity> findByDevAgeGreaterThanEqual(Integer age);

	Stream<DemoEntity> findByDevName(String name);
	
	
	//jpql구문 이용하기
	//Query어노테이션 이용
	@Query("select d from DemoEntity d")
	public List<DemoEntity> selectDemoAll();
	
	
	//매개변수 처리하기 
	//인덱스 번호, key 방식 
	//이 두개를 사용한다
	
	//쉬뻐 이거 인덱스 처리방식 : ?넣으면 매개변수가 자동으로 들어감
	@Query("select d from DemoEntity d where d.devAge >= ?1 and d.devName like ?2")
	public List<DemoEntity> selectDemoByAgeAndName(Integer age,String name);
	
	
	//keyvalue 방식
	@Query("select d from DemoEntity d where d.devGender = :gender")
	public List<DemoEntity> selectGender(@Param(value="gender")String gender);
	
	@Query(value="SELECT * FROM DEV",nativeQuery=true)
	public List<DemoEntity> selectNativeAll();
	
}
