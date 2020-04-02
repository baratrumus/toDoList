package data;

import models.Item;

import java.util.List;

public interface Db {
    boolean addNewItem(Item item);
    List<Item> getAllItems();
    List<Item> getDoneItems();
    Boolean changeItemDone(int itemId, boolean state);
}
