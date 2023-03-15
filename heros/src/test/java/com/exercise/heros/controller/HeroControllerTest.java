package com.exercise.heros.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.exercise.heros.model.HeroDTO;
import com.exercise.heros.service.HeroService;
/**
 * 
 * @author CRISTIAN
 * TDD para la entidad
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HeroControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private HeroService heroService;
	
	@Test
	public void consultar_hero_por_id_existente() throws Exception {
		Long id = Long.valueOf(1);
		HeroDTO pivot = HeroDTO.builder().nombre("pata").descripcion("un hero tipo pata").build();

		given(heroService.getHeroById(id)).willReturn(Optional.of(pivot));
		
		ResultActions actions = mockMvc.perform(get("/api/heros/{id}",id));
		
		actions.andExpect(status().isOk()).andDo(print());
	}																																																																																																																	

}
