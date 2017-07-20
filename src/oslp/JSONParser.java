/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oslp;

/** This class parses the OSBuddy JSON API 
 * More details by each method
 *
 * @author Artpicks
 */

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;


public class JSONParser {
    private JSONObject json;
    
    //Constructor reads from the RSBuddy API and stores the data in a JSONObject
    public JSONParser(){
        try{
            json = readJsonFromUrl("https://rsbuddy.com/exchange/summary.json");
        }
        catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    //Method for URL I/O
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    //Method for URL I/O
    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
    }
    
    //Returns the price (int) for any given item ID from the JSONObject directly
    //Old unused code, still might be useful in future projects
    public int getPriceData(int item_id){
        try{
            //System.out.println("Getting price data of "+item_id);
            JSONObject pricejson = json.getJSONObject(Integer.toString(item_id));
            //System.out.println("Data read for "+item_id);
            return pricejson.getInt("overall_average");
        }
        catch(JSONException e){
            System.out.println("Error: JSONException -- URL Read does not have valid JSON formatting, check Internet connection");
            return 0;
        }
    }
    
    //Returns a Map of all tradeable Items with their current prices
    //Untradeables were added manually near the end of this method
    //If a new untradeable comes out, the Image must be downloaded manually and the Item added as specifed below with price = 0 and id = -1
    public Map<String, Item> getItemMap(){
        JSONArray names = json.toJSONArray(json.names());
        Map<String, Item> itemMap = new TreeMap();
        for(int i = 0; i < names.length(); i++){
            try{
                JSONObject tempjson = names.getJSONObject(i);
                int id = tempjson.getInt("id");
                String name = tempjson.getString("name");
                int price = tempjson.getInt("sell_average");
                Item tempitem = new Item(name, id, price);
                itemMap.put(name, tempitem);
            }
            catch(JSONException e){
                System.out.println("JSON Error");
                System.exit(-1);
            }
        }
        //Coins inputting (they are weird)
        int id = -2;
        int price = 1;
        String name = "Coins";
        itemMap.put(name, new Item(name, id, price));
        id = -1;
        price = 0;
        //Untradeables go here
        // change String name to item name, then use "itemMap.put(name, new Item(name, id, price));
        //<editor-fold>
        name = "Clue scroll (elite)";
        itemMap.put(name, new Item(name, id, price));
        name = "Clue scroll (hard)";
        itemMap.put(name, new Item(name, id, price));
        name = "Clue scroll (medium)";
        itemMap.put(name, new Item(name, id, price));
        name = "Clue scroll (easy)";
        itemMap.put(name, new Item(name, id, price));
        name = "Clue scroll (master)";
        itemMap.put(name, new Item(name, id, price));
        name = "Spirit seed";
        itemMap.put(name, new Item(name, id, price));
        name = "Pet snakeling";
        itemMap.put(name, new Item(name, id, price));
        name = "Tanzanite mutagen";
        itemMap.put(name, new Item(name, id, price));
        name = "Magma mutagen";
        itemMap.put(name, new Item(name, id, price));
        name = "Pet kraken";
        itemMap.put(name, new Item(name, id, price));
        name = "Rusty sword";
        itemMap.put(name, new Item(name, id, price));
        name = "Unsired";
        itemMap.put(name, new Item(name, id, price));
        name = "Abyssal head";
        itemMap.put(name, new Item(name, id, price));
        name = "Abyssal orphan";
        itemMap.put(name, new Item(name, id, price));
        name = "Bludgeon claw";
        itemMap.put(name, new Item(name, id, price));
        name = "Bludgeon spine";
        itemMap.put(name, new Item(name, id, price));
        name = "Bludgeon axon";
        itemMap.put(name, new Item(name, id, price));
        name = "Looting bag";
        itemMap.put(name, new Item(name, id, price));
        name = "Callisto cub";
        itemMap.put(name, new Item(name, id, price));
        name = "Key master teleport";
        itemMap.put(name, new Item(name, id, price));
        name = "Hellpuppy";
        itemMap.put(name, new Item(name, id, price));
        name = "Pet chaos elemental";
        itemMap.put(name, new Item(name, id, price));
        name = "Pet zilyana";
        itemMap.put(name, new Item(name, id, price));
        name = "Pet dark core";
        itemMap.put(name, new Item(name, id, price));
        name = "Long bone";
        itemMap.put(name, new Item(name, id, price));
        name = "Curved bone";
        itemMap.put(name, new Item(name, id, price));
        name = "Fremennik helm";
        itemMap.put(name, new Item(name, id, price));
        name = "Pet dagannoth prime";
        itemMap.put(name, new Item(name, id, price));
        name = "Pet dagannoth supreme";
        itemMap.put(name, new Item(name, id, price));
        name = "Pet general graardor";
        itemMap.put(name, new Item(name, id, price));
        name = "Pet general graardor";
        itemMap.put(name, new Item(name, id, price));
        name = "Baby mole";
        itemMap.put(name, new Item(name, id, price));
        name = "Pet k'ril tsutsaroth";
        itemMap.put(name, new Item(name, id, price));
        name = "Kq head";
        itemMap.put(name, new Item(name, id, price));
        name = "Kalphite princess";
        itemMap.put(name, new Item(name, id, price));
        name = "Kbd heads";
        itemMap.put(name, new Item(name, id, price));
        name = "Prince black dragon";
        itemMap.put(name, new Item(name, id, price));
        name = "Pet kree'arra";
        itemMap.put(name, new Item(name, id, price));
        name = "Giant key";
        itemMap.put(name, new Item(name, id, price));
        name = "Champion scroll (giant)";
        itemMap.put(name, new Item(name, id, price));
        name = "Scorpia's offspring";
        itemMap.put(name, new Item(name, id, price));
        name = "Dark totem base";
        itemMap.put(name, new Item(name, id, price));
        name = "Dark totem middle";
        itemMap.put(name, new Item(name, id, price));
        name = "Dark totem top";
        itemMap.put(name, new Item(name, id, price));
        name = "Dark totem";
        itemMap.put(name, new Item(name, id, price));
        name = "Dark claw";
        itemMap.put(name, new Item(name, id, price));
        name = "Skotos";
        itemMap.put(name, new Item(name, id, price));
        name = "Pet smoke devil";
        itemMap.put(name, new Item(name, id, price));
        name = "Venenatis spiderling";
        itemMap.put(name, new Item(name, id, price));
        name = "Vet'ion jr.";
        itemMap.put(name, new Item(name, id, price));
        name = "Champion scroll (skeleton)";
        itemMap.put(name, new Item(name, id, price));
        //</editor-fold>
        return itemMap;
    }
    
