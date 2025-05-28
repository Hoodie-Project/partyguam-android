pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven (url = "https://devrepo.kakao.com/nexus/content/groups/public/")
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "guam"
include(":app")
include(":common")
include(":data")
include(":di")
include(":domain")
include(":presentation")
include(":design")
