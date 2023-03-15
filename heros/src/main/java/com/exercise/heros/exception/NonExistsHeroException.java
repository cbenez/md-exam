package com.exercise.heros.exception;

/**
 * 
 * @author CRISTIAN
 */

public class NonExistsHeroException extends HeroException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 444556855767517298L;

	public NonExistsHeroException(String message) {
		super(message);
	}
}
