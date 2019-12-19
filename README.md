# Welcome!
This is a fork of Arcaniax's BuildersUtilities that has been modernized and is
now exclusively 1.14+.

## What's different?
I felt that some features weren't really that important, such as the WorldEdit 
shortcuts, and there were also some features that became obsolete in 1.13+,
like the secret blocks menu, so I took the executive decision to DESTROY THEM >:D

Of course, there might be some people who are huge fans of those features, or
they're just not on 1.14+ yet. In that case, please use Arcaniax's original
BuildersUtilities, it's a great plugin!

Other than that, actually quite a bit has been changed:
- You no longer need FAWE or WorldEdit.
- Every single message and string is now easily configurable.
- Both the permissions and the commands have been completely redone.
- All of the GUIs have been changed. Everything looks and feels a lot nicer.
- Most notably, the code has essentially been rewritten.

What I originally set out to do with this fork was to clean up some code and
make some minor changes I felt the original plugin needed. This, however,
is not what I ended up doing.

Although the codebase is completely different and almost every line of code has
either been changed, added, or removed, the plugin still keeps the spirit of
helping builders do their thing.

## What are the permissions and commands?
Instead of giving you a big list that I have to update every now and again,
just navigate to `src/main/resources/plugin.yml` and you'll find all of the
permissions and commands in a slightly more "programmy" style than a pretty list.

If you find YAML too hard to read, that means you aren't a pro hacker yet.
To fix this, put on some sunglasses, play some 80s techno music, and
furiously mash your keyboard. Now you should be able to read it with no problem!

## Why should I use it?
Take a look at the [What's different?](#whats-different) section! In addition
to that, everything's been nicely polished and reviewed, rigorously, by me,
over a couple sleepless nights. Now *that's* what I call quality code.

## Sounds great! How do I get it?
You could do one of two things:
- [Download the pre-built .jar files.](https://github.com/ItsTehBrian/BuildersUtilities/releases)
- [Build it yourself.](#how-do-i-build-it-myself)

### How do I build it myself?
Ah, well aren't you just Mr. Fancypants? This is a bit
more of a pain, but it's still pretty simple.

Unless you have a good reason to build the plugin yourself, I'd recommend
downloading the pre-built .jar files, it's just easier. But hey, you do you.

Unfortunately NMS was required in the plugin because of the fact that we used
custom player heads. This just means we have to take a minor detour and
use BuildTools as well.

1. Make sure you have git and maven installed.
2. Install and run BuildTools for 1.14: https://www.spigotmc.org/wiki/buildtools/,
this should install CraftBukkit to your local maven repository.
3. Open up your Terminal of choice and navigate to some nice directory, like
your desktop.
4. Clone this git repository by doing `git clone https://github.com/ItsTehBrian/BuildersUtilities.git`.
5. Navigate inside of the repository folder by doing `cd BuildersUtilities`.
6. Build the jars by doing `mvn package`.

Congratulations, you have your very own .jar file!

## Can I contribute?
Sure! Feel free to submit a PR or file an issue. Even if it's just a small typo,
all changes are welcome. If you're contributing code, please follow our code style,
which is just IntelliJ's default code style.

---

#### ok looks great brian but where's the frekin secret blocks menu?
it's gone i'm so sorry

#### gash darn it where's the frikn secret blocks menu
i, i don't know what to tell you it's gone

#### brian tell me where's the frakin secret blocks menu
i told you, it's gone!!

#### wth?? why you destroy fraken secret blocks menu????!!
because it was bad

#### smh my head
i'm sorry