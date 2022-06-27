plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.papermc.paperweight.userdev") version "1.3.8"
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("net.kyori.indra.checkstyle") version "2.1.1"
}

group = "xyz.tehbrian"
version = "1.5.1"
description = "A curated bundle of tiny features that help builders do their thing."

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") {
        name = "papermc"
    }
    maven("https://repo.broccol.ai/snapshots/") {
        name = "broccolai-snapshots"
    }
    maven("https://repo.thbn.me/releases/") {
        name = "thbn"
    }
    maven("https://repo.thbn.me/snapshots/") {
        name = "thbn-snapshots"
    }
}

dependencies {
    paperDevBundle("1.19-R0.1-SNAPSHOT")

    implementation("broccolai.corn:corn-minecraft-paper:3.0.0-SNAPSHOT")
    implementation("cloud.commandframework:cloud-minecraft-extras:1.7.0")
    implementation("com.google.inject:guice:5.1.0")
    implementation("dev.tehbrian:tehlib-paper:0.3.1")
    implementation("org.spongepowered:configurate-yaml:4.1.2")
    implementation("xyz.tehbrian.restrictionhelper:restrictionhelper-spigot:0.3.0")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    processResources {
        expand("version" to project.version, "description" to project.description)
    }

    shadowJar {
        val libsPackage = "${project.group}.${project.name}.libs"
        relocate("com.google.inject", "$libsPackage.guice")
        relocate("org.spongepowered.configurate", "$libsPackage.configurate")
        relocate("xyz.tehbrian.restrictionhelper", "$libsPackage.restrictionhelper")
        relocate("dev.tehbrian.tehlib", "$libsPackage.tehlib")
        relocate("broccolai.corn", "$libsPackage.corn")
        relocate("cloud.commandframework", "$libsPackage.cloud")
    }

    runServer {
        minecraftVersion("1.19")
    }
}
