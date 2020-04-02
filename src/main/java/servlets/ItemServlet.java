package servlets;

import com.google.gson.Gson;
import controller.ItemsController;
import controller.Controller;
import models.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

public class ItemServlet extends HttpServlet {

    private final Controller storage = ItemsController.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(ItemServlet.class);

    /**
     * list items
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       //первый вход на страницу, заход в get идет не через ajax и first = null
       //так что делаем редирект, чтобы логика ajax отработалась
        String onlyDone = req.getParameter("onlyDone");
        if (onlyDone == null) {
            resp.sendRedirect(String.format("%s/index.html", req.getContextPath()));
        } else {
            List<Item> listOfItems = new ArrayList<>();
            if (onlyDone.equals("n")) {
                listOfItems = storage.getAllItems();
            } else if (onlyDone.equals("y")) {
                listOfItems = storage.getDoneItems();
            }
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter pr = resp.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(listOfItems);
            pr.write(jsonInString);
            pr.flush();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String descr = req.getParameter("description");
        //Calendar cal = Calendar.getInstance();
        //cal.setTimeInMillis(System.currentTimeMillis());
        //LocalDateTime dt =  millsToLocalDateTime(System.currentTimeMillis());
        Timestamp dt = new Timestamp(System.currentTimeMillis());
        Item item = new Item(0, descr, dt, false);
        boolean add = storage.addItem(item);
        LOG.info("item addition is " + add);
    }

    public static LocalDateTime millsToLocalDateTime(long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDateTime date = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return date;
    }
}