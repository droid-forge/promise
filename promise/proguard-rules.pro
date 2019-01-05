# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/yoctopus/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line quantity information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line quantity information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn javax.annotation.**
-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.* { ; }
-keep interface okhttp3.* { ; }
-dontwarn okhttp3.
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}
-dontwarn io.reactivex.functions.Function
-dontwarn rx.internal.util.**
# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

#ACRA specifics
# Restore some Source file names and restore approximate line numbers in the stack traces,
# otherwise the stack traces are pretty useless
-keepattributes SourceFile,LineNumberTable

# ACRA needs "annotations" so add this...
# Note: This may already be defined in the default "proguard-android-optimize.txt"
# file in the SDK. If it is, then you don't need to duplicate it. See your
# "project.properties" file to get the path to the default "proguard-android-optimize.txt".
-keepattributes *Annotation*

# ACRA loads Plugins using reflection, so we need to keep all Plugin classes
-keep class * extends org.acra.plugins.Plugin {*;}

# ACRA uses enum fields in annotations, so we have to keep those
-keep enum org.acra.** {*;}

-dontwarn android.support.**
