import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.13-R0.1-SNAPSHOT")
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks {
    javadoc {
        enabled = true

        options {
            require(this is StandardJavadocDocletOptions)

            links("https://hub.spigotmc.org/javadocs/spigot/")
//            links("https://javadoc.io/doc/org.jetbrains/annotations-java5/23.0.0/") // as of 2025/10/02 this randomly returns 403
        }
    }

    withType<ShadowJar> {
        dependsOn("sourcesJar", "javadocJar")
    }
}