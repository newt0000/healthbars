# Healthbars Plugin

A Bukkit/Paper plugin that displays a floating heart-based health bar above mobs as a custom name tag. Each heart in the bar represents one actual Minecraft heart, so you always know exactly how much health an entity has at a glance.

**Author:** Newt_00
**Website:** ycs.newt-tech.com

---

## How it works

Every few ticks the plugin scans nearby entities for each online player and sets their custom name to a coloured heart bar, like:

```
§a❤❤❤❤❤❤❥♡♡♡
```

The bar length matches the entity's real max hearts (e.g. a Zombie has 10 hearts so it gets a 10-heart bar). A configurable cap prevents bosses with huge HP pools from showing an absurdly long bar. Each player has their own settings — one player can hide passive mob bars while another sees them — without affecting anyone else.

---

## Commands

| Command | Alias | Description |
|---|---|---|
| `/hb` | `/healthbars` | Opens the settings GUI |
| `/hb reload` | — | Reloads `config.yml` for all players and clears all bars |
| `/hbbuild` | — | Shows the current plugin build version |
| `/hbcleanup` | — | Removes all healthbar name tags and reapplies only to allowed entities |

All commands require the sender to be a player (not the console).

---

## Settings GUI (`/hb`)

Opening `/hb` shows a 3-row inventory with the following buttons:

| Slot | Item | What it does |
|---|---|---|
| 10 | Zombie Head | Toggles healthbars on hostile mobs **for you** |
| 11 | Sheep Spawn Egg | Toggles healthbars on passive mobs **for you** |
| 13 | Compass | Cycles your personal render distance through the four presets defined in config |
| 15 | Redstone | Reloads `config.yml` — applies to all players globally |
| 22 | Barrier | Closes the menu |

Settings are per-player. Toggling hostile or changing your distance only affects what you see — other players are not impacted.

---

## config.yml breakdown

### `settings`

Controls global server-side behaviour.

```yaml
settings:
  default-show-hostile: true   # Whether new players see hostile mob bars by default
  default-show-passive: true   # Whether new players see passive mob bars by default
  max-render-distance: 32.0    # Hard cap on render distance — players cannot exceed this
  update-interval: 10          # How often bars refresh, in ticks (20 ticks = 1 second)
  hide-full-health: false      # If true, bars are hidden entirely when an entity is at full HP
```

`update-interval` is a balance between responsiveness and server load. 10 ticks (0.5s) is a good default. Raise it on busy servers.

---

### `bar`

Controls what the health bar looks like.

```yaml
bar:
  max-segments: 20      # Maximum number of hearts to display regardless of entity max HP

  full-char:  "❤"       # Character for a full heart
  half-char:  "❥"       # Character for a half heart
  empty-char: "♡"       # Character for an empty heart

  smooth-color: true    # Reserved for future per-segment colour transitions

  colors:
    high:     "&a"      # Colour when HP is above 75%  (green)
    mid-high: "&e"      # Colour when HP is 50–75%     (yellow)
    mid:      "&6"      # Colour when HP is 25–50%     (gold)
    low:      "&c"      # Colour when HP is below 25%  (red)
```

You can swap the heart characters for any Unicode symbol or plain ASCII characters (e.g. `|` and `-`). Colours use standard Minecraft `&` colour codes.

`max-segments` is a display cap only — it does not affect actual HP. If an entity has 50 hearts but `max-segments` is 20, the 20 displayed hearts will be proportionally scaled to represent the full HP range.

---

### `entities`

Defines which entity types are treated as hostile or passive. Only entity types in these lists will receive a healthbar.

```yaml
entities:
  hostile:
    enabled: true
    list:
      - ZOMBIE
      - SKELETON
      - CREEPER
      # ... add any Bukkit EntityType name

  passive:
    enabled: true
    list:
      - COW
      - SHEEP
      # ... add any Bukkit EntityType name
```

Entity names must exactly match [Bukkit's EntityType enum](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html) (all caps, underscores). Any unrecognised name will log a warning on startup/reload and be skipped.

---

### `blacklist`

Entities in this list will **never** receive a healthbar, even if they appear in the `entities` lists above. Useful for blocking non-mob living entities like armour stands.

```yaml
blacklist:
  enabled: true
  entities:
    - ARMOR_STAND
    - ITEM_FRAME
    - PAINTING
    # etc.
```

Set `enabled: false` to disable the blacklist entirely without removing the list.

---

### `bossbar`

> **Note:** This section is read by the config but bossbar rendering is not yet fully implemented. These values are reserved for an upcoming feature.

```yaml
bossbar:
  enabled: true
  threshold-health: 40.0   # Switch to bossbar display when entity max HP exceeds this value
  color: PURPLE             # Bukkit BarColor value
  style: SEGMENTED_10       # Bukkit BarStyle value
```

---

### `performance`

Fine-tune how aggressively the plugin processes entities each tick.

```yaml
performance:
  hard-cutoff-distance: 48.0    # Entities beyond this distance are never processed (overrides player settings)
  stagger-ticks: 2              # Reserved — intended for spreading entity updates across ticks
  max-entities-per-player: 40   # Reserved — intended to cap entities processed per player per tick
```

`hard-cutoff-distance` is the absolute maximum. Even if a player sets their render distance to 32, entities beyond 48 blocks are skipped at the task level before `update()` is even called.

---

### `gui`

Controls the settings menu opened by `/hb`.

```yaml
gui:
  title: "&8Healthbar Settings"   # Inventory title — supports & colour codes

  rows: 3                         # Reserved (menu is always 3 rows currently)

  distances:                      # The four distance presets cycled by the Compass button
    low:    8
    medium: 16
    normal: 24
    max:    32

  pagination-size: 21             # Reserved for future paginated entity list views
```

The `distances` presets are what players cycle through in the GUI. The `medium` value is also used as the default render distance for new players.

---

## Adding new mobs

To add a new mob to the hostile or passive list, add its `EntityType` name to the appropriate list in `config.yml` and run `/hb reload`:

```yaml
entities:
  hostile:
    list:
      - ZOMBIE
      - WARDEN   # ← added
```

No restart required.

---

## Resetting config

If your `config.yml` is missing keys (e.g. after a plugin update), delete the file from `plugins/healthbars/` and restart the server. It will be regenerated with all defaults.
