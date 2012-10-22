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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

/**
 * Manages and searches for games.
 * All games should be annotated with the {@code Games} annotation.
 * If not they will not be selectable when creating a new alarm.
 * @author Emil Edholm, Johan Gustafsson
 * @date 14 okt 2012
 */ 
public enum GameManager {
	;
	private static Map<String, Class<?>> gameCache = null;
	
	/**
	 * Retrieves a list of all possible games. 
	 * Searches for available games defined by their annotation.
	 * @return returns a list of all types annotated with the {@code Game} annotation.
	 */
	public static Map<String, Class<?>> getAvailableGames() {
		if(gameCache != null) {
			return gameCache;
		}
		
		List<Class<?>> availableGames = getTypesAnnotatedWith(Game.class);
		gameCache = new HashMap<String, Class<?>>();
		
		for(Class<?> game : availableGames) {
			Game anno = game.getAnnotation(Game.class);
			gameCache.put(anno.name(), game);
		}
		
		return gameCache;
	}
	
	/**
	 * Return a game type from a specified game name.
	 * @param gameName the name of the game to search for.
	 * @return the type of the game searched for or null if not found
	 */
	public static Class<?> lookupGame(String gameName) {
		return getAvailableGames().get(gameName);
	}
	
	private static List<Class<?>> getTypesAnnotatedWith(Class<?> anno) {
		// TODO: Use reflection to search for classes annotated instead.
		// Couldn't get google reflections to work...
		
		List<Class<?>> type = new ArrayList<Class<?>>();
		
		type.add(CalculusGame.class);
		type.add(RocketLanderGame.class);
		type.add(WhacAMoleGame.class);
		
		return type;
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
	 * @return an instance of the game or null if something fails
	 */
	public static AbstractGameView createGame(Class<?> type, Context c) {
		AbstractGameView game = null;
		try {
			game = (AbstractGameView)type.getConstructor(Context.class).newInstance(c);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return game;
	}
	
	public static AbstractGameView createGame(String gameName, Context c) {
		return createGame(lookupGame(gameName), c);
	}
	
}
