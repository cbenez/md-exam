package com.exercise.heros.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exercise.heros.model.HeroDTO;
import com.exercise.heros.persistence.entity.HeroEntity;
import com.exercise.heros.persistence.repository.HeroRepository;

/**
 * 
 * @author CRISTIAN
 * service test para el ABMC de la entidad
 *
 */

@ExtendWith(MockitoExtension.class)
public class HeroServiceTest {
	
	@Mock
	private HeroRepository heroRepository;
	
	@InjectMocks
	private HeroService heroService;
	
	@Test
	void test_consultar_todos_heros() {
		
		HeroDTO pivot = HeroDTO.builder().nombre("uno").descripcion("larga").build();
		HeroEntity entity = HeroEntity.builder().nombre("uno").descripcion("larga").build();
		given(heroRepository.findAll()).willReturn(List.of(entity));
		
		assertEquals(heroService.getAllHero().get(0), pivot);
		
	}
	
}
