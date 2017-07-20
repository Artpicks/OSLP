/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oslp;

/** This class specifies the ItemDrop type
 * ItemDrop differs from Item as it has additional information about:
 * Drop Quantities (int drop_min & int drop_max) (They are identical if the drop is static)
 * Rarity of the item dropped (String rarity) (Should only be of the form "Always", "Common", "Uncommon", "Rare", "Very rare")
 * The quantity of items dropped for the user (int quantity)
 * 
 * @author Artpicks
 */
public class ItemDrop {
    private int quantity;
    private Item drop_item;
    private String rarity;
    private int drop_min;
    private int drop_max;
    public ItemDrop(Item in_item, int amount, String in_rarity, int in_drop_min, int in_drop_max){
        quantity = amount;
        drop_item = in_item;
        rarity = in_rarity;
        drop_min = in_drop_min;
        drop_max = in_drop_max;
    }
    public int getQuantity(){
        return quantity;
    }
    public Item getItem(){
        return drop_item;
    }
    public String getRarity(){
        return rarity;
    }
    public int getMinDrop(){
        return drop_min;
    }
    public int getMaxDrop(){
        return drop_max;
    }
    //Returns true if the drop is static, false if it is a variable drop
    public boolean isDropConstant(){
        return (drop_min == drop_max);
    }        
    public void addQuantity(int add){
        int temp = quantity;
        int total = temp+add;
        if(total > temp){
            quantity = total;
        }
    }
    public void subQuantity(int sub){
        int temp = quantity;
        int total = temp-sub;
        if(total <= 0){
            quantity = 0;
        }
        else if(total < temp){
            quantity = total;
        }
    }
    public void setQuantity(int newnum){
        if(newnum > 0){
            quantity = newnum;
        }
        else{
            quantity = 0;
        }
    }
}
