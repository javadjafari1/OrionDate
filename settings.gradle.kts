pluginManagement {
    includeBuild("convention-plugins")
    repositories {
        if (File("local.properties").exists()) {
            val localProperties = java.util.Properties().apply {
                load(java.io.FileInputStream("local.properties"))
            }
            maven {
                url = uri(localProperties.getProperty("url"))
                credentials {
                    username = localProperties.getProperty("username")
                    password = localProperties.getProperty("password")
                }
            }
        }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        if (File("local.properties").exists()) {
            val localProperties = java.util.Properties().apply {
                load(java.io.FileInputStream("local.properties"))
            }
            maven {
                url = uri(localProperties.getProperty("url"))
                credentials {
                    username = localProperties.getProperty("username")
                    password = localProperties.getProperty("password")
                }
            }
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "OrionDate"
include(":orionDate")
