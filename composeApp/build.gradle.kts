import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.0.20"
    alias(libs.plugins.ksp)
    alias(libs.plugins.sqldelight) //sqdelight
}

kotlin {

    androidTarget("android") {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11" // Set JVM target for Kotlin to match Java
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        moduleName = "composeApp"
//        browser {
//            val rootDirPath = project.rootDir.path
//            val projectDirPath = project.projectDir.path
//            commonWebpackConfig {
//                outputFileName = "composeApp.js"
//                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
//                    static = (static ?: mutableListOf()).apply {
//                        // Serve sources to debug inside browser
//                        add(rootDirPath)
//                        add(projectDirPath)
//                    }
//                }
//            }
//        }
//        binaries.executable()
//    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.components.uiToolingPreview)
//            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.android.driver) //sqdelight

        }
        val commonMain by getting {
            resources.srcDir("composeResources")
        }
        commonMain.dependencies {
            implementation(compose.components.uiToolingPreview)
            implementation(libs.ui)
            implementation(libs.kotlin.stdlib)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(libs.kotlinx.coroutines.core)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.koin.compose.viewmodel.nav)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.sqlite.bundled)

            api(libs.androidx.datastore.preferences.core)
            api(libs.androidx.datastore.core.okio)
            implementation(libs.runtime) //sqdelight
            implementation(libs.android.sqldelight.coroutines) //sqdelight


        }
        desktopMain.dependencies {
            implementation(compose.components.uiToolingPreview)
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.native.sqlite.driver)
        }
        iosMain.dependencies {
            implementation(compose.components.uiToolingPreview)
            implementation(libs.native.driver) //sqdelight
            implementation(libs.ktor.serialization.kotlinx.json)
        }
    }
}

android {
    namespace = "org.example.todotask"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.todotask"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.7.3" // Ensure this version matches your composeVersion
    }

}

dependencies {
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.material3.android)
    debugImplementation(compose.uiTooling)
//    ksp(libs.androidx.room.compiler)


}
compose.desktop {
    application {
        mainClass = "org.example.todotask.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.todotask"
            packageVersion = "1.0.0"
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "org.example.todotask.resources"
    generateResClass = auto
}

//room {
//    schemaDirectory("$projectDir/schemas")
//}

sqldelight {
    databases {
        create(name = "TodoDatabase") {
            packageName.set("org.example.todotask.db")
        }
    }
}

