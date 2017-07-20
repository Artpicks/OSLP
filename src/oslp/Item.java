/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oslp;

/** This class creates the Item type
 * Each item has:
 * A unique name (String item_name)
 * A path to an image icon (String icon_path)
 * A unique id (int item_id)
 * A current price (int price)
 * 
 * @author Artpicks
 */

public class Item {
    private final String item_name;
    private final String icon_path;
    private final int item_id;
    private int price = 0;
    public Item(String name_in, int id_in, int in_price){
        item_name = name_in;
        String clean_name = name_in.replace("/", "-");
        clean_name = clean_name.replace(".", "");
        icon_path = "/oslp/images/"+clean_name+".png";
        item_id = id_in;
        price = in_price;
    }
    public String getName(){
        return item_name;
    }
    public int getID(){
        return item_id;
    }
    public int getPrice(){
        return price;
    }
    public String getIconPath(){
        return icon_path;
    }
    
}
