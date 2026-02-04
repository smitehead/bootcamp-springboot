package com.bs.boot.model.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bs.boot.model.entity.DemoEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class CriteriaRepository {
	@PersistenceContext
	private EntityManager em; // JPA의 핵심 도구, DB에 쿼리를 날리는 역할을 함
	
	public List<DemoEntity> findAllCriteria(Map<String, Object> param) {
		// 1. 쿼리 공장(Builder)과 주문서(Query) 준비
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DemoEntity> criteraQuery = cb.createQuery(DemoEntity.class);
		
		// 2. FROM 절: "어떤 테이블에서 데이터를 가져올까?" (SQL: FROM DEV)
		// Root는 쿼리의 기준점이 되는 엔티티이며, 여기서 컬럼 정보를 꺼냄
		Root<DemoEntity> demo = criteraQuery.from(DemoEntity.class);
		
		// 3. SELECT 절: "모든 컬럼을 다 가져오겠다" (SQL: SELECT *)
		criteraQuery.select(demo);
		
		// 4. WHERE 절을 위한 조건 바구니 준비
		// 사용자가 어떤 검색 조건을 넣을지 모르므로 가변 리스트로 생성
		List<Predicate> wheres = new ArrayList<>();
		
		// 5. [동적 조건 조립 1] 이름 검색 조건 (SQL: WHERE DEV_NAME LIKE %값%)
		if(param.get("name") != null && ((String)param.get("name")).length() > 0) {
			// cb.like(컬럼, 패턴): 이름 컬럼에 해당 문자열이 포함되는지 확인
			wheres.add(cb.like(demo.get("devName"), "%" + param.get("name") + "%"));
		}
		
		// 6. [동적 조건 조립 2] 나이 검색 조건 (SQL: AND DEV_AGE > 값)
		if(param.get("age") != null && param.get("age") instanceof Integer) {
			// cb.greaterThan(컬럼, 값): 나이 컬럼이 입력값보다 큰지(>) 확인
			wheres.add(cb.greaterThan(demo.get("devAge"), (int)param.get("age")));
		}
		
		// 7. 바구니에 담긴 조건들을 실제 주문서(Query)에 WHERE 절로 등록
		if(wheres.size() > 0) {
			// List를 배열로 변환하여 AND 조건으로 결합
			criteraQuery.where(wheres.toArray(new Predicate[wheres.size()]));
		}
		
		// 8. 최종 조립된 주문서를 가지고 실제 실행 가능한 쿼리 객체 생성
		TypedQuery<DemoEntity> tquery = em.createQuery(criteraQuery);
		
		// 9. DB에 쿼리 전송 및 결과 리스트 반환
		return tquery.getResultList();
	}
}