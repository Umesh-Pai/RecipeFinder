package com.pactera.utils;

import com.pactera.archive.FridgeArchive;
import com.pactera.archive.RecipesArchive;
import com.pactera.domain.FridgeItem;
import com.pactera.domain.Item;
import com.pactera.domain.Recipe;
import com.pactera.domain.SuggestedRecipe;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SuggestionHelper {

    private SuggestionHelper() {}

    /**
     * This method checks if the FridgeArchive or the RecipesArchive is empty. If not suggestions for what to cook is returned
     *
     * @param  
     * @return  suggestions for what to cook
     */
    public static Suggestion suggestRecipe() {
        return FridgeArchive.instance().isEmpty() ? fridgeIsEmpty()
                : RecipesArchive.instance().isEmpty() ? takeout()
                : findRecipe();
    }

    /**
     * This method returns a Suggestion object with message 'Fridge is empty'
     *
     * @param  
     * @return Suggestion object with message 'Fridge is empty'
     */
    private static Suggestion fridgeIsEmpty() {
        return new Suggestion("Fridge is empty");
    }

    /**
     * This method returns a Suggestion object with message 'Order Takeout'
     *
     * @param  
     * @return Suggestion object with message 'Order Takeout'
     */
    private static Suggestion takeout() {
        return new Suggestion("Order Takeout");	
    }

    /**
     * This method returns a Suggestion object with message 'Suggestion for dinner' and the suggested recipe
     *
     * @param  
     * @return Suggestion object with message 'Suggestion for dinner' and the suggested recipe
     */
    private static Suggestion suggestedRecipe(Recipe recipe) {
        return new Suggestion("Suggestion for dinner", recipe); 	 	
    }

    /**
     * This method returns a Suggestion object with the suggested recipe
     *
     * @param  
     * @return Suggestion object with the suggested recipe
     */
    private static Suggestion findRecipe() {
        List<SuggestedRecipe> recipesList = new ArrayList<>();
        for (Recipe recipe : RecipesArchive.instance().get()) 
        {
            boolean foundRecipe = true;
            SuggestedRecipe recipeItem = new SuggestedRecipe(recipe);

            for (Item ingredient : recipe.getIngredients()) 
            {
                FridgeItem fridgeItem = FridgeArchive.instance().getItem(ingredient.getItem());
                if (!isValid(fridgeItem, ingredient, recipeItem)) 
                {
                    foundRecipe = false;
                    break;
                }
            }

            if (foundRecipe)
                recipesList.add(recipeItem);
        }

        return recipesList.isEmpty() ? takeout()
                : recipesList.size() == 1 ? suggestedRecipe(recipesList.get(0).getRecipe())
                : findBestMatch(recipesList);
    }

    /**
     * This method checks if the Fridge item, ingredient and recipe item provided are valid
     *
     * @param  item  the fridge item
     * @param  ingredient  ingredient of the recipe
     * @param  recipeItem  recipe Item to be validated
     * @return  boolean value specifying whether the values are valid
     */
    private static boolean isValid(FridgeItem item, Item ingredient, SuggestedRecipe recipeItem) {
        if (item == null
                || !item.getUnit().equals(ingredient.getUnit())
                || item.getAmount() < ingredient.getAmount())
            return false;

        LocalDate today = new LocalDate();
        LocalDate useBy = new LocalDate(item.getUseBy());
        if (useBy.isBefore(today))
            return false;

        recipeItem.getClosestDays().add(Days.daysBetween(today, useBy).getDays());
        return true;
    }

    /**
     * This method returns the most matching recipe as a suggestion from a list of recipe item provided
     *
     * @param  recipeItemList  the list of recipe items that has to be checked
     * @return  Suggestion object which specifies the best matching recipe
     */
    private static Suggestion findBestMatch(List<SuggestedRecipe> recipeItemList) {
        Iterator<SuggestedRecipe> it = recipeItemList.iterator();
        return suggestedRecipe(findBestMatch(it.next(), it).getRecipe());
    }

    /**
     * This method returns the most matching recipe as a suggestion from a list of recipe packs provided
     *
     * @param  current  current recipe pack
     * @param  it  Iterator to iterate over the list of recipe pack
     * @return  SuggestedRecipe object which specifies the best matching recipe
     */
    private static SuggestedRecipe findBestMatch(SuggestedRecipe current, Iterator<SuggestedRecipe> it) {
        if (!it.hasNext())
            return current;

        SuggestedRecipe next = it.next();

        Iterator<Integer> currentIt = current.getClosestDays().iterator();
        Iterator<Integer> nextIt = next.getClosestDays().iterator();

        while (currentIt.hasNext()) {
            if (!nextIt.hasNext())
                return findBestMatch(next, it);

            int result = currentIt.next().compareTo(nextIt.next());
            if (result == -1)
                return findBestMatch(current, it);
            if (result == 1)
                return findBestMatch(next, it);
        }

        return findBestMatch(current, it);
    }
}
