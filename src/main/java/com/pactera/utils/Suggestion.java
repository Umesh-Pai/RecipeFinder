package com.pactera.utils;

import javax.xml.bind.annotation.XmlRootElement;

import com.pactera.domain.Recipe;

@XmlRootElement
public class Suggestion {

    private String message;
    private Recipe recipe;

    public Suggestion(String message) {
        this(message, null);
    }

    public Suggestion(String message, Recipe recipe) {
        setMessage(message);
        setRecipe(recipe);
    }

    /**
	 * getter method for message
	 *
	 * @param  
	 * @return   message   the message to be displayed
	 */
    public String getMessage() {
        return message;
    }

    /**
	 * setter method for message
	 *
	 * @param  message  the message to be displayed
	 * @return 
	 */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
	 * getter method for recipe
	 *
	 * @param  
	 * @return   recipe   the recipe object
	 */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
	 * setter method for recipe
	 *
	 * @param  recipe  the recipe object
	 * @return 
	 */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
