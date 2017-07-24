Old School Looting Program

This program requires:

jdom2-2.0.5.jar http://www.jdom.org/index.html

json-20170516.jar http://json.org/

This program was designed as an easy way to keep track of drops from various bosses and creatures around Old School Runescape. It supports dynamic window creation, XML log saving and drop table information scraped straight from the 2007scape wiki (credit to /u/overlysalty). Requires Java 7+. Bugs can and likely will happen, please refer to the git hub page (https://github.com/Artpicks/OSLP) and create a post in the Issues section if one does not already exist. This program supports the creation of user-made drops tables as well, details below.

POTENTIAL ISSUES:

- If drop tables are modified, generated xml logs will need to be migrated which the program does not currently support, thus the xml log will potentially crash the program

AREAS OF IMPROVEMENT:

- UI could use some makeup, as its function over form with regards to monster menus
- A built in manager and creator of drop tables/monsters (manual atm)
- If an icon doesn't exist, the program will crash. I've ensured all tradeable items are available, but untradeables may become an issue in the future

USAGE:

The program when run will get information from the menu folder (bosses.txt and monsters.txt) on what to display for the boss and monster window. These files can be modified to add/remove monsters and bosses, in the form (Monster Name, Monster_Wiki_Path) (no space between the comma). Once a monster is selected, the program will grab the drop table locally, or online if a local copy does not exist using a python script developed by /u/overlysalty (https://github.com/bclarke98/OSRSWikiScraper). Once this information is secured, the window will display every drop the monster can drop. Clicking any button will add the quantity to the log, or if the quantity varies, prompt the user to enter the amount. Killcount can be optionally added at the bottom of the menu. Enabling the remove radio button will remove quantities and kill counts instead of adding them. Closing or click the back button will save the log locally, and load from it the next time the user enters the monster. Logs are saved in the "/Logs" folder, and drop tables in the "/Drop Tables" folder. Files can be freely deleted from this folder at any time.

CUSTOM MONSTERS/BOSSES:

NOTE: this is only for monsters/bosses that do not exist on the wiki, if you want to add a existing monster, go to the Menus folder, open up either bosses.txt or monsters.txt, and the following line (No spaces after comma):
Monster Name, Wiki_Page_Name

Firstly, a custom drop table must be created in the form of:

>Monster Name
>
>Item_name1,Quantity1,Rarity1
>
>Item_name2,Quantity2a-Quantity2b,Rarity2
>
> etc.

Item_name1 must exist as a tradeable item, or the program will crash. Quantity cans either be of the form (number) or (number1-number2, with number1 < number2), Rarity must have one of the following values (Common) (Uncommon) (Rare) (Very rare) (Always).
So as an example, adding a single DWH drop with a very rare rarity (The name must be identical to the name of the item, case and all):

> Dragon warhammer,1,Very rare

Or a coin drop with 1 to 1000 coins as a common:

> Coins,1-1000,Common

Once done, save it to the Drop Tables folder as whatever name you'd like.

Now go to menus, open either bosses.txt or monsters.txt (depending on what you made) and add the following line:
Name of Monster, filename
where file name is whatever you named your drop table above. If done correctly, the window should generate for you!

DONE:

This has been a fun learning project, thanks for reading.
