package com.pactera.utils;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import com.pactera.archive.FridgeArchive;
import com.pactera.archive.RecipesArchive;
import com.pactera.domain.FridgeItem;
import com.pactera.domain.Item;
import com.pactera.domain.Recipe;

public class SuggestionHelperTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("d/M/yyyy");

    private static final List<FridgeItem> FRIDGE_ITEMS = newArrayList(
            new FridgeItem("bread", 8, UnitEnum.SLICES, FORMATTER.parseLocalDate("12/3/2025")),
            new FridgeItem("banana", 12, UnitEnum.OF, new LocalDate()),
            new FridgeItem("orange", 250, UnitEnum.GRAMS, new LocalDate().plusDays(1)),
            new FridgeItem("kiwi", 500, UnitEnum.GRAMS, new LocalDate().plusDays(2)),
            new FridgeItem("avocado", 50, UnitEnum.OF, new LocalDate().minusDays(1)),
            new FridgeItem("cheese", 10, UnitEnum.SLICES, FORMATTER.parseLocalDate("3/12/2024")));

    private static final List<Item> ITEMS_EXCEEDING_AMOUNT_IN_THE_FRIDGE = newArrayList(
            new Item("bread", 8, UnitEnum.SLICES),
            new Item("cheese", 112, UnitEnum.SLICES));
            //
    private static final Recipe RECIPE_EXCEEDING_AMOUNT_IN_THE_FRIDGE =
            new Recipe("Recipe One", ITEMS_EXCEEDING_AMOUNT_IN_THE_FRIDGE);

    private static final List<Item> ITEMS_PAST_USE_BY_DATE = newArrayList(
            new Item("avocado", 8, UnitEnum.OF),
            new Item("cheese", 3, UnitEnum.SLICES));
            //
    private static final Recipe RECIPE_PAST_USE_BY_DATE =
            new Recipe("Recipe Two", ITEMS_PAST_USE_BY_DATE);

    private static final List<Item> ITEMS_UNITS_DO_NOT_MATCH = newArrayList(
            new Item("kiwi", 8, UnitEnum.OF),
            new Item("orange", 8, UnitEnum.GRAMS));
            //
    private static final Recipe RECIPE_UNITS_DO_NOT_MATCH =
            new Recipe("Recipe Three", ITEMS_UNITS_DO_NOT_MATCH);

    private static final List<Item> ITEMS_OK_ONE = newArrayList(
            new Item("kiwi", 8, UnitEnum.GRAMS),
            new Item("cheese", 2, UnitEnum.SLICES));
            //
    private static final Recipe RECIPE_OK_ONE =
            new Recipe("Recipe Four", ITEMS_OK_ONE);

    private static final List<Item> ITEMS_OK_TWO = newArrayList(
            new Item("kiwi", 8, UnitEnum.GRAMS),
            new Item("orange", 8, UnitEnum.GRAMS),
            new Item("cheese", 2, UnitEnum.SLICES));
            //
    private static final Recipe RECIPE_OK_TWO =
            new Recipe("Recipe Five", ITEMS_OK_TWO);

    private static final List<Item> ITEMS_OK_THREE = newArrayList(
            new Item("orange", 8, UnitEnum.GRAMS),
            new Item("cheese", 2, UnitEnum.SLICES));
            //
    private static final Recipe RECIPE_OK_THREE =
            new Recipe("Recipe Six", ITEMS_OK_THREE);

    @Before
    public void init() {
        FridgeArchive.instance().removeAll();
        RecipesArchive.instance().removeAll();
    }

    @Test
    public void getRecipe_shouldReturnTheBestMatch_whenIngredientsAreInTheFridge() {
        FridgeArchive.instance().update(FRIDGE_ITEMS);

        RecipesArchive.instance().update(newArrayList(RECIPE_OK_ONE, RECIPE_OK_TWO));
        Suggestion response = SuggestionHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("suggestion");
        assertThat(response.getRecipe()).isEqualTo(RECIPE_OK_TWO);

        RecipesArchive.instance().update(newArrayList(RECIPE_OK_TWO, RECIPE_OK_THREE));
        response = SuggestionHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("suggestion");
        assertThat(response.getRecipe()).isEqualTo(RECIPE_OK_TWO);

        RecipesArchive.instance().update(newArrayList(RECIPE_OK_ONE, RECIPE_UNITS_DO_NOT_MATCH));
        response = SuggestionHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("suggestion");
        assertThat(response.getRecipe()).isEqualTo(RECIPE_OK_ONE);
    }

    @Test
    public void getRecipe_shouldReturnTakeout_whenItemInTheFridgePastUseByDate() {
        FridgeArchive.instance().update(FRIDGE_ITEMS);

        RecipesArchive.instance().update(newArrayList(RECIPE_PAST_USE_BY_DATE));
        Suggestion response = SuggestionHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("takeout");
        assertThat(response.getRecipe()).isNull();
    }

    @Test
    public void getRecipe_shouldReturnTakeout_whenThereAreNotEnoughIngredientsInTheFridge() {
        FridgeArchive.instance().update(FRIDGE_ITEMS);

        RecipesArchive.instance().update(newArrayList(RECIPE_EXCEEDING_AMOUNT_IN_THE_FRIDGE));
        Suggestion response = SuggestionHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("takeout");
        assertThat(response.getRecipe()).isNull();
    }

    @Test
    public void getRecipe_shouldReturnFridgeEmptyMessage_whenThereAreNoItemsOnTheFridge() {
        assertThat(FridgeArchive.instance().isEmpty()).isTrue();

        Suggestion response = SuggestionHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("fridge")
                .containsIgnoringCase("empty");
        assertThat(response.getRecipe()).isNull();
    }

    @Test
    public void getRecipe_shouldReturnTakeOutMessage_whenFridgeHasItemsButThereAreNoRecipes() {
        FridgeArchive.instance().update(FRIDGE_ITEMS);
        assertThat(FridgeArchive.instance().isEmpty()).isFalse();
        assertThat(RecipesArchive.instance().isEmpty()).isTrue();

        Suggestion response = SuggestionHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("takeout");
        assertThat(response.getRecipe()).isNull();
    }
}
