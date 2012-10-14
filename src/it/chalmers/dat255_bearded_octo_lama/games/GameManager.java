/**
 * Copyright (C) 2012 Emil Edholm, Emil Johansson, Johan Andersson, Johan Gustafsson
 *
 * This file is part of dat255-bearded-octo-lama
 *
 *  dat255-bearded-octo-lama is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  dat255-bearded-octo-lama is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with dat255-bearded-octo-lama.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package it.chalmers.dat255_bearded_octo_lama.games;

import it.chalmers.dat255_bearded_octo_lama.games.anno.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import android.content.Context;

/**
 * Manages and searches for games.
 * All games should be annotated with the {@code Games} annotation.
 * If not they will not be selectable when creating a new alarm.
 * @author Emil Edholm, Johan Gustafsson
 * @date 14 okt 2012
 */ 
public final class GameManager {
	
	/**
	 * Retrieves a list of all possible games. 
	 * Searches for available games defined by their annotation.
	 * @return returns a list of all types annotated with the {@code Game} annotation.
	 */
	public static Map<String, Class<?>> getAvailableGames() {
		Reflections reflections = new Reflections("it.chalmers.dat255_bearded_octo_lama.games");
		
		Set<Class<?>> availableGames = reflections.getTypesAnnotatedWith(Game.class);
		Map<String, Class<?>> games = new HashMap<String, Class<?>>();
		
		for(Class<?> game : availableGames) {
			Game anno = game.getAnnotation(Game.class);
			games.put(anno.name(), game);
		}
		
		return games;
	}
	
	/**
	 * Retrieves the names of all available games.
	 * @return a string array with the name of all available games.
	 */
	public static String[] getAvailableGamesStrings() {
		Map<String, Class<?>> games = getAvailableGames();
		
		return games.keySet().toArray(new String[games.size()]);
	}
	
	/**
	 * Creates an instance of the supplied game type with the specified layout and context.
	 * @param type - the game type to instantiate.
	 * @param c - the context
	 * @return an instance of the game
	 */
	public static AbstractGameView createGame(Class<? extends AbstractGameView> type, Context c) {
		AbstractGameView game = createInstance(type);
		// Forward parameters to the game instance.
		return game;
	}
	
	
	/**
	 * Creates a instance of the specified class 
	 * @param class the class/type to create a instance of.
	 * @return A instance of type {@code T} or null error occurred
	 */
	private static <T> T createInstance(Class<T> classToInstantiate) {
		T tmpInstance = null;
		try {
			tmpInstance = classToInstantiate.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch(IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		return tmpInstance;
	}
}
