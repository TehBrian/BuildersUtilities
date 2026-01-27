plugins {
	id("java")
	id("com.github.johnrengelman.shadow") version "8.1.1"
	id("io.papermc.paperweight.userdev") version "1.7.1"
	id("net.kyori.indra.checkstyle") version "3.1.3"
	id("xyz.jpenilla.run-paper") version "2.3.0"
	id("com.github.ben-manes.versions") version "0.51.0"
}

group = "dev.tehbrian"
version = "1.8.3"
description = "A curated bundle of tiny features that help builders do their thing."

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
	mavenCentral()
	maven("https://papermc.io/repo/repository/maven-public/")
	maven("https://repo.thbn.me/releases/")
	maven("https://repo.tehbrian.dev/snapshots/")
	maven("https://repo.codemc.io/repository/maven-public/")
	maven("https://repo.broccol.ai/snapshots/")
}

dependencies {
	paperweight.paperDevBundle("1.21.1-R0.1-SNAPSHOT")

	implementation("org.jspecify:jspecify:1.0.0")
	implementation("love.broccolai.corn:corn-minecraft:4.0.0-SNAPSHOT")
	implementation("cloud.commandframework:cloud-paper:1.8.4")
	implementation("cloud.commandframework:cloud-minecraft-extras:1.8.4") {
		exclude("net.kyori", "adventure-api")
	}
	implementation("com.google.inject:guice:7.0.0")
	implementation("org.spongepowered:configurate-yaml:4.1.2")
	implementation("dev.tehbrian:tehlib-paper:0.6.0")
	implementation("dev.tehbrian:restrictionhelper-spigot:0.5.0")
	implementation("de.tr7zw:item-nbt-api:2.15.0")
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
		fun moveToLibs(vararg patterns: String) {
			for (pattern in patterns) {
				relocate(pattern, "$libsPackage.$pattern")
			}
		}

		moveToLibs(
				"love.broccolai.corn",
				"cloud.commandframework",
				"com.google",
				"dev.tehbrian.restrictionhelper-spigot",
				"dev.tehbrian.tehlib",
				"io.leangen",
				"jakarta.inject",
				"javax.annotation",
				"net.kyori.examination",
				"org.aopalliance",
				"org.checkerframework",
				"org.spongepowered",
				"org.yaml",
				"de.tr7zw.changeme.nbtapi"
		)
	}

	runServer {
		minecraftVersion("1.21.1")
	}
}
