package com.pactera.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.pactera.archive.RecipesArchive;
import com.pactera.domain.Item;
import com.pactera.domain.Recipe;
import com.pactera.facade.RecipesFacade;
import com.pactera.utils.UnitEnum;

public class RecipesFacadeTest {

	private RecipesFacade facade;

    @Before
    public void init()
    {
    	facade = new RecipesFacade();
        RecipesArchive.instance().removeAll();
    }

    @Test(expected = WebApplicationException.class)
    public void addRecipes_shouldThrowAnException_WhenListIsEmpty() throws IOException {
    	facade.add(new ArrayList<Recipe>());
        fail("Adding recipes should have failed!");
    }

    @Test(expected = WebApplicationException.class)
    public void addRecipes_shouldThrowAnException_WhenListIsNull() throws IOException {
    	facade.add(null);
        fail("Adding items should have failed!");
    }

    @Test
    public void addRecipe_shouldPopulateTheArchive() {
    	
    	List<Item> RECIPE_ITEMS = Lists.newArrayList(
    			(new Item("bread" ,  8, UnitEnum.SLICES)),
    			(new Item("cheese", 10, UnitEnum.SLICES)));
    			
        List<Recipe> RECIPE = Lists.newArrayList(
                new Recipe("cheese sand wich ", RECIPE_ITEMS ));
        
        facade.add(RECIPE);
        assertThat(RecipesArchive.instance().get()).hasSize(1);
    }

}
