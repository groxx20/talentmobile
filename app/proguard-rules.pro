
##---------------Begin: proguard configuration for removing all log messages  ----------
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** e(...);
    public static *** w(...);
    public static *** wtf(...);
}
##---------------End: proguard configuration for removing all log messages  ----------


##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# keep anything annotated with @Expose
-keepclassmembers public class * {
    @com.google.gson.annotations.Expose *;
}
# Also keep classes that @Expose everything
-keep @com.google.gson.annotations.Expose public class *

# Application classes that will be serialized/deserialized over Gson
##---------------End: proguard configuration for Gson  ----------

##---------------Begin: proguard configuration for Retrofit  ----------
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
##---------------End: proguard configuration for Retrofit  ----------

##---------------Begin: proguard configuration for Support Libraries  ----------
-dontwarn android.support.**
-dontwarn okio.**
##---------------End: proguard configuration for Support LIbraries  ----------

##---------------Begin: proguard configuration for Dagger  ----------
-dontwarn com.google.errorprone.annotations.**
##---------------End: proguard configuration for Dagger  ----------

##---------------Begin: proguard configuration for Greendao + Rx  ----------
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties


