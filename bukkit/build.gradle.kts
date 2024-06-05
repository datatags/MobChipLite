import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val versions = mapOf(
    "1_17_R1" to 17,
    "1_18_R1" to 17,
    "1_18_R2" to 17,
    "1_19_R1" to 17,
    "1_19_R2" to 17,
    "1_19_R3" to 17,
    "1_20_R1" to 17,
    "1_20_R2" to 17,
    "1_20_R3" to 17,
    "1_20_R4" to 21
)

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.13-R0.1-SNAPSHOT") {
        version {
            strictly("1.13-R0.1-SNAPSHOT")
        }
    }

    // API

    api(project(":mobchip-base"))
    api(project(":mobchip-abstraction"))

    versions.forEach {
        api(project(":mobchip-${it.key}"))
    }
}

java {
    withJavadocJar()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

sourceSets["main"].allJava.srcDir("src/main/javadoc")

tasks {
    compileJava {
        for (version in versions) {
            if (version.value >= 17) {
                dependsOn(project(":mobchip-${version.key}").tasks["remap"])
            }
        }
    }

    javadoc {
        enabled = true

        options {
            require(this is StandardJavadocDocletOptions)

            links("https://hub.spigotmc.org/javadocs/spigot/")
            links("https://javadoc.io/doc/org.jetbrains/annotations-java5/23.0.0/")
        }
    }

    register("sourcesJar", Jar::class.java) {
        archiveClassifier.set("sources")

        val sources = listOf(
            sourceSets["main"].allSource,
            project(":mobchip-base").sourceSets["main"].allSource
        )

        from(sources)
    }

    jar.configure {
        artifacts {
            add("archives", getByName<Jar>("sourcesJar"))
        }
    }

    withType<ShadowJar> {
        dependsOn("sourcesJar", "javadocJar")
    }
}