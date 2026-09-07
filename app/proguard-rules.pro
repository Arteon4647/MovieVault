# ── Kotlin ────────────────────────────────────────────────────────────────────
-keep class kotlin.Metadata { *; }
-keepclassmembers class **$WhenMappings { *; }

# ── Domain models — не обфусковувати ─────────────────────────────────────────
# Gson/Retrofit серіалізують поля за іменами — якщо імена зміняться, JSON не розпарситься
-keep class com.example.movievault.data.remote.dto.** { *; }
-keep class com.example.movievault.domain.model.** { *; }
-keep class com.example.movievault.data.local.entity.** { *; }

# ── Retrofit + OkHttp ─────────────────────────────────────────────────────────
-keepattributes Signature
-keepattributes Exceptions
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn okio.**

# ── Gson ──────────────────────────────────────────────────────────────────────
-keep class com.google.gson.** { *; }
-keepattributes *Annotation*
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# ── Room ──────────────────────────────────────────────────────────────────────
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.**

# ── Hilt / Dagger ─────────────────────────────────────────────────────────────
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager { *; }
-dontwarn dagger.**

# ── Firebase ──────────────────────────────────────────────────────────────────
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# ── Crashlytics ───────────────────────────────────────────────────────────────
# Зберігаємо імена класів для читабельних crash reports
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-keep class com.google.firebase.crashlytics.** { *; }

# ── Timber ────────────────────────────────────────────────────────────────────
-dontwarn org.jetbrains.annotations.**
-keep class timber.log.** { *; }

# ── Coil ──────────────────────────────────────────────────────────────────────
-dontwarn coil.**

# ── Paging 3 ──────────────────────────────────────────────────────────────────
-keep class androidx.paging.** { *; }

# ── Coroutines ────────────────────────────────────────────────────────────────
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# ── Navigation 3 ──────────────────────────────────────────────────────────────
-keep class androidx.navigation3.** { *; }
-keep @kotlinx.serialization.Serializable class * { *; }
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
}

# ── Зберегти рядки для дебагу R8 (опціонально, видали у production) ───────────
# -printmapping build/outputs/mapping/release/mapping.txt
