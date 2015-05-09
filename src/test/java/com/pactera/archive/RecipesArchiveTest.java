package com.pactera.archive;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.pactera.archive.RecipesArchive;
import com.pactera.domain.Item;
import com.pactera.domain.Recipe;
import com.pactera.utils.UnitEnum;

public class RecipesArchiveTest {

	private static final List<Item> RECIPE_ONE_ITEMS = Lists.newArrayList(
			(new Item("bread" ,  8, UnitEnum.SLICES)),
			(new Item("cheese", 10, UnitEnum.SLICES)));
			
    private static final List<Recipe> RECIPE_ONE = Lists.newArrayList(
            new Recipe("cheese sand wich ", RECIPE_ONE_ITEMS ));
            		
    
    private static final List<Item> RECIPE_TWO_ITEMS = Lists.newArrayList(
			(new Item("guava", 8300, UnitEnum.GRAMS)),
			(new Item("rice", 1000, UnitEnum.ML)));
			
    private static final List<Recipe> RECIPE_TWO = Lists.newArrayList(
            new Recipe("rice and guava ", RECIPE_TWO_ITEMS ));
    
    private static final List<Item> RECIPE_THREE_ITEMS = Lists.newArrayList(
			(new Item("guava", 8300, UnitEnum.GRAMS)),
			(new Item("rice", 500, UnitEnum.ML)),
			(new Item("rice", 700, UnitEnum.ML)));
			
    private static final List<Recipe> RECIPE_THREE = Lists.newArrayList(
            new Recipe("rice and guava ", RECIPE_THREE_ITEMS ),
            new Recipe("rice and guava ", RECIPE_TWO_ITEMS )
    		);


    private RecipesArchive archive;

    @Before
    public void init() {
    	archive = RecipesArchive.instance();
    }

    /**
     * Below method tests adding of items to archive and ensures only added 
     * items are available and clear old items
     */
    @Test
    public void update_shouldRemoveItems_BeforeAddingNewOnes() {
    	archive.update(RECIPE_ONE);
        List<Recipe> recipeList = archive.get();
        assertThat(recipeList).hasSize(1).containsAll(RECIPE_ONE);
        
        List<Item> itemsInRecipe = recipeList.get(0).getIngredients();
        assertThat(itemsInRecipe).hasSize(2).containsAll(RECIPE_ONE_ITEMS);
        
        archive.update(RECIPE_TWO);
        List<Recipe> recipeListTwo = archive.get();
        assertThat(recipeListTwo).hasSize(1).containsAll(RECIPE_TWO);
        
        List<Item> itemsInRecipeTwo = recipeListTwo.get(0).getIngredients();
        assertThat(itemsInRecipeTwo).hasSize(2).containsAll(RECIPE_TWO_ITEMS);
        
    }

    
    /**
     * To test duplicate items insertion and only latest items
     * are available 
     */
    @Test
    public void update_shouldOverrideDuplicatedItems() {
    	archive.update(RECIPE_THREE);
        List<Recipe> items = archive.get();
        assertThat(items).hasSize(1).contains(RECIPE_THREE.get(1));
        
        List<Item> itemsInRecipe = items.get(0).getIngredients();
        assertThat(itemsInRecipe).hasSize(2).contains(RECIPE_TWO_ITEMS.get(1));
        
    }

    
    /**
     * To test remove functionality of archive
     */
    @Test
    public void removeAll_shouldClearTheArchive() {
    	archive.update(RECIPE_THREE);
        assertThat(archive.isEmpty()).isFalse();
        assertThat(archive.get()).hasSize(1);

        archive.removeAll();
        assertThat(archive.isEmpty()).isTrue();
        assertThat(archive.get()).isEmpty();
    }
}
