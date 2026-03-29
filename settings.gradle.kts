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

listOf(
    "1_17_R1",
    "1_18_R1",
    "1_18_R2",
    "1_19_R1",
    "1_19_R2",
    "1_19_R3",
    "1_20_R1",
    "1_20_R2",
    "1_20_R3",
    "1_20_R4",
    "1_21_R1",
    "1_21_R2",
    "1_21_R3",
    "1_21_R4",
    "1_21_R5",
    "1_21_R6",
    "1_21_R7",
    "26_1",
).forEach {
    include(":mobchip-$it")
    project(":mobchip-$it").projectDir = rootDir.resolve("nms/$it")
}