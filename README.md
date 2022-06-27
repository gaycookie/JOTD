# Job of the Day 💼
[![minecraft-1.14.2-ready](https://img.shields.io/badge/Minecraft-1.18.2%20ready-brightgreen.svg)](https://www.minecraft.net)
[![build-against-1.14](https://img.shields.io/badge/Spigot%20Build-1.18.2-brightgreen.svg)](https://www.spigotmc.org/)

**JOTD** short for "Job of the Day" is a custom plugin made for a server I play on.  
This plugin must be used in combination with [Jobs Reborn] and does **not** cycle on its own.

## Required plugins
- [Jobs Reborn] (5.1.0.0)
- [PlaceholderAPI] (2.11.1)

## Permissions

| Permission   | Description                                                     |
|:-------------|:----------------------------------------------------------------|
| `jotd.admin` | Admin permissions, automatically assigned to those that are OP. |

## Commands

| Command          | Permissions  | Description                                 |
|:-----------------|:-------------|:--------------------------------------------| 
| `/jotd current`  |              | Shows the current Job of the Day boost.     |
| `/jotd generate` | `jotd.admin` | Generate a new random Job of the Day boost. |
| `/jotd previous` |              | Shows the previous Job of the Day boost.    |
| `/jotd reload`   | `jotd.admin` | Reload the Job of the Day plugin.           |

## Placeholders

| Placeholder             | Description                                      |
|:------------------------|:-------------------------------------------------|
| `%jotd_current_job%`    | Returns the current Job of the day.              |
| `%jotd_current_boost%`  | Return the current boost of the job of the day.  |
| `%jotd_previous_job%`   | Return the previous Job of the day.              |
| `%jotd_previous_boost%` | Return the previous boost of the job of the day. |

[Jobs Reborn]: https://www.spigotmc.org/resources/jobs-reborn.4216/
[PlaceholderAPI]: https://www.spigotmc.org/resources/placeholderapi.6245/