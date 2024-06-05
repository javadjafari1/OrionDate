pluginManagement {
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
        gradlePluginPortal()
        mavenCentral()
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
        gradlePluginPortal()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}