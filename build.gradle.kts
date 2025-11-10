plugins {
    id("fabric-loom") version "1.5-SNAPSHOT"
}

sourceCompatibility = targetCompatibility = JavaVersion.VERSION_17

archivesBaseName = project.archives_base_name
version = project.mod_version
group = project.maven_group

repositories {
    maven {
        name = "Meteor Dev Releases"
        url = "https://maven.meteordev.org/releases"
    }
    maven {
        name = "Meteor Dev Snapshots"
        url = "https://maven.meteordev.org/snapshots"
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${project.extra["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${project.extra["yarn_mappings"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.extra["loader_version"]}")
    modImplementation("meteordevelopment:meteor-client:${project.extra["meteor_version"]}")

    include("meteordevelopment:meteor-client:${project.extra["meteor_version"]}")
}


    // Meteor
    modImplementation "meteordevelopment:meteor-client:${project.meteor_version}"
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("mc_version", project.extra["minecraft_version"])

    filesMatching("fabric.mod.json") {
        expand(
            mapOf(
                "version" to project.version,
                "mc_version" to project.extra["minecraft_version"]
            )
        )
    }
}


tasks.withType(JavaCompile).configureEach {
    it.options.encoding("UTF-8")
}
