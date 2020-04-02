package controller;

import data.DbHibernate;
import data.Db;
import models.Item;

import java.util.List;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

public class ItemsController implements Controller {

    private static final ItemsController INSTANCE = new ItemsController();

    private ItemsController() {
    }

    public static ItemsController getInstance() {
        return INSTANCE;
    }

    private static final Db STORAGE = DbHibernate.getInstance();

    @Override
    public boolean addItem(Item item) {
        STORAGE.addNewItem(item);
        return true;
    }

    @Override
    public List<Item> getAllItems() {
        return STORAGE.getAllItems();
    }

    @Override
    public List<Item> getDoneItems() {
        return STORAGE.getDoneItems();
    }

    @Override
    public Boolean changeItemDone(int itemId, boolean state) {
        return STORAGE.changeItemDone(itemId, state);
    }
}
