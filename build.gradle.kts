plugins {
    id("fabric-loom") version "1.6.9"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

base {
    archivesName.set(project.property("archives_base_name") as String)
}

version = project.property("version") as String
group = project.property("maven_group") as String

repositories {
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
    mavenCentral()

    // Repositórios Meteor (caso use dependências dele)
    maven {
        name = "Meteor Dev Releases"
        url = uri("https://maven.meteordev.org/releases")
    }
    maven {
        name = "Meteor Dev Snapshots"
        url = uri("https://maven.meteordev.org/snapshots")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

    // Se usar o Meteor Client
    modImplementation("meteordevelopment:meteor-client:${project.property("meteor_version")}")
    include("meteordevelopment:meteor-client:${project.property("meteor_version")}")
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("mc_version", project.property("minecraft_version"))

    filesMatching("fabric.mod.json") {
        expand(
            mapOf(
                "version" to project.version,
                "mc_version" to project.property("minecraft_version")
            )
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}
