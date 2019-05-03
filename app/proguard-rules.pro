# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in z class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.


-obfuscationdictionary dictionary.txt
-repackageclasses com.hviewtech.wawupay


-dontshrink
-optimizationpasses 5
-dontusemixedcaseclassnames#混淆时不会大小写混合类名
-dontskipnonpubliclibraryclasses #指定不去忽略非公共的库类
-dontpreverify #不预校验
-dontwarn #不警告
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* #优化配置
-dontoptimize #不优化
-ignorewarnings #忽略警告

-dontnote

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-printconfiguration
-keep,allowobfuscation @interface android.support.annotation.Keep

-keepclassmembers class ** {
    @android.support.annotation.Keep public <methods>;
    @android.support.annotation.Keep public <fields>;
}

-keep,allowobfuscation @interface com.hviewtech.wawupay.common.annotations.Keep

-keepclassmembers class ** {
    @com.hviewtech.wawupay.common.annotations.Keep public <methods>;
    @com.hviewtech.wawupay.common.annotations.Keep public <fields>;
}

-keepclassmembers @com.hviewtech.wawupay.common.annotations.Keep class ** {
    *;
}

#######################################################################
#混淆时不使用大小写混合类名
-dontusemixedcaseclassnames
#打印混淆的详细信息
-verbose

-dontskipnonpubliclibraryclassmembers
# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
#不进行优化，建议使用此选项，理由见上
#-dontoptimize
#不进行预校验，预校验是作用在Java平台上的，Android平台上不需要这项功能，去掉之后还可以加快混淆速度
#-dontpreverify
# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
# instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.

#保留注解参数
-keepattributes *Annotation*

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
#保留native方法的类名和方法名
-keepclasseswithmembernames class * {
    native <methods>;
}
# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
#保留自定义View,如"属性动画"中的set/get方法
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
# 保留混淆枚举中的values()和valueOf()方法

-keepclassmembers,allowoptimization enum * {
      public static **[] values();
      public static ** valueOf(java.lang.String);
}


-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Parcelable实现类中的CREATOR字段是绝对不能改变的，包括大小写
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}
# R文件中的所有记录资源id的静态字段
-keepclassmembers class **.R$* {
    public static <fields>;
}


-dontwarn com.hviewtech.wawupay.**

-dontwarn android.support.v4.**






-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

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

##---------------End: proguard configuration for Gson  ----------


##---------------Begin: proguard configuration for RxJava  ----------
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
##---------------End: proguard configuration for RxJava  ----------

##---------------Begin: proguard configuration for RxAndroid  ----------
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
##---------------End: proguard configuration for RxAndroid  ----------


##---------------Begin: proguard configuration for Retrofit  ----------
# Retain generic type information for use by reflection by converters and adapters.
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by z NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.-KotlinExtensions
##---------------End: proguard configuration for Retrofit  ----------


##---------------Begin: proguard configuration for OkHttp3  ----------
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with z relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform
##---------------End: proguard configuration for OkHttp3  ----------


##---------------Begin: proguard configuration for OKio  ----------
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*
##---------------End: proguard configuration for OKio  ----------


##---------------Begin: proguard configuration for Dagger2  ----------
-dontwarn com.google.errorprone.annotations.*
##---------------End: proguard configuration for Dagger2  ----------


##---------------Begin: proguard configuration for glide  ----------
-keep public class com.bumptech.glide.**
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
#-keep,allowobfuscation public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
##---------------End: proguard configuration for glide  ----------


##---------------Begin: proguard configuration for picture_picker  ----------
-keep class com.luck.picture.lib.** { *; }

-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }
##---------------End: proguard configuration for picture_picker  ----------


##---------------Begin: proguard configuration for SmartRefreshLayout  ----------
-keep class com.github.mmin18.** {*;}

-dontwarn android.support.v8.renderscript.*
-keepclassmembers class android.support.v8.renderscript.RenderScript {
  native *** rsn*(...);
  native *** n*(...);
}

-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

-keep class com.scwang.refreshlayout.activity.practice.BannerPracticeActivity$Movie {*;}
##---------------End: proguard configuration for SmartRefreshLayout  ----------


##---------------Begin: proguard configuration for agentweb  ----------
#-keep class com.just.agentweb.** {
#    *;
#}
-dontwarn com.just.agentweb.**
#-keepclassmembers class com.just.agentweb.sample.common.AndroidInterface{ *; }
##---------------End: proguard configuration for agentweb  ----------

