# LegendaryAPI #

LegendaryFramework is our API to create and manage minigames, endless-games or serves like the lobby. With the Framework you can create your own minigame and access a various of variables and functions easily.

### How to compile ###

1. You need the cloud SpigotPlugin installed in your local maven repository
2. Run maven clean install in in the directory where the pom.xml is located
3. Now you can use the LegendaryAPI in your plugin and you can copy the MinigameAPI-*.*.*-*.jar from the target directory.


### Maven ###

Put this in your pom.xml of your project under ```<dependencies>```.
~~~~
<dependency>
	<groupId>de.maltesermailo</groupId>
	<artifactId>MinigameAPI</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
~~~~
More information can be found in our [wiki](https://bitbucket.org/playlegendeu/legendaryapi/wiki/).