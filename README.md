![foxglow_with_foxes_optimized](https://github.com/user-attachments/assets/dfdf4edf-3ef7-45d1-ace2-d6ce7b7d2a4a)
#
![fabric](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@2/assets/cozy/supported/fabric_vector.svg)
![fabric-api](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@2/assets/cozy/requires/fabric-api_vector.svg)
![forge](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@2/assets/cozy/supported/forge_vector.svg)
![quilt](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@2/assets/cozy/supported/quilt_vector.svg)
![quilted-fabric-api](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@2/assets/cozy/requires/quilted-fabric-api_vector.svg)
[![kofi-singular-alt](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@2/assets/cozy/donate/kofi-singular-alt_vector.svg)](https://ko-fi.com/emafire003)
[![try-cgl](https://www.emafire003.me/badges/try-cgl-cozy-small.svg)](https://modrinth.com/mod/coloredglowlib)

This mod makes foxes and players glow when they eat Glow Berries, or other items that should make the player glow! Simple right? This should be in vanilla, anyway you can have it this way now!

![foxglow](https://user-images.githubusercontent.com/29462910/153724910-88b14423-4768-4f78-b801-4142d1dffbe9.gif)

[![bisecthosting](https://github.com/Emafire003/ColoredGlowLib/assets/29462910/973c0c1a-062c-4c4a-aa04-f02e184fd5d7)](https://www.bisecthosting.com/LightDev)

### Configuration
#### Fabric
You can adjust the duration of the glow effect using the gamerule `foxGlowDuration`. It takes one parameter which is the duration in seconds. (Without decimals)

`/gamerule foxGlowDuration <duration>`

For example, 2 seconds would be
`/gamerule foxGlowDuration 2`

You can toggle the effect for players using `doPlayersGlow`. The value `true` will enable the effect, `false` will disable it. It is active by default.

`/gamerule doPlayersGlow <true/false>`

For example, to disable the effect
`/gamerule doPlayersGlow false`

Similar to this one, there also are the `customColorGlow` which determines weather or not they glow effect should have a custom color, and `customColorGlow` which determines weather the custom colored above should be random or not. (both of which require [ColoredGlowLib](https://www.curseforge.com/minecraft/mc-mods/coloredglowlib) to work)

To add an item to the list of items that can make foxes/player glow when eaten you will need to use the commmand:

`/foxglow addGlowFood <item>`

Where <item> is the namespace of something, for example to make Golden Apples a "glowfood" you will do this: `/foxglow addGlowFood minecraft:golden_apple`
The cool thing is that it **support other mod's items as well!!!**, so if you have a mod that adds a Glow Berry juice or something, you can add it like that!

On this note, if you are a developer, you can use the `FoxGlow.addGlowFood(Item item)` method to add your mod's items by default. (Javadocs are present in the code)

The remove the items, you can use `/foxglow removeGlowFood <item>`, same thing for devs: `FoxGlow.removeGlowFood(Item item)`

#### Forge
It's the same as on fabric but you have to use the `/foxglow set <gamerule> <value>` and `/foxglow get <gamerule>` for the gamerules, because working with gamerules with forge is kind of a mess, at least for now.

For example, to set the *foxglowDuration* rule to 100, you will need to do this:

`/foxglow set foxglowDuration 100`

(The adding items to the list of items that can make foxes/player glow when eaten is exactly the same so scroll above)

Also, as side note there also are some localizations of the gamerules in the World Creation screen.

## Setup

Just drop it inside your mods folder alongside [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api).
If you want to custom glow colors, install [ColoredGlowLib](https://www.curseforge.com/minecraft/mc-mods/coloredglowlib)
Also this mod can be installed only on the server if you wish, you don't necesserly need it on the client.

![fox1](https://user-images.githubusercontent.com/29462910/152815217-8ca8abcf-2dfe-4c20-8235-84013a047c1e.png)

## License

This mod is available under the MIT License.

## Support me
This really is a stupidly simple mod, but still, if you would like to offer me a coffee, here you go.

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/S6S88307C)

Thanks to [JustFoxx](https://github.com/JustFoxx), [Dqu1J](https://github.com/Dqu1J) for translations

For modpack devs: You are permitted to use this mod without directly asking, but please credit me somewhere, it would help! (Also, i'm kind of a curios person so maybe send me a message when you include it into your modpack and i'd like to check it out)
