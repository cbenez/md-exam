package com.exercise.heros.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import com.exercise.heros.exception.DuplicatedHeroException;
import com.exercise.heros.exception.NonExistsHeroException;
import com.exercise.heros.exception.HeroException;
import com.exercise.heros.mapper.HeroMapper;
import com.exercise.heros.model.HeroDTO;
import com.exercise.heros.persistence.entity.HeroEntity;
import com.exercise.heros.persistence.repository.HeroRepository;

/**
 * 
 * @author CRISTIAN
 */

@Service
public class HeroService {

	@Autowired
	private HeroRepository heroRepository;
	
	private static Logger logger = LoggerFactory.getLogger(HeroService.class);

	@Caching(evict = { @CacheEvict(value = "HEROS_BY_ID", allEntries = true) })
	public Optional<HeroDTO> getHeroById(Long id) throws HeroException {
		Optional<HeroEntity> resEnt = null;
		try {
			if ((resEnt = heroRepository.findById(id)).isEmpty()) {
				logger.error("Hero {" + id + "} No existe");
				throw new NonExistsHeroException("Hero {" + id + "} No existe");
			}
			
		} catch (Exception e) {
			logger.error("Error al buscar Hero {" + id + "}");
			throw new HeroException("Error al buscar Hero {" + id + "}");
		}
		return Optional.of(HeroMapper.MAPPER.toDTO(resEnt.get()));
	}

	@Cacheable(cacheNames = "HEROS_ALL")
	public List<HeroDTO> getAllHero() {
		List<HeroEntity> entityList = heroRepository.findAll();
		return HeroMapper.MAPPER.toDTOList(entityList);
	}

	@Caching(evict = { @CacheEvict(value = "HEROS_BY_NOMBRE", allEntries = true) })
	public List<HeroDTO> getHeroByNombre(String nombre) {
		List<HeroEntity> entityList = heroRepository.findByNombreContainingIgnoreCase(nombre);
		return HeroMapper.MAPPER.toDTOList(entityList);
	}

	@Transactional
	public Optional<HeroDTO> updateHero(Long id, HeroDTO dto) throws HeroException {
		Optional<HeroEntity> resEnt = null;
		if ((resEnt = heroRepository.findById(id)).isEmpty()) {
			logger.error("Entidad {" + id + "} No existe");
			throw new NonExistsHeroException("Entidad {" + id + "} No existe");
		}
		
		resEnt.get().setNombre(dto.getNombre());
		resEnt.get().setDescripcion(dto.getDescripcion());
		try {
			heroRepository.save(resEnt.get());
		} catch (TransactionException e) {
			logger.error("Entidad {" + dto + "} duplicada");
			throw new DuplicatedHeroException("Entidad {" + dto + "} duplicada");
		}

		return Optional.of(HeroMapper.MAPPER.toDTO(resEnt.get()));
	}

	@Transactional
	public Optional<HeroDTO> deleteHero(Long id) throws HeroException {
		Optional<HeroEntity> resEnt = null;
		if ((resEnt = heroRepository.findById(id)).isEmpty()) {
			logger.error("Entidad {" + id + "} No existe");
			throw new NonExistsHeroException("Entidad {" + id + "} No existe");
		}
		heroRepository.delete(resEnt.get());
		return Optional.of(HeroMapper.MAPPER.toDTO(resEnt.get()));
	}

	@Transactional
	public Optional<HeroDTO> createHero(HeroDTO dto) throws HeroException {
		
		HeroEntity entitySaved = null;
		try {
			HeroEntity entity = HeroMapper.MAPPER.toEntity(dto);
			entitySaved = heroRepository.save(entity);
		} catch (TransactionException e) {
			logger.error("Entidad {" + dto + "} duplicada");
			throw new DuplicatedHeroException("Entidad {" + dto + "} duplicada");
			
		} catch (Exception e) {
			logger.error("Error al crear Entidad {" + dto + "}");
			throw new HeroException("Error al crear Entidad {" + dto + "}");

		}
		
		return Optional.of(HeroMapper.MAPPER.toDTO(entitySaved));
	}

}
