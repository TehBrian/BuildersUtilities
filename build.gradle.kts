plugins {
  id("java")
  id("com.github.johnrengelman.shadow") version "7.1.2"
  id("io.papermc.paperweight.userdev") version "1.3.8"
  id("net.kyori.indra.checkstyle") version "3.0.0"
  id("xyz.jpenilla.run-paper") version "2.0.0"
}

group = "xyz.tehbrian"
version = "1.6.1"
description = "A curated bundle of tiny features that help builders do their thing."

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
  mavenCentral()
  maven("https://papermc.io/repo/repository/maven-public/")
  maven("https://repo.broccol.ai/releases/")
  maven("https://repo.thbn.me/releases/")
}

dependencies {
  paperDevBundle("1.19.2-R0.1-SNAPSHOT")

  implementation("broccolai.corn:corn-minecraft-paper:3.1.0")
  implementation("cloud.commandframework:cloud-minecraft-extras:1.7.1")
  implementation("com.google.inject:guice:5.1.0")
  implementation("dev.tehbrian:tehlib-paper:0.4.0")
  implementation("org.spongepowered:configurate-yaml:4.1.2")
  implementation("xyz.tehbrian.restrictionhelper:restrictionhelper-spigot:0.3.2")
}

tasks {
  assemble {
    dependsOn(reobfJar)
  }

  processResources {
    expand("version" to project.version, "description" to project.description)
  }

  base {
    archivesName.set("BuildersUtilities")
  }

  shadowJar {
    val libsPackage = "${project.group}.${project.name}.libs"
    relocate("broccolai.corn", "$libsPackage.corn")
    relocate("cloud.commandframework", "$libsPackage.cloud")
    relocate("com.google.inject", "$libsPackage.guice")
    relocate("dev.tehbrian.tehlib", "$libsPackage.tehlib")
    relocate("org.spongepowered.configurate", "$libsPackage.configurate")
    relocate("xyz.tehbrian.restrictionhelper", "$libsPackage.restrictionhelper")
  }

  runServer {
    minecraftVersion("1.19.2")
  }
}
