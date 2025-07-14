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
    }
}

rootProject.name = "OversteerF1"
include(":app")
include(":design")
include(":utilities")
include(":datasource")
include(":schedule")
include(":standings")
include(":network")
include(":result")
include(":authentication")
include(":settings")
include(":notifications")
include(":news")