    //Method to grab all tradeable icons from the internet
    //This is an extremely slow method thanks to Runescape's Offical API being a major pain
    //Between every image download, the program must wait 5 seconds or the API will refuse to download new icons
    //If the icon exists, the program will skip it
    //Can be implemented to run on startup to get image data of any new tradeable items that may have been added in a update
    //Packaging messes with directory, only works on development atm
    public void downloadIcons(Map<String, Item> itemMap){
        for(String key : itemMap.keySet()){
            try{
                String local_dir = System.getProperty("user.dir");
                String key_clean = key.replace("/", "-");
                File file = new File(local_dir+"\\src\\oslp\\images\\"+key_clean+".png");
                if(!file.exists()){
                    System.out.println("Getting PNG of item: "+key);
                    JSONObject imgjson = readJsonFromUrl("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item="+itemMap.get(key).getID());
                    imgjson = imgjson.getJSONObject("item");
                    URL img_url = new URL(imgjson.getString("icon"));
                    BufferedImage image = ImageIO.read(img_url);
                    ImageIO.write(image, "png", file);
                    System.out.println("PNG downloaded to: "+local_dir+"\\src\\oslp\\images\\"+key_clean+".png");
                    TimeUnit.SECONDS.sleep(5);
                }
            }
            catch(IOException e){
                System.out.println("Error: IOException -- I/O Operation failed or was interrupted");
            }
            catch(JSONException e){
                System.out.println("Error: JSONException -- URL Read does not have valid JSON formatting, check Internet connection");
            }
            catch(InterruptedException e){
                System.out.println("Error: InterruptedException -- Operation Interrupted, try again");
            }
        }
    }
}
