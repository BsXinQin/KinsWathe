## Introduction

- Kin's Wathe is an addon mod for [WATHE](https://modrinth.com/mod/wathe) by [doctor4t](https://www.youtube.com/@doctor4t)

- And it also undergoes some balance modifications and fix some issues present in [Noelle's Roles](https://modrinth.com/mod/noelles-roles-tmm)

- You can check out how to play with addon mods in [agmas' video](https://www.youtube-nocookie.com/embed/h_c-dpjlONY)

- Download Kin's Wathe on Modrinth: [Kin's Wathe](https://modrinth.com/mod/kinswathe)

- My video about Kin's Wathe (Chinese version): [Bsxin's video](https://www.bilibili.com/video/BV1sqkpByEq8)


## Modifications to Noelle's Roles

- Issues Fix
  - Killers infinite stamina not working

- New Additions
  - Add item cooldown display text
  - Clear Trapper's Role Mine entity when game ends
  - Conductor can see dropped items (disabled by default)
  - Coroner can see bodies when mood is higher than mid (disabled by default)
  - Jester is unable to attack killer in Psycho Mode (disabled by default)


## Modifications to Wathe

- Can set whether killers drop Revolver after killing civilians

- Can set starting coins for civilians, neutrals and killers

- Can set coins obtained for killer after a kill


## Wathe Plus Function

- Add visual stamina bar display

- Jump can be allowed when not in game

- Auto join voice chat group for spectators

- Better modifications to Blackout and Psycho Mode

- Unlock 32 render distance limit when ultraPerfMode is disabled 

- Poisoned state will be automatically cleared upon death

- Switch player inventory to the last slot when game starts

- Clear field drops and player effects, inventory and bodies when game ends

- Add safe time when game starts, players can't damage or die in duration


## New Civillians

- Bellringer
  - Can check time
  - Use ability to reduce remaining time by 1 minute

- Cook
  - Has passive income
  - See players who eat through walls temporarily
  - Can purchase Pan and food in shop
  - Pan can use like Knife and stun target
  - Pan can destroy it to protects player one extra shooting of life

- Detective
  - Use ability to check whether target is innocent

- Judge
  - Has passive income
  - Use ability to make target glow

- Physician
  - Can see poisoned players through walls
  - Can analyze the death reason of the bodies
  - Has 15 seconds of sprint time
  - Initially equip with Medical Kit
  - Medical Kit can use on a player to remove poison and get coins
  - Can purchase Pill in shop
  - Pill can protect player one extra hit of life

- Robot
  - Has no mood
  - Infinite sprint time
  - Immune poison and dazing
  - Use ability to see the darkness clearly

- Technician
  - Can purchase Wrench, Capture Device and Power Restoration in shop
  - Wrench can restore any jammed or pried door to normal
  - Capture Device can trap passing player and inform the user
  - Power Restoration can end blackout effect instantly


## New Killers

- Bodymaker
  - Can create a body of someone to confuse others
  - Both death reason and role of the body can be edited

- Cleaner
  - Initially equip with Sulfuric Acid Barrel
  - Sulfuric Acid Barrel can dissolve a body and get coins
  - Use ability to clear field drops

- Drugmaker
  - Limit on the number of players that can be generated
  - Can see really poisoned players through walls
  - Get coins if someone was poisoned
  - Can not purchase Grenade and Psycho Mode in shop
  - Price of Poison Vial and Scorpion reduced by half
  - Can purchase Poison Injector and Blowgun in shop
  - Poison Injector can poison target
  - Blowgun can shoot and poison target

- Hunter
  - If holding knife when sprinting, hunter will speed up
  - Can not purchase Poison Vial and Scorpion in shop
  - Can purchase Hunting Knife in shop
  - Hunting Knife will immediately cooldown when stoping holding if sprinted
  - Use ability to refresh knifes' cooldown

- Kidnapper
  - Can not purchase Crowbar in shop
  - Can purchase Knockout Drug in shop
  - Initially equip with a Knockout Drug
  - Knockout Drug can daze target and let it follow you
  - Players that be dazed can not see, attack, use or voice chat
  - Can get additional coins for personally killing the dazed target
  - If distance between Kidnapper and dazed player exceeds 5 blocks, the player will be lifted


## New Neutrals

- Dreamer
  - Generates successfully when Nolle's Roles was loaded
  - Killer side neutral role
  - Initially equip with Dream Imprint
  - Dream Imprint can imprint target and protects it one extra hit of life
  - When imprinted target is damaged, it will teleport to user and give Dreamer a count to turn into killer
  - Turn into killer based on the non-killer players who taking Delusion Vial
  - The number of non-killer players taking Delusion Vial is one-fourth of the total number of players

- Hacker
  - Killer side neutral role
  - Limit on the number of players that can be generated
  - Can not generate with Mimic by default if Nolle's Roles was loaded
  - Initially equip with Phone for killers and Hacker
  - Phone can allow killers and Hacker voice chat in a group
  - Staring at a civilian for enough time to crack role and send it to every Phone, and get coins
  - Can purchase killer side powerful auxiliary props in shop

- Licensed Villain
  - Limit on the number of players that can be generated
  - Requires killing all players to win
  - Game does not end when all civilians or killers are dead
  - Has 15 seconds of sprint time
  - Initially equip with Lockpick
  - Can purchase Revolver in shop and have no backfire effect


## New Modifiers

- Magnate
  - Only generates on roles with passive income
  - Double passive income

- Taskmaster
  - Only generates on roles with task income
  - Killer roles receive 50 coins after completing tasks
  - Non-killer roles receive an additional 25 income after completing tasks

- Violator
  - Disabled by default
  - Can jump when in game


## Config Settings

- Modifications to other mods are disabled by default

- All roles ability are configurable

- Generation conditions of roles are configurable

- Most price of items in shop are configurable


## Commands

- Use /kinswathe to modify general config settings in game


## Requires

- [HarpyModLoader](https://modrinth.com/mod/harpymodloader)


## Special Thanks

- AqumpusAXY for Wathe Plus Function

- Annina for Bodymaker and Technician