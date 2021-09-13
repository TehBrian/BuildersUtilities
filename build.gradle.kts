plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("xyz.jpenilla.run-paper") version "1.0.3"
}

group = "xyz.tehbrian"
version = "1.3.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://papermc.io/repo/repository/maven-public/") {
        name = "papermc-repo"
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
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper:1.17.1-R0.1-SNAPSHOT")

    implementation("com.google.inject:guice:5.0.1")

    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")
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
        relocate("xyz.tehbrian.restrictionhelper", "xyz.tehbrian.buildersutilities.restrictionhelper")
        archiveBaseName.set("BuildersUtilities")
    }

    runServer {
        minecraftVersion("1.17.1")
    }
}
