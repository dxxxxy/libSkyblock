# libSkyblock
A library made for easy interactions with Hypixel Skyblock items, players and much more. Contains many useful objects, methods and utilities for extracting data and presenting it in a pretty data form.

## Importing
```groovy
repositories {
    maven { url "https://repo.dreamys.studio/" }
}
```

```groovy
dependencies {
    implementation "studio.dreamys:libSkyblock:1.0.1"
}
```

## SBItem
> Initialize the class by passing in an ItemStack like so:
```java
ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
SBItem item = new SBItem(stack);
//item.getReforge()
//...
```

### Example of all functions
![img.png](img/img.png)

## SBPlayer
Currently, in development.