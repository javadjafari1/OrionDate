import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("root.publication")
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.detekt)
}

detekt {
    autoCorrect = true
    buildUponDefaultConfig = true
    config.from(files("$rootDir/detekt.yml"))
    source.from(
        "src/commonMain/kotlin",
        "src/commonTest/kotlin",
    )
}
tasks.register("detektAll") {
    description = "run detekt for all sourceSets"
    group = "Verification"
    allprojects {
        dependsOn(tasks.withType<Detekt>())
    }
}