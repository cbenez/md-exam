package com.exercise.heros.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CRISTIAN
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HeroDTO {
	private String nombre;
	
	private String descripcion;

}
