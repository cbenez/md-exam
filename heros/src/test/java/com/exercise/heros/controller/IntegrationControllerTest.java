package com.exercise.heros.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.exercise.heros.HerosApplication;
import com.exercise.heros.model.HeroDTO;
import com.exercise.heros.persistence.entity.HeroEntity;
import com.exercise.heros.persistence.repository.HeroRepository;

/**
 * 
 * @author CRISTIAN TDD para la entidad
 */
//@SpringBootTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = HerosApplication.class)

@AutoConfigureMockMvc
public class IntegrationControllerTest {

	private static String PREFIX_SITE = "/api/heros/";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private HeroRepository heroRepository;

	// test_consulta
	@Test
	public void test_consultar_hero_por_nombre_ok() throws Exception {
		String match = "uno";
		mockMvc.perform(get(PREFIX_SITE + "getByNombre/" + match).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void test_create_hero() throws IOException, Exception {
		HeroDTO dto = HeroDTO.builder().nombre("bob").descripcion("desc de bob").build();
		mockMvc.perform(post(PREFIX_SITE + "createHero").contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(dto))).andDo(print()).andExpect(status().isOk());
		List<HeroEntity> found = heroRepository.findAll();
		assertThat(found).extracting(HeroEntity::getNombre).contains("bob");
	}

	@Test
	public void test_baja_hero_ok() throws Exception {
		// actualente estan los (N) elementos
		Integer sizePreviuos = heroRepository.findAll().size();

		Long id = firstId();
		mockMvc.perform(delete(PREFIX_SITE + id, id)).andDo(print()).andExpect(status().isOk());

		// se borra (1) por tanto quedan (N-1) elementos
		List<HeroEntity> found = heroRepository.findAll();
		assertThat(found).hasSize(sizePreviuos - 1);
	}

	@Test
	public void test_baja_hero_error() throws Exception {
		// actualente estan los (N) elementos
		Integer sizePreviuos = heroRepository.findAll().size();

		Long id = Long.valueOf(100);
		mockMvc.perform(delete(PREFIX_SITE + id, id)).andDo(print()).andExpect(status().isNotFound());

		// se borra (0) por tanto quedan (N) elementos
		List<HeroEntity> found = heroRepository.findAll();
		assertThat(found).hasSize(sizePreviuos);
	}

	@Test
	public void test_modificar_hero() throws Exception {
		Long id = firstId();
		HeroDTO dto = HeroDTO.builder().nombre("cambio").descripcion("dos").build();

		mockMvc.perform(put(PREFIX_SITE + id, id).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(dto)))
				.andDo(print()).andExpect(status().isOk());

		// compare
		HeroEntity match = heroRepository.findById(id).get();
		assertEquals(match.getNombre(), dto.getNombre());

	}

	@BeforeEach
	public void populateDTOList() {
		HeroEntity p1 = HeroEntity.builder().nombre("uno").descripcion("desc de uno").build();
		HeroEntity p2 = HeroEntity.builder().nombre("dos").descripcion("desc de dos").build();
		heroRepository.save(p1);
		heroRepository.save(p2);
	}

	private Long firstId() {
		return heroRepository.findAll().get(0).getId();
	}

	@AfterEach
	public void clearDataBase() {
		heroRepository.deleteAll();
	}
}
