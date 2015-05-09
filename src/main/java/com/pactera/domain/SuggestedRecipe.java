package com.pactera.domain;

import java.util.TreeSet;

public class SuggestedRecipe {
	final Recipe recipe;
	final TreeSet<Integer> closestDays = new TreeSet<>();

	public SuggestedRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	/**
	 * getter method for Recipe
	 *
	 * @param  
	 * @return  recipe  the recipe
	 */
	public Recipe getRecipe(){
		return recipe;
	}
	
	/**
	 * This method returns a set of closest days to todays date
	 *
	 * @param  
	 * @return  closestDays  the closestDays to todays date
	 */
	public TreeSet<Integer> getClosestDays(){
		return closestDays;
	}
	
	/**
	 * toString method
	 *
	 */
	@Override
	public String toString() {
		return "RecipeFound{" + "recipe=" + recipe + ", closestDays="
				+ closestDays + "}";
	}
}