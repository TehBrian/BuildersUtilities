plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("xyz.jpenilla.run-paper") version "1.0.3"
}

group = "xyz.tehbrian"
version = "1.3.0-BETA"

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://s01.oss.sonatype.org/content/groups/public/")

    maven("https://repo.incendo.org/content/repositories/snapshots/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")

    implementation("org.slf4j:slf4j-api:2.0.0-alpha1")
    implementation("com.google.inject:guice:5.0.1")

    implementation("xyz.tehbrian.restrictionhelper:restrictionhelper-bukkit:0.2.0")
    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")
    implementation("org.incendo.interfaces:interfaces-paper:1.0.0-SNAPSHOT")
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
