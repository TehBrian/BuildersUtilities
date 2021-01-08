# BuildersUtilities
A fork of Arcaniax's BuildersUtilities with customizable messages and
nicer-looking GUIs.

* [Downloads](https://github.com/ItsTehBrian/BuildersUtilities/releases)
* [Discord](https://tehbrian.xyz/discord)

## What's different?
- Every message is now easily configurable from the config.
- Both the permissions and the commands have been completely redone. (See below for an updated list!)
- All the GUIs have been revamped. Everything looks and feels a lot nicer.

In addition to that, everything has been polished and reviewed, rigorously,
by me, over a couple sleepless nights. Now *that's* what I call quality code.

## Commands and Permissions
A full list of commands and permissions can be found
[here](https://github.com/ItsTehBrian/BuildersUtilities/blob/master/src/main/resources/plugin.yml).

## Building
1. Run [BuildTools](https://www.spigotmc.org/wiki/buildtools/) using the `--compile craftbukkit` flag.
   This should install CraftBukkit to your local maven repository.    
2. Clone this repository. `git clone https://github.com/ItsTehBrian/BuildersUtilities`
3. Run `./gradlew build` in the main project directory.

Congratulations! The built jar can be found in `build/libs`.

## Contributing
Feel free to submit a pull-request or file an issue! Even if it's just a small typo,
all changes are welcome. If you're contributing code, please follow the project's code style,
which is just IntelliJ's default code style.
