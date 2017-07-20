package oslp;

/**
 *
 * @author Artpicks
 */

import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.*;

public class DropTable {
    public static final String TABLE_FOLDER = System.getProperty("user.dir")+"\\Drop Tables\\";
    
    private void downloadDropTable(String monster_name){
        try{
            URL tableURL = new URL(OSLP.DROP_TABLE_ADDRESS+"?npc="+monster_name+"&icons=0");
            ReadableByteChannel urlStream = Channels.newChannel(tableURL.openStream());
            FileOutputStream outStream = new FileOutputStream(TABLE_FOLDER+monster_name+".txt");
            outStream.getChannel().transferFrom(urlStream, 0, Long.MAX_VALUE);
            outStream.close();
            urlStream.close();
        }
        catch(MalformedURLException e){
            System.out.println("Malformed URL: Please ensure the NPC name is spelt correctly and the internet connection is functional");
            System.exit(-1);
        }
        catch(IOException e){
            System.out.println("Inupt/Output Error: "+e.getMessage());
            System.exit(-1);
        }
    }
    
    public ArrayList<ItemDrop> getDropTable(String monster_name){
        String clean_name = monster_name.replace(" ", "_");
        File drop_table_file = new File(TABLE_FOLDER+clean_name+".txt");
        ArrayList<ItemDrop> returnList = new ArrayList();
        if(!drop_table_file.exists()){
            downloadDropTable(clean_name);
            drop_table_file = new File(TABLE_FOLDER+clean_name+".txt");
        }
        try{
            BufferedReader fileStream = new BufferedReader(new FileReader(drop_table_file));
            String line;
            fileStream.readLine(); //Get rid of monster info on line 1
            while((line = fileStream.readLine()) != null){
                String[] linesplit = line.split(",");
                linesplit[1] = linesplit[1].replace(" (noted)","");
                linesplit[1] = linesplit[1].replace("; ","-");
                String[] quantitySplit = linesplit[1].split("-");
                if(OSLP.itemCollection.containsKey(linesplit[0])){
                    if(quantitySplit.length == 1){
                        ItemDrop mapDrop = new ItemDrop(OSLP.itemCollection.get(linesplit[0]), 0, linesplit[2], Integer.parseInt(linesplit[1]), Integer.parseInt(linesplit[1]));
                        returnList.add(mapDrop);
                    }
                    else{
                        ItemDrop mapDrop = new ItemDrop(OSLP.itemCollection.get(linesplit[0]), 0, linesplit[2], Integer.parseInt(quantitySplit[0]), Integer.parseInt(quantitySplit[1]));
                        returnList.add(mapDrop);
                    }
                }
            }
        }
        catch(FileNotFoundException e){
           System.out.println("Drop Table not found!");
           System.exit(-1);
        }
        catch(IOException e){
            System.out.println("Inupt/Output Error: "+e.getMessage());
            System.exit(-1);
        }
        return returnList;
    }
}
