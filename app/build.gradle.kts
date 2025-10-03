plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin1kept)
}

android {
    namespace = "com.tech.momenti"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tech.momenti"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.dagger)
    implementation(libs.gson)
    implementation (libs.retrofit)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    implementation (libs.play.services.location)
    implementation (libs.androidx.fragment.ktx)
    implementation (libs.glide)
    implementation (libs.sdp.android)
    implementation (libs.ssp.android)
    implementation (libs.gson)
    kapt(libs.dagger.compiler)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation (libs.lottie)
    implementation (libs.converter.gson)
    implementation(libs.androidx.navigation.fragment)



    //loader
   // implementation(libs.android.spinkit)
    implementation(libs.ccp)


    //circular progress bar
        implementation ("com.mikhaellopez:circularprogressbar:3.1.0")


    // calender
    implementation ("com.applandeo:material-calendar-view:1.9.2")

    //Dot indicator
    implementation("com.tbuonomo:dotsindicator:4.3")

    //swipe layout
  //  implementation ("com.chauthai.swipereveallayout:swipe-reveal-layout:1.4.1")
}