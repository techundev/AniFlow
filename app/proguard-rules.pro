# Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}

# Koin
-keep class org.koin.** { *; }

# Ktor
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# Rome RSS
-keep class com.rometools.** { *; }
-dontwarn com.rometools.**

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.**

# Coil
-dontwarn coil.**

# Modelos de dominio — evitar que R8 los elimine
-keep class com.techun.dev.aniflow.**.domain.model.** { *; }
-keep class com.techun.dev.aniflow.**.data.local.entity.** { *; }