plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.squareup.sqldelight")


}
version = "1.0-SNAPSHOT"
val ktorVersion = extra["ktor.version"]
val kotlinVersion = extra["kotlin.version"]
val composeVersion = extra["compose.version"]

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    ios {
        binaries {
            framework {
               // isStatic = true
                baseName = "shared"

            }
        }
    }







    cocoapods {
        summary = "Currency-Converter for Shared Module"
        homepage = "https://github.com/d1lithium/Currency-Converter"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            isStatic = true
            baseName = "shared"
        }
    }


    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-server-http-redirect:$ktorVersion")
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation("org.jetbrains.compose.components:components-resources:1.3.0-beta04-dev879")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
                api("io.github.qdsfdhvh:image-loader:1.2.8")
                implementation("com.squareup.sqldelight:runtime:1.5.5")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
             //   implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
                //implementation ("io.mockk:mockk:1.12.0")






            }

        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.mockk:mockk:1.4.1")
                implementation("junit:junit:4.13.2")

            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.appcompat:appcompat:1.5.1")
                implementation("androidx.core:core-ktx:1.9.0")
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("androidx.compose.ui:ui:1.2.1")
                implementation("androidx.compose.ui:ui-tooling:1.2.1")
                implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
                implementation("androidx.compose.foundation:foundation:1.2.1")
                implementation("androidx.activity:activity-compose:1.5.1")
                implementation("androidx.compose.material:material:1.4.3")
                implementation("androidx.appcompat:appcompat:1.6.1")
                implementation("com.squareup.sqldelight:android-driver:1.5.5")
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
                implementation("io.mockk:mockk-android:1.12.0")


            }

        }
        val androidTest by getting {
            dependencies {  }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation ("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:1.5.5")



            }
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting





    }
}

sqldelight {
    database("CCDatabase") {
        packageName = "com.moin.currency_converter"
        sourceFolders = listOf("sqldelight")
    }
}


android {
    namespace = "com.moin.currency_converter"
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        exclude("META-INF/*")
        exclude("META-INF/*")
    }
}