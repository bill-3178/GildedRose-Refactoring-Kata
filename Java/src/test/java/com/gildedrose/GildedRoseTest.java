package com.gildedrose;

import com.gildedrose.helpers.ItemUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.getItems()[0].name);
    }

    @Test
    public void itemWithNullNameShouldThrowPreConditionException() {
        Item[] items = new Item[] { new Item(null, 0, 0) };
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new GildedRose(items);
        });
    }

    @Test
    public void itemWithNegativeQualityShouldThrowPreConditionException() {
        Item[] items = new Item[] { new Item("ledger", 0, -1) };
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new GildedRose(items);
        });
    }

    @Test
    public void itemWithExceededQualityShouldThrowPreConditionException() {
        Item[] items = new Item[] { new Item("ledger", 0, 51) };
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new GildedRose(items);
        });
    }

    @Test
    public void correctlyInitializedItemShouldNotThrowPreConditionException() {
        Item[] items = new Item[] { new Item("ledger", 0, 10) };
        assertDoesNotThrow(() -> {
            new GildedRose(items);
        });
    }

    @Test
    void testUpdates() {
        Item[] items = new Item[] {
            new Item(ItemUtils.SULFURAS, 25, 40),
            new Item(ItemUtils.AGED_BRIE, 25, 2),
            new Item(ItemUtils.BACKSTAGE_PASSES, 25, 5),
            new Item("foo", 25, 50)
        };

        GildedRose app = new GildedRose(items);

        Item sulfuras = app.getItems()[0];
        Item agedBrie = app.getItems()[1];
        Item backstagePasses = app.getItems()[2];

        assertEquals(ItemUtils.SULFURAS, sulfuras.name);
        assertEquals(ItemUtils.AGED_BRIE, agedBrie.name);
        assertEquals(ItemUtils.BACKSTAGE_PASSES, backstagePasses.name);

        app.updateQuality();
        assertEquals(25, sulfuras.sellIn);
        assertEquals(24, agedBrie.sellIn);
        assertEquals(24, backstagePasses.sellIn);
        assertEquals(24, app.getItems()[3].sellIn);
        assertEquals(ItemUtils.SULFURAS_QUALITY, sulfuras.quality);
        assertEquals(3, agedBrie.quality);
        assertEquals(6, backstagePasses.quality);
        assertEquals(49, app.getItems()[3].quality);
    }

    @ParameterizedTest
    @CsvSource({ "25, 40, 1", "25, 40, 100" })
    void sulfurasCountsNeverChange(int sellIn, int quality, int loops) {
        Item sulfuras = new Item(ItemUtils.SULFURAS, sellIn, quality);

        GildedRose app = new GildedRose(new Item[] { sulfuras });

        for (int i = 0; i < loops; i++) {
            app.updateQuality();
            assertEquals(25, sulfuras.sellIn);
            assertEquals(ItemUtils.SULFURAS_QUALITY, sulfuras.quality);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testData.csv", delimiter = ';', numLinesToSkip = 1)
    void testCsvUpdates(String itemName, int sellIn, int quality, int loops, int expectedSellIn, int expectedQuality) {
        Item item = new Item(itemName, sellIn, quality);

        GildedRose app = new GildedRose(new Item[] { item });

        for (int i = 0; i < loops; i++) {
            app.updateQuality();
        }

        assertEquals(expectedSellIn, item.sellIn, "Remaining days for " + itemName + " should be " + expectedSellIn);
        assertEquals(expectedQuality, item.quality, "Quality of " + itemName + " should be " + expectedQuality);
    }

}
