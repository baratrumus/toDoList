package controller;

import models.Item;

import java.util.List;

public interface Controller {
    boolean addItem(Item item);
    List<Item> getAllItems();
    List<Item> getDoneItems();
    Boolean changeItemDone(int itemId, boolean state);
}