##---------------Begin: proguard configuration for Extra  ----------
# vlayout
-keepattributes InnerClasses
-keep class com.alibaba.android.vlayout.ExposeLinearLayoutManagerEx { *; }
-keep class android.support.v7.widget.RecyclerView$LayoutParams { *; }
-keep class android.support.v7.widget.RecyclerView$ViewHolder { *; }
-keep class android.support.v7.widget.ChildHelper { *; }
-keep class android.support.v7.widget.ChildHelper$Bucket { *; }
-keep class android.support.v7.widget.RecyclerView$LayoutManager { *; }

# timber
-dontwarn org.jetbrains.annotations.**
# ormlite
# OrmLite uses reflection
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }
-keepclassmembers class * {
    @com.j256.ormlite.field.DatabaseField *;
}
-keepattributes Signature

-keepattributes *Annotation*
##---------------End: proguard configuration for Extra  ----------



##---------------Start: proguard configuration for UMeng Push  ----------
-dontwarn com.ut.mini.**
-dontwarn okio.**
-dontwarn com.xiaomi.**
-dontwarn com.squareup.wire.**
-dontwarn android.support.v4.**

-keepattributes *Annotation*

-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }

-keep class okio.** {*;}
-keep class com.squareup.wire.** {*;}

-keep class com.umeng.message.protobuffer.* {
	 public <fields>;
         public <methods>;
}

-keep class com.umeng.message.* {
	 public <fields>;
         public <methods>;
}

-keep class org.android.agoo.impl.* {
	 public <fields>;
         public <methods>;
}

-keep class org.android.agoo.service.* {*;}

-keep class org.android.spdy.**{*;}

-keep public class [应用包名].R$*{
    public static final int *;
}
# 如果compileSdkVersion为23，请添加以下混淆代码
-dontwarn org.apache.http.**
-dontwarn android.webkit.**
-keep class org.apache.http.** { *; }
-keep class org.apache.commons.codec.** { *; }
-keep class org.apache.commons.logging.** { *; }
-keep class android.net.compatibility.** { *; }
-keep class android.net.http.** { *; }

##---------------End: proguard configuration for UMeng Push  ----------



##---------------Start: proguard configuration for Moshi  ----------
# JSR 305 annotations are for embedding nullability information.
#-keep class com.squareup.moshi.**

-dontwarn javax.annotation.**

-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}

-keep @com.squareup.moshi.JsonQualifier interface *

# Enum field names are used by the integrated EnumJsonAdapter.
# Annotate enums with @JsonClass(generateAdapter = false) to use them with Moshi.
-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
}


# The name of @JsonClass types is used to look up the generated adapter.
-keepnames @com.squareup.moshi.JsonClass class *

# Retain generated JsonAdapters if annotated type is retained.
#-if @com.squareup.moshi.JsonClass class *
#-keep class <1>JsonAdapter {
#    <initialize>(...);
#    <fields>;
#}
#-if @com.squareup.moshi.JsonClass class **$*
#-keep class <1>_<2>JsonAdapter {
#    <initialize>(...);
#    <fields>;
#}
#-if @com.squareup.moshi.JsonClass class **$*$*
#-keep class <1>_<2>_<3>JsonAdapter {
#    <initialize>(...);
#    <fields>;
#}
#-if @com.squareup.moshi.JsonClass class **$*$*$*
#-keep class <1>_<2>_<3>_<4>JsonAdapter {
#    <initialize>(...);
#    <fields>;
#}
#-if @com.squareup.moshi.JsonClass class **$*$*$*$*
#-keep class <1>_<2>_<3>_<4>_<5>JsonAdapter {
#    <initialize>(...);
#    <fields>;
#}
#-if @com.squareup.moshi.JsonClass class **$*$*$*$*$*
#-keep class <1>_<2>_<3>_<4>_<5>_<6>JsonAdapter {
#    <initialize>(...);
#    <fields>;
#}


# Moshi Kotlin  (Use Reflect)
-keep class kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoaderImpl

-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
##---------------End: proguard configuration for Moshi  ----------

##---------------End: proguard configuration for BaiduMap  ----------
-keep class com.baidu.** {*;}
-keep class mapsdkvi.com.** {*;}
-dontwarn com.baidu.**
##---------------End: proguard configuration for BaiduMap  ----------