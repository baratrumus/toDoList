package servlets;

import controller.Controller;
import controller.ItemsController;
import models.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

public class ChangeStateServlet  extends HttpServlet {

    private final Controller storage = ItemsController.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(ItemServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int itemId = Integer.parseInt(req.getParameter("itemId"));
        String df = req.getParameter("chState");
        Boolean state = !(Boolean.valueOf(req.getParameter("chState")));
        boolean change = storage.changeItemDone(itemId, state);
        LOG.info("ITEM " + itemId + " changed to " + state);
    }

}
