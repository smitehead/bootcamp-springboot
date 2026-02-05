package com.bs.boot.rest.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.bs.boot.rest.model.entity.MemberEntity;


public interface MemberRepository extends JpaRepository<MemberEntity, String>{

}
