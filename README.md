# ![](https://static.wikia.nocookie.net/minecraft_gamepedia/images/d/da/Alex_wearing_Dragon_Head.png/revision/latest/scale-to-width-down/150?cb=20191111194818) Dragon Travel WIP

A rewrite of the [original DragonTravel][1] plugin for the [SpongeAPI][2].
Works on [SpongeVanilla][4] and [SpongeForge][3].

[1]: https://github.com/Phiwa/DragonTravel
[2]: https://github.com/SpongePowered/SpongeAPI
[3]: https://github.com/SpongePowered/SpongeForge
[4]: https://github.com/SpongePowered/SpongeVanilla
### Features
* Create flights which consist of waypoints.
* Create signs/holograms that when click trigger the flight
* Set prices for flights.
* Different languages support, all messages are customisable.
### Depending on this plugin
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
