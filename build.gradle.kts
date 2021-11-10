plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("xyz.jpenilla.run-paper") version "1.0.4"
}

group = "xyz.tehbrian"
version = "1.3.1"

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

repositories {
    mavenCentral()
    mavenLocal() // required for paper dependency

    maven("https://papermc.io/repo/repository/maven-public/") {
        name = "papermc"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven("https://s01.oss.sonatype.org/content/groups/public/") {
        name = "sonatype-s01"
    }
    maven("https://repo.broccol.ai/snapshots/") {
        name = "broccolai-snapshots"
    }
    maven("https://repo.thbn.me/snapshots/") {
        name = "thbn-snapshots"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper:1.17.1-R0.1-SNAPSHOT")

    implementation("com.google.inject:guice:5.0.1")

    implementation("net.kyori:adventure-text-minimessage:4.2.0-SNAPSHOT")
    implementation("org.spongepowered:configurate-yaml:4.1.2")

    implementation("xyz.tehbrian.restrictionhelper:restrictionhelper-spigot:0.2.0-SNAPSHOT")
    implementation("dev.tehbrian:tehlib-paper:0.1.0-SNAPSHOT")
    implementation("broccolai.corn:corn-minecraft-paper:3.0.0-SNAPSHOT")
}

tasks {
    processResources {
        expand("version" to project.version)
    }

    shadowJar {
        archiveBaseName.set("BuildersUtilities")
        archiveClassifier.set("")

        relocate("com.google.inject", "xyz.tehbrian.buildersutilities.libs.guice")
        relocate("net.kyori.adventure.text.minimessage", "xyz.tehbrian.buildersutilities.libs.minimessage")
        relocate("org.spongepowered.configurate.yaml", "xyz.tehbrian.buildersutilities.libs.configurate.yaml")
        relocate("xyz.tehbrian.restrictionhelper", "xyz.tehbrian.buildersutilities.libs.restrictionhelper")
        relocate("dev.tehbrian.tehlib", "xyz.tehbrian.buildersutilities.libs.tehlib")
        relocate("broccolai.corn", "xyz.tehbrian.buildersutilities.libs.corn")
        relocate("cloud.commandframework", "xyz.tehbrian.buildersutilities.libs.cloud")
    }

    runServer {
        minecraftVersion("1.17.1")
    }
}
