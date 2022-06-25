package com.gildedrose;

import static com.gildedrose.helpers.ItemUtils.*;

class GildedRose {
    private final Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (!item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES)) {
                if (item.quality > 0) {
                    if (item.name.equals(CONJURED)) {
                        if (item.quality == 1) {
                            item.quality = 0;
                        } else {
                            item.quality -= 2;
                        }
                    } else if (!item.name.equals(SULFURAS)) {
                        item.quality = item.quality - 1;
                    }
                }
            } else {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;

                    if (item.name.equals(BACKSTAGE_PASSES)) {
                        if (item.sellIn < 11) {
                            if (item.quality < 50) {
                                item.quality = item.quality + 1;
                            }
                        }

                        if (item.sellIn < 6) {
                            if (item.quality < 50) {
                                item.quality = item.quality + 1;
                            }
                        }
                    }
                }
            }

            if (!item.name.equals(SULFURAS)) {
                item.sellIn = item.sellIn - 1;

                if (item.sellIn < 0) {
                    if (item.name.equals(AGED_BRIE)) {
//                    if (item.quality < 50) {
//                        // FIXME Not mentioned that quality of Brie increases twice as much once the sell by date has passed
//                        item.quality = item.quality + 1;
//                    }
                    } else if (item.name.equals(BACKSTAGE_PASSES)) {
                        item.quality = 0;
                    } else if (item.name.equals(CONJURED)) {
                        if (item.quality >= 2) {
                            item.quality -= 2;
                        } else {
                            item.quality = 0;
                        }
                    } else {
                        if (item.quality > 0) {
                            item.quality = item.quality - 1;
                        }
                    }
                }
            }

        }
    }

    public Item[] getItems() {
        return items;
    }
}
