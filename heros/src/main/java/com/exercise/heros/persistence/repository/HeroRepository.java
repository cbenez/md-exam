package com.exercise.heros.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exercise.heros.persistence.entity.HeroEntity;
/**
 * 
 * @author CRISTIAN
 */

@Repository
public interface HeroRepository extends JpaRepository<HeroEntity,Long>{
	
	@Transactional(readOnly = true)
	List<HeroEntity> findByNombreContainingIgnoreCase(String name);

}
