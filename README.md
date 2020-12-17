# Dragon Travel WIP <img src="https://static.wikia.nocookie.net/minecraft_gamepedia/images/b/b6/Dragon_Head.png/revision/latest/scale-to-width-down/150?cb=20200309195302" width="25" height="25">

 
A rewrite of the [original DragonTravel][1] plugin for the [SpongeAPI][2].
Works on [SpongeVanilla][4] and [SpongeForge][3].

[1]: https://github.com/Phiwa/DragonTravel
[2]: https://github.com/SpongePowered/SpongeAPI
[3]: https://github.com/SpongePowered/SpongeForge
[4]: https://github.com/SpongePowered/SpongeVanilla
###### Features
* Create path flights
* Create signs/holograms that when click trigger the flight
* Set prices for flights
* Different languages support, all messages are customisable
###### Depending on this plugin
You can depend on this plugin using jitpack. 

**Gradle**
```
repositories {
     ...
     maven { url "https://jitpack.io" }
 }
 dependencies {
     ...
     compile 'com.github.mjra007:DragonTravel:master-SNAPSHOT'
 }
```
## Commands
###### Path Flight Editor 
The path flight editor is a tool meant to be used by staff members to create public dragon paths.
That once saved can be taken by anyone with the dragontravel.path permission using ***/dragontravel path [path name]***
command.

| Command        | Effect       | Permission |
| --------------- |-------------| -------------| 
| /dragonpath create [path name] | Create a flight and puts you in flight mode | dragonpath.editor | 
| /dragonpath point [(optional) pointIndex] | Sets a path point [pointIndex can be specified]|dragonpath.editor | 
| /dragonpath save | Saves the flight being edited and puts you out of flight mode|dragonpath.editor | 
| /dragonpath remove [path name] | Removes dragon path | dragonpath.editor | 
| /dragonpath setsign [path name] | Sets a sign that can be used to trigger flight | dragonpath.editor |
###### General
| Command        | Effect       |  Permission |
| --------------- |-------------| -------------| 
| /dragontravel path [path name]| Starts path flight | dragontravel.path |
| /dragontravel sethome [(optional) home name]| Sets player home| dragontravel.home.**x** <br>`where x is number of houses player is allowed to set`</br>|
| /dragontravel home [(optional) home name]  | Takes player to their dragon home |dragontravel.home.**x** <br>`where x is number of houses player is allowed to set`</br>|
| /dragontravel removehome [(optional) home name]  | Removes dragon home |dragontravel.home.**x** <br>`where x is number of houses player is allowed to set`</br>|
| /dragontravel travel [x] [y] [z] [(optional)world] | Takes player to specified coordinates |dragontravel.targetlocation 

## Planned features
* Allow players to create personal path flights;
* Allow players to freely ride the dragon;
* Cache public path flight positions;
* API.

Feel free to suggest new features.

