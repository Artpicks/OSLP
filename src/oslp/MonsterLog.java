package oslp;

/**
 *
 * @author Artpicks
 */

import java.util.*;
import java.io.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.*;
import org.jdom2.output.*;
import org.jdom2.JDOMException;

public class MonsterLog {
    private String name;
    private int times_killed;
    private ArrayList<ItemDrop> drops = new ArrayList<>();
    
    public MonsterLog(String monster_name, ArrayList<ItemDrop> item_drops){
        name = monster_name;
        drops = item_drops;
        times_killed = 0;
    }
    public String getName(){
        return name;
    }
    public ArrayList<ItemDrop> getDrops(){
        return drops;
    }
    public int getKillcount(){
        return times_killed;
    }
    public void addKillcount(int i){
        int total = i + times_killed;
        if(total > times_killed){
            times_killed = total;
        }
    }
    public void subKillcount(int i){
        int total = times_killed - i;
        if(total > 0 && total <= times_killed){
            times_killed = total;
        }
        else{
            times_killed = 0;
        }
    }
    public void saveMonsterXML(){
        try{
            Element monsterlog = new Element("MonsterLog");
            Document doc = new Document();
            Element monstername = new Element("Name");
            monstername.addContent(name);
            Element killcount = new Element("Killcount");
            killcount.addContent(Integer.toString(times_killed));
            monsterlog.addContent(killcount);
            Element itemlist = new Element("ItemList");
            Map<String, ItemDrop> dropsmap = getMap();
            int i = 0;
            for(String key : dropsmap.keySet()){
                Element itemtemp = new Element("Item"+i);
                itemtemp.addContent(new Element("Name").addContent(key));
                itemtemp.addContent(new Element("Price").addContent(Integer.toString(dropsmap.get(key).getItem().getPrice())));
                itemtemp.addContent(new Element("Quantity").addContent(Integer.toString(dropsmap.get(key).getQuantity())));
                itemlist.addContent(itemtemp);
                i++;
            }
            monsterlog.addContent(itemlist);
            doc.setRootElement(monsterlog);
        
            XMLOutputter outter = new XMLOutputter();
            outter.setFormat(Format.getPrettyFormat());
            String dir = System.getProperty("user.dir");
            outter.output(doc, new FileWriter(new File(dir+"//Logs//",name+".xml")));
        }
        catch(IOException e){
            System.out.println("Inupt/Output Error: "+e.getMessage());
        }
    }
    
    public void loadMonsterXML(){
        SAXBuilder builder = new SAXBuilder();
        String dir = System.getProperty("user.dir");
        File xmllog = new File(dir+"//Logs//",name+".xml");
        if(xmllog.exists()){
            try{
                Document doc = (Document) builder.build(xmllog);
                Element root = doc.getRootElement();
                String killcount = root.getChildText("Killcount");
                times_killed = Integer.parseInt(killcount);
                Element itemlistel = root.getChild("ItemList");
                List<Element> itemlist = itemlistel.getChildren();
                for(int i = 0; i < itemlist.size();i++){
                    Element temp = itemlist.get(i);
                    String itemname = temp.getChildText("Name");
                    int quan = Integer.parseInt(temp.getChildText("Quantity"));
                    for(int j=0; j < drops.size(); j++){
                        if(drops.get(j).getItem().getName().equals(itemname)){
                            drops.get(j).addQuantity(quan);
                            break;
                        }
                    }

                }
            } 
            catch(IOException e){
                System.out.println("Inupt/Output Error: "+e.getMessage());
            }
            catch(JDOMException e){
                System.out.println("JDOM Error: "+e.getMessage());
            }
        }
    }
    
    public Map<String, ItemDrop> getMap(){
        Map<String, ItemDrop> dropsmap = new TreeMap<>();
        drops.forEach((itemDrop) -> {
            if(dropsmap.containsKey(itemDrop.getItem().getName())){
                dropsmap.get(itemDrop.getItem().getName()).addQuantity(itemDrop.getQuantity());
                itemDrop.setQuantity(0);
            }
            else{
                dropsmap.put(itemDrop.getItem().getName(), itemDrop);
            }
        });
        return dropsmap;
    }
}
