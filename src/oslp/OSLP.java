package oslp;

/**
 *
 * @author Artpicks
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.*;
public class OSLP {
    //Address
    public static final String DROP_TABLE_ADDRESS = "http://rs.d3x.me:8000/droptable";
    public static Map<String, Item> itemCollection;
    public static void main(String[] args) {
        LoadingWindow loading = new LoadingWindow();
        loading.setVisible(true);
        JSONParser parser = new JSONParser();
        itemCollection = parser.getItemMap();
        loading.setVisible(false);
        StartMenu menu = new StartMenu();
        menu.setVisible(true);
        loading.dispose();
    }
}
