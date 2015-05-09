package com.pactera.archive;

import com.pactera.domain.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RecipesArchive {

    private static RecipesArchive archive;
    private final ConcurrentHashMap<String, Recipe> recipes = new ConcurrentHashMap<>();

    private RecipesArchive() {}

    /**
     * This method returns an instance of RecipesArchive.
     * If an instance is already created, the method will return that instance or else will create a new one.
     *
     * @param  
     * @return  archive  an instance of RecipesArchive
     */
    public static RecipesArchive instance() {
        if (archive == null) 
        	archive = new RecipesArchive();
        return archive;
    }

    /**
     * This method updates the list of Recipes. It removes all the items in the Recipes list and adds the new list of items.
     *
     * @param  recipeList  list of recipes that has to be added to the Recipes list
     * @return
     */
    public synchronized void update(List<Recipe> recipeList) {

    	/**
    	* Previous items are removed from the collection
    	*/
        removeAll();

        /**
    	* Duplicated items are overridden
    	*/
        for (Recipe recipe : recipeList)
            recipes.put(recipe.getName(), recipe);
    }

    /**
     * This method removes all the items from the Recipe list.
     *
     * @param  
     * @return
     */
    public synchronized void removeAll() {
        recipes.clear();
    }

    /**
     * This method returns all the items from the Recipe list
     *
     * @param  
     * @return  list of items in the Recipe list
     */
    public synchronized List<Recipe> get() {
        return new ArrayList<>(recipes.values());
    }

    /**
     * This method returns a boolean value specifying whether the Recipe list is empty or not
     *
     * @param  
     * @return  boolean value specifying whether the Recipe list is empty or not
     */
    public synchronized boolean isEmpty() {
        return recipes.isEmpty();
    }
}
