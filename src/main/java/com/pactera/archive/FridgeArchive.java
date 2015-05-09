package com.pactera.archive;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.pactera.domain.FridgeItem;

public final class FridgeArchive {

    private static FridgeArchive archive;
    private final ConcurrentHashMap<String, FridgeItem> fridgeItems = new ConcurrentHashMap<>();

    private FridgeArchive() {}

    /**
     * This method returns an instance of FridgeArchive.
     * If an instance is already created, the method will return that instance or else will create a new one.
     *
     * @param  
     * @return  archive  an instance of FridgeArchive
     */
    public static FridgeArchive instance() {
        if (archive == null) archive = new FridgeArchive();
        return archive;
    }
    
    /**
     * This method updates the items in the Fridge. It removes all the items in the fridge and adds the new list of items.
     *
     * @param  items  list of items that has to be added to the fridge
     * @return
     */
    public synchronized void update(List<FridgeItem> items) {
    	
    	/**
    	* Previous items are removed from the collection
    	*/
        removeAll();

        /**
    	* Duplicated items are overridden
    	*/
        for (FridgeItem item : items)
            fridgeItems.put(item.getItem(), item);
        
    }

    /**
     * This method removes all the items from the Fridge.
     *
     * @param  
     * @return
     */
    public synchronized void removeAll() {
        fridgeItems.clear();
    }

    /**
     * This method returns all the items in the Fridge
     *
     * @param  
     * @return  list of items in the Fridge
     */
    public synchronized List<FridgeItem> get() {
        return new ArrayList<>(fridgeItems.values());
    }

    /**
     * This method returns an item in the fridge pertaining to the item name provided
     *
     * @param  name  name of the item
     * @return  item in the fridge pertaining to the item name provided
     */
    public synchronized FridgeItem getItem(String name) {
        return fridgeItems.get(name);
    }

    /**
     * This method returns a boolean value specifying whether the Fridge is empty or not
     *
     * @param  
     * @return  boolean value specifying whether the Fridge is empty or not
     */
    public synchronized boolean isEmpty() {
        return fridgeItems.isEmpty();
    }
}
