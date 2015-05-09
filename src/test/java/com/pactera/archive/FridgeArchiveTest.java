package com.pactera.archive;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import com.google.common.collect.Lists;

import com.pactera.archive.FridgeArchive;
import com.pactera.domain.FridgeItem;
import com.pactera.utils.UnitEnum;

public class FridgeArchiveTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("d/M/yyyy");

    private static final List<FridgeItem> ITEMS_ONE = Lists.newArrayList(
            new FridgeItem("bread", 8, UnitEnum.SLICES, FORMATTER.parseLocalDate("25/03/2014")),
            new FridgeItem("cheese", 10, UnitEnum.SLICES, FORMATTER.parseLocalDate("13/12/2014")));

    private static final List<FridgeItem> ITEMS_TWO = Lists.newArrayList(
            new FridgeItem("guava", 8300, UnitEnum.GRAMS, FORMATTER.parseLocalDate("25/03/2014")),
            new FridgeItem("rice", 500, UnitEnum.ML, FORMATTER.parseLocalDate("25/12/2015")));

    private static final List<FridgeItem> ITEMS_THREE = Lists.newArrayList(
            new FridgeItem("guava", 8300, UnitEnum.GRAMS, FORMATTER.parseLocalDate("25/03/2014")),
            new FridgeItem("rice", 500, UnitEnum.ML, FORMATTER.parseLocalDate("25/12/2015")),
            new FridgeItem("rice", 700, UnitEnum.ML, FORMATTER.parseLocalDate("22/12/2015")));

    private FridgeArchive archive;

    @Before
    public void init() {
    	archive = FridgeArchive.instance();
    }

    /**
     * Below method tests adding of items to archive and ensures only added 
     * items are available and clears old items
     */
    @Test
    public void update_shouldRemoveItems_BeforeAddingNewOnes() {
    	archive.update(ITEMS_ONE);
        List<FridgeItem> itemsOne = archive.get();
        assertThat(itemsOne).hasSize(2).containsAll(ITEMS_ONE);

        archive.update(ITEMS_TWO);
        List<FridgeItem> itemsTwo = archive.get();
        assertThat(itemsTwo).hasSize(2).containsAll(ITEMS_TWO);
    }

    /**
     * To test duplicate items insertion
     */
    @Test
    public void update_shouldOverrideDuplicatedItems() {
    	archive.update(ITEMS_THREE);
        List<FridgeItem> items = archive.get();
        assertThat(items).hasSize(2).contains(ITEMS_THREE.get(0)).contains(ITEMS_THREE.get(2));
    }

    /**
     * To test remove functionality of archive
     */
    @Test
    public void removeAll_shouldClearTheArchive() {
    	archive.update(ITEMS_ONE);
        assertThat(archive.isEmpty()).isFalse();
        assertThat(archive.get()).hasSize(2);

        archive.removeAll();
        assertThat(archive.isEmpty()).isTrue();
        assertThat(archive.get()).isEmpty();
    }
}
