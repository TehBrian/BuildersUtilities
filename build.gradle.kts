plugins {
  id("java")
  id("com.github.johnrengelman.shadow") version "8.1.1"
  id("io.papermc.paperweight.userdev") version "1.5.5"
  id("net.kyori.indra.checkstyle") version "3.1.1"
  id("xyz.jpenilla.run-paper") version "2.1.0"
  id("com.github.ben-manes.versions") version "0.47.0"
}

group = "xyz.tehbrian"
version = "1.7.0"
description = "A curated bundle of tiny features that help builders do their thing."

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
  mavenCentral()
  maven("https://papermc.io/repo/repository/maven-public/")
  maven("https://repo.thbn.me/releases/")
}

dependencies {
  paperweight.paperDevBundle("1.19.4-R0.1-SNAPSHOT")

  implementation("broccolai.corn:corn-minecraft-paper:3.2.0")
  implementation("cloud.commandframework:cloud-minecraft-extras:1.8.3")
  implementation("com.google.inject:guice:7.0.0")
  implementation("dev.tehbrian:tehlib-paper:0.4.2")
  implementation("org.spongepowered:configurate-yaml:4.1.2")
  implementation("dev.tehbrian.restrictionhelper:restrictionhelper-spigot:0.3.4")
}

tasks {
  assemble {
    dependsOn(reobfJar)
  }

  processResources {
    filesMatching("plugin.yml") {
      expand(
          "version" to project.version,
          "description" to project.description
      )
    }
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
    relocate("dev.tehbrian.restrictionhelper", "$libsPackage.restrictionhelper")
  }

  runServer {
    minecraftVersion("1.19.4")
  }
}
