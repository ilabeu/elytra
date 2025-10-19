plugins {
    id("fabric-loom") version "1.10"
    id("maven-publish")
    java
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
```

---

## **3. Estrutura do projeto:**
```
src/main/
├── java/com/seunome/elytraswap/
│   ├── AutoElytraSwap.java     ← Mod principal
│   └── ElytraSwapClient.java   ← Inicializador
└── resources/
    └── fabric.mod.json          ← Configuração do mod
