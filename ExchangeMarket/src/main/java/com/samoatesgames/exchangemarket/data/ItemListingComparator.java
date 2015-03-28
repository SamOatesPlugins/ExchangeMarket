package com.samoatesgames.exchangemarket.data;

import java.util.Comparator;

/**
 *
 * @author Sam
 */
public class ItemListingComparator implements Comparator<ItemListing> {
    @Override
    public int compare(ItemListing o1, ItemListing o2) {
        return o1.getPrice() < o2.getPrice() ?  -1 : 1;
    }
}