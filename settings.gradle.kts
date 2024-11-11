rootProject.name = "mobchip-parent"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

listOf("base", "abstraction", "bukkit").forEach {
    include(":mobchip-$it")
    project(":mobchip-$it").projectDir = rootDir.resolve(it)
}

mapOf(
    "1_17_R1" to 17,
    "1_18_R1" to 17,
    "1_18_R2" to 17,
    "1_19_R1" to 17,
    "1_19_R2" to 17,
    "1_19_R3" to 17,
    "1_20_R1" to 17,
    "1_20_R2" to 17,
    "1_20_R3" to 17,
    "1_20_R4" to 21,
    "1_21_R1" to 21,
    "1_21_R2" to 21,
).forEach {
    val id = it.key

    include(":mobchip-$id")
    project(":mobchip-$id").projectDir = rootDir.resolve("nms/$id")
}