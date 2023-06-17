# Rainglow-Paper

Why is this server-side plugin needed for a client side mod?!

This is a counterpart for the Rainglow client mod to help control and store the colors server-side on Paper servers. That means the server can control the mob colors, and even have them persist between rejoins and restarts. \o/

---

For a list of permissions, see the [plugin.yml](https://github.com/BillyGalbreath/Rainglow-Paper/blob/master/src/main/resources/plugin.yml) file.

---

Clients require the [Rainglow client mod](https://modrinth.com/mod/rainglow), otherwise they will not see anything this plugin is controlling.

---

Why not CraftBukkit or Spigot!?

These server softwares are extremely old and are lacking the API needed for this plugin to properly function (specifically a way to know when mobs are loaded, not just when first spawned). You should think about upgrading to Paper anyways. less than 5% of modern servers are using CraftBukkit or Spigot (30% if you count all the legacy servers that refuse to update).

---

Available color modes:
* **all_colours**: black, blue, brown, cyan, gray, green, indigo, light blue, light gray, lime, magenta, orange, pink, purple, red, white, yellow
* **aro_pride**: black, gray, white, green
* **ace_pride**: black, gray, white, purple
* **bi_pride**: blue, pink, purple
* **gay_pride**: blue, cyan, green, white
* **genderfluid_pride**: purple, white, black, pink, blue
* **lesbian_pride**: red, orange, white, pink, purple
* **monochrome**: black, gray, white
* **enby_pride**: yellow, white, black, purple
* **pan_pride**: pink, yellow, blue
* **rainbow**: red, orange, yellow, green, blue, indigo, purple
* **trans_pride**: blue, white, pink
* **vanilla**: blue (for allay and glow squids), lime (for slimes)
