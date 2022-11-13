# Proguard, R8, Optimize APK


## Proguard
### Shrink, obfuscate, and optimize your app
- https://www.youtube.com/watch?v=bgpyuuzMlo0
- https://developer.android.com/studio/build/shrink-code
- To make your app as small as possible, you should enable shrinking in your release build to remove unused code and resources. 
- When enabling shrinking, you also benefit from obfuscation, which shortens the names of your app’s classes and members, 
- and optimization, which applies more aggressive strategies to further reduce the size of your app. 
- This page describes how R8 performs these compile-time tasks for your project and how you can customize them.
- When you build your project using Android Gradle plugin 3.4.0 or higher, 
- The plugin no longer uses ProGuard to perform compile-time code optimization. Instead, the plugin works with the R8 compiler to handle the following compile-time tasks:

## Code shrinking (or tree-shaking)
- Detects and safely removes unused classes, fields, methods, and attributes from your app and 
- its library dependencies (making it a valuable tool for working around the 64k reference limit).
- For example, if you use only a few APIs of a library dependency, shrinking can identify library code that your app is not using and remove only that code from your app. 
- To learn more, go to the section about how to shrink your code.
## Resource shrinking:
- Removes unused resources from your packaged app, including unused resources in your app’s library dependencies. 
- It works in conjunction with code shrinking such that once unused code has been removed, 
- any resources no longer referenced can be safely removed as well. To learn more, go to the section about how to shrink your resources.
## Obfuscation
- shortens the name of classes and members, which results in reduced DEX file sizes. 
- To learn more, go to the section about how to obfuscate your code.
## Optimization:
- Inspects and rewrites your code to further reduce the size of your app’s DEX files. 
- For example, if R8 detects that the else {} branch for a given if/else statement is never taken.
- R8 removes the code for the else {} branch. To learn more, go to the section about code optimization.
- When building the release version of your app, by default, R8 automatically performs the compile-time tasks described above for you. However, you can disable certain tasks or customize R8’s behavior through ProGuard rules files. In fact, R8 works with all of your existing ProGuard rules files, so updating the Android Gradle plugin to use R8 should not require you to change your existing rules.
- isMinifyEnabled = true
- isShrinkResources = true

## Important Tips
- Don't Obfuscate data classes used in network call

```
    android {
        buildTypes {
            getByName("release") {
                // Enables code shrinking, obfuscation, and optimization for only
                // your project's release build type.
                isMinifyEnabled = true
    
                // Enables resource shrinking, which is performed by the
                // Android Gradle plugin.
                isShrinkResources = true
    
                // Includes the default ProGuard rules files that are packaged with
                // the Android Gradle plugin. To learn more, go to the section about
                // R8 configuration files.
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        ...
    }
```

## R8
- https://developer.android.com/studio/build/shrink-code
- https://www.geeksforgeeks.org/how-to-use-r8-to-reduce-apk-size-in-android/
## How to see the mapping files in android studio
- Enable R8 in gradle by  **minifyEnabled true**
- Now select the Build type from the left bottom build variant
- Select Release
- Now rebuild the project
- app/build/outputs/mapping/mapping.txt (To see the details of the R8 Mapping)
- 
## Optimize APK file
- https://developer.android.com/topic/performance/reduce-apk-size
- 