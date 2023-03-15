package com.exercise.heros.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author CRISTIAN
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HERO")
public class HeroEntity {

	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY )
	private Long id;
	
	@Column(name = "NAME", nullable = false, unique = true)
	private String nombre;

	@Column(name = "DESCRIPTION", nullable = false)
	private String descripcion;
}
