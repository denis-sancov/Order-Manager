package com.sancov.buildsrc

object AndroidTarget {
    const val compileSdk = 33

    const val buildToolsVersion = "30.0.3"
    const val ndkVersion = "25.0.8775105"

    const val minSdk = 21
    const val targetSdk = 33
}

object Module {
    const val routers = ":routers"

    object Core {
        const val di = ":di"
        const val data = ":data"
        const val domain = ":domain"
        const val core = ":core"
    }

    object UI {
        const val designSystem = ":ui:design_system"
        const val controlKit = ":ui:control-kit"
    }

    object Feature {
        const val dashboard = ":feature:dashboard"
        const val order = ":feature:order"
    }
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.3.1"

    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.1.5"
    const val materialDesign = "com.google.android.material:material:1.6.1"

    object Kotlin {
        const val version = "1.7.20"
        const val jvmTarget = "1.8"

        const val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"

        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10"

        object Coroutines {
            private const val version = "1.6.4"

            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        }
    }

    object Hilt {
        private const val version = "2.42"

        const val implementation = "com.google.dagger:hilt-android:$version"
        const val kapt = "com.google.dagger:hilt-android-compiler:$version"

        const val plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
    }

    object AndroidX {
        object AppCompat {
            private const val version = "1.7.0-alpha01"

            const val core = "androidx.appcompat:appcompat:$version"
            const val resources = "androidx.appcompat:appcompat-resources:$version"
        }

        const val core = "androidx.core:core-ktx:1.9.0"
        const val activity = "androidx.activity:activity-ktx:1.7.0-alpha01"

        const val fragment = "androidx.fragment:fragment-ktx:1.5.3"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"

        object Lifecycle {
            private const val version = "2.5.1"

            const val common = "androidx.lifecycle:lifecycle-common-java8:$version"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"

            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val viewModelSavedState =
                "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
        }

        object RecyclerView {
            const val implementation = "androidx.recyclerview:recyclerview:1.3.0-rc01"
        }

        object Test {
            private const val version = "1.4.0"
            const val core = "androidx.test:core:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"
        }

        object Espresso {
            const val core = "androidx.test.espresso:espresso-core:3.2.0"
        }

        object Junit {
            private const val version = "1.1.2"

            const val core = "junit:junit:4.+"
            const val ext = "androidx.test.ext:junit-ktx:$version"
        }
    }
}
