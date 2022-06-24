package com.gildedrose.helpers;

import com.gildedrose.Item;

public class ItemUtils {
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    public static boolean isNameEquals(Item item, String name) {
        return item.name.equals(name);
    }
}
