import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val mcVersion = "26.2"

dependencies {
    api(project(":mobchip-base"))
    api(project(":mobchip-abstraction"))
    api(project(":mobchip-26_1"))

    compileOnly("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT")
    testImplementation("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
