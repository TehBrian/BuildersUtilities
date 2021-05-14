plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "xyz.tehbrian"
version = "1.2.1"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
    // For CraftBukkit.
    mavenLocal()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://s01.oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    // To get, use BuildTools provided by Spigot.
    compileOnly("org.bukkit:craftbukkit:1.16.5-R0.1-SNAPSHOT")

    implementation("xyz.tehbrian.restrictionhelper:restrictionhelper-bukkit:0.2.0")
    implementation("org.slf4j:slf4j-api:2.0.0-alpha1")

    implementation("com.google.inject:guice:5.0.1")
}

tasks.processResources {
    expand("version" to project.version)
}

tasks.shadowJar {
    relocate("xyz.tehbrian.restrictionhelper", "xyz.tehbrian.buildersutilities.restrictionhelper")
    archiveBaseName.set("BuildersUtilities")
}
