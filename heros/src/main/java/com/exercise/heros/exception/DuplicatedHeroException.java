package com.exercise.heros.exception;

/**
 * 
 * @author CRISTIAN
 */

public class DuplicatedHeroException extends HeroException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2804810430263451141L;

	public DuplicatedHeroException(String message) {
		super(message);
	}
}
