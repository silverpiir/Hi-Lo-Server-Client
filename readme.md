#Hi-Lo Card game

##Introduction:
This is a Hi-Lo card game.
Game Card deck is standard 52 cards.
Each round player must guess if next card drawn from the deck is equal, higher or lower to the base card.
Card ranks are from 2 to 10. Jack, Queen, King and Ace have the rank 10.

**1. Running the program:**

**1.1** /out/ folder is provided for ease of use. To run this program in an IDE, open the project and navigate to
out/artifacts/server_jar and out/artifacts/client_jar.
*In Intellij, first right click server.jar and choose 'Run server.jar', then do the same for client.jar.
*In Eclipse, first right click server.jar and choose 'Run As'>'Java Application' and choose GameServiceImpl>OK,
then right click client.jar and choose 'Run As'>'Java Application' and choose GameClientImpl>OK.

**1.2** To run from the command line, open 2 (two) command line windows, one in /Hi_Lo/out/artifacts/server_jar
 folder and one in /Hi_Lo/out/artifacts/client_jar folder, or navigate there using commands. In the server
 folder's command line, type **java -jar server.jar**, in the client folder **java -jar client.jar**

**2.** If both jar files are running, the client allows the player to start a round or exit.

**Notes: Running from the Intellij IDE produces an unwanted side effect:** input can only be entered while the countdown
timer is in between ticks, if the user waits too long to press enter, the input will be an empty string. Eclipse IDE
doesn't seem to have this problem. Running from the command line works well also.
