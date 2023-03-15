package com.exercise.heros.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.heros.aspect.annotation.AuditTime;
import com.exercise.heros.exception.DuplicatedHeroException;
import com.exercise.heros.exception.NonExistsHeroException;
import com.exercise.heros.exception.HeroException;
import com.exercise.heros.model.HeroDTO;
import com.exercise.heros.service.HeroService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author CRISTIAN
 */

@RestController
@RequestMapping("/api/heros")
public class HeroController {
	@Autowired
	private HeroService heroService;
	
	private static Logger logger = LoggerFactory.getLogger(HeroController.class);

	@ApiOperation(value ="Retornar todas las entidades de la base")
	@GetMapping("/getAll")
	@AuditTime
	public List<HeroDTO> getAllHero() {
		logger.info("#getAllHero");
		return heroService.getAllHero();
	}

	@ApiOperation(value =  "Buscar Entidad por ID")
	@GetMapping("/{id}")
	@AuditTime
	public ResponseEntity<?> getHeroById(@PathVariable("id") Long id) {
		logger.info("#getHeroById " + id);
		Optional<HeroDTO> opt = null;
		try {
			opt = heroService.getHeroById(id);
		} catch (NonExistsHeroException e) {
			return ResponseEntity.notFound().build();
		} catch (HeroException e) {
			return ResponseEntity.internalServerError().build();
		}
		return opt
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@ApiOperation(value="Buscar Entidad por nombre")
	@GetMapping("/getByNombre/{nombre}")
	@AuditTime
	public List<HeroDTO> getHeroByNombre(@PathVariable("nombre") String nombre) {
		logger.info("#getHeroByNombre " + nombre);
		return heroService.getHeroByNombre(nombre);
	}
	
	@ApiOperation(value="Modificar Entidad")
	@PutMapping("/{id}")
	@AuditTime
	public ResponseEntity<?> updateHero(@PathVariable("id") Long id, @RequestBody HeroDTO dto){
		logger.info("#updateHero " + dto);
		Optional<HeroDTO> opt = null;
		try {
			opt = heroService.updateHero(id,dto);
		} catch (NonExistsHeroException e) {
			return ResponseEntity.notFound().build();
		} catch (DuplicatedHeroException e) {
			return ResponseEntity.unprocessableEntity().build();
		} catch (HeroException e) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(opt);
	}
	
	@ApiOperation(value="Crear Entidad")
	@PostMapping("/createHero")
	@AuditTime
	public ResponseEntity<?> createHero(@RequestBody HeroDTO dto){
		logger.info("#createHero " + dto);
		Optional<HeroDTO> opt = null;
		try {
			opt = (heroService.createHero(dto));
		} catch (DuplicatedHeroException e) {
			return ResponseEntity.unprocessableEntity().build();
		} catch (HeroException e) {
			return ResponseEntity.internalServerError().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
		return ResponseEntity.ok(opt);
	}
	
	@ApiOperation(value="Eliminar Entidad")
	@DeleteMapping("/{id}")
	@AuditTime
	public ResponseEntity<?> deleteHero(@PathVariable("id") Long id){
		try {
			heroService.deleteHero(id);
		} catch (NonExistsHeroException e) {
			return ResponseEntity.notFound().build();
		} catch (HeroException e) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok().build();
	}
	

}
