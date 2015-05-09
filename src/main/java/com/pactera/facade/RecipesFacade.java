package com.pactera.facade;

import com.pactera.archive.RecipesArchive;
import com.pactera.domain.Recipe;
import com.pactera.utils.Suggestion;
import com.pactera.utils.SuggestionHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.WebApplicationException;

import java.util.List;

import static javax.ws.rs.core.Response.Status;

/**
 * This class is responsible for fetching recipe items
 * and to add new recipes
 * 
 */
@Path("/recipes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecipesFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipesFacade.class);

    /**
     * This method returns the list of recipes from the Recipe list
     *
     * @param  
     * @return  list of items from the Recipe list
     */
    @GET
    public List<Recipe> get() {
        return RecipesArchive.instance().get();
    }
    
    /**
     * This method inserts items into Recipe list and returns suggestions for what to cook based on the items in the fridge and the recipes provided
     *
     * @param  recipes  list of items to be added into the Recipe list
     * @return  suggestions for what to cook
     */
    @POST @Path("/add")
    public Suggestion add(List<Recipe> recipes) {
        LOGGER.info("Adding recipes: ", recipes);
        try {
        	if(recipes == null || recipes.size() == 0)
        		throw new IllegalArgumentException("No Recipes specified! ");
        	
            RecipesArchive.instance().update(recipes);
            
        } catch(IllegalArgumentException e) {
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build());
        }

        return SuggestionHelper.suggestRecipe();
    }

    /**
     * This method returns suggestions for what to cook based on the items in the fridge and the recipes provided
     *
     * @param
     * @return  suggestions for what to cook
     */
    @GET @Path("/suggestion")
    public Suggestion suggestion() {
        return SuggestionHelper.suggestRecipe();
    }
}
