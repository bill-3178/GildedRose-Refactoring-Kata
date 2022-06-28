package com.gildedrose;

import static com.gildedrose.helpers.ItemUtils.*;

class GildedRose {
    private final Item[] items;

    public GildedRose(Item[] items) {
        checkItemPreConditions(items);
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (item.name.equals(SULFURAS)) {
                item.quality = SULFURAS_QUALITY; // FIXME or set to MAX at initialization and then never changed ?
            } else {
                if (!item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES)) {
                    if (item.quality > 0) {
                        if (item.name.equals(CONJURED)) {
                            if (item.quality == 1) {
                                item.quality = 0;
                            } else {
                                item.quality -= 2;
                            }
                        } else {
                            item.quality = item.quality - 1;
                        }
                    }
                } else {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1;

                        if (item.name.equals(BACKSTAGE_PASSES)) {
                            // limit values have been included
                            if (item.sellIn <= 6) {
                                if (item.quality <= 48) {
                                    item.quality += 2;
                                } else if (item.quality < 50) {
                                    item.quality++;
                                }
                            } else if (item.sellIn <= 11 && item.quality < 50) {
                                item.quality++;
                            }
                        }
                    }
                }

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

    private void checkItemPreConditions(Item[] items) {
        for (Item it : items) {
            if (it.name == null || it.name.length() <= 0) {
                throw new IllegalStateException("Item name cannot be null or empty");
            }
            if (it.quality < 0 || (it.quality > 50 && !it.name.equals(SULFURAS)) || it.quality > 80) {
                throw new IllegalStateException("Item quality must be a positive number between 0 and 50");
            }

            // Specific check on 'Sulfuras' fixed quality could be added depending on the requirements
        }
    }

    public Item[] getItems() {
        return items;
    }
}
