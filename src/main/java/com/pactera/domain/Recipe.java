package com.pactera.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Recipe {

	private String name;
	private List<Item> ingredients;

	public Recipe() {
	}

	public Recipe(String name, List<Item> items) {
		setName(name);
		setIngredients(items);
	}

	/**
	 * getter method for name
	 *
	 * @param  
	 * @return  name  the name of the Recipe
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter method for name
	 *
	 * @param  name  the name of the Recipe
	 * @return 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter method for ingredients
	 *
	 * @param  
	 * @return  ingredients  list of ingredients for the Recipe
	 */
	public List<Item> getIngredients() {
		return ingredients;
	}

	/**
	 * setter method for ingredients
	 *
	 * @param  ingredients  list of ingredients for the Recipe
	 * @return 
	 */
	public void setIngredients(List<Item> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * toString method
	 *
	 */
	@Override
	public String toString() {
		return "Recipe {" + "name='" + name + '\'' + ", ingredients="
				+ ingredients + "}";
	}
}
