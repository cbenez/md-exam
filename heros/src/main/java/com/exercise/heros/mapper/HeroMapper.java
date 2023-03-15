package com.exercise.heros.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.exercise.heros.model.HeroDTO;
import com.exercise.heros.persistence.entity.HeroEntity;

/**
 * 
 * @author CRISTIAN
 */

@Mapper
public interface HeroMapper {
	HeroMapper MAPPER = Mappers.getMapper( HeroMapper.class );

	@Mapping(target = "nombre", source = "nombre")
	@Mapping(target = "descripcion", source = "descripcion")
	HeroEntity toEntity(HeroDTO dto);
	
	@InheritInverseConfiguration
	HeroDTO toDTO(HeroEntity entity);
	
	default List<HeroDTO> toDTOList(List<HeroEntity> entityList){
		return entityList.stream().map(heroE -> HeroMapper.MAPPER.toDTO(heroE))
				.collect(Collectors.toList());
	}
}
