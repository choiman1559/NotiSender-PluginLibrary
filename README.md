# NotiSender-PluginLibrary
[![](https://jitpack.io/v/choiman1559/NotiSender-PluginLibrary.svg)](https://jitpack.io/#choiman1559/NotiSender-PluginLibrary)

Library for developing Plugin for NotiSender

## Plugin structure

### Understanding role of plugin

### How to communicate with NotiSender

### Action types

## Getting start
### Intergration
**Step 1.** Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```css
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2.** Add the dependency

```css
	dependencies {
	        implementation 'com.github.choiman1559:NotiSender-PluginLibrary:Tag'
	}
```


### Initializing plugin library

**Step 1.** Add [Application](https://developer.android.com/reference/android/app/Application) class to your application project.

**Step 2.** Inheritance  custom ```PluginResponse``` class to receive remote tasks

```java
//Plugin Data Handling Class  
//Warning: All methods in this class are called "unexpectedly" in the background, not on the UI thread.  
public class PluginResponses implements PluginResponse {  
    @Override  
  public void onReceiveRemoteActionRequest(Context context, PairDeviceInfo deviceInfo, String taskType, String args) {  
        //Handling Remote Work Requests    
    }  
  
    @Override  
  public void onReceiveRemoteDataRequest(Context context, PairDeviceInfo deviceInfo, String requestedDataType) {  
        //Handling Remote Data Sending Requests
        //The requested data must be replied through the PluginAction.responseRemoteData method.
 
		//Return data to the plugin here  
		PluginAction.responseRemoteData(context, deviceInfo, requestedDataType, /* Your data to respond */);
	}  
  
    @Override  
  public void onReceiveException(Context context, Exception e) {  
        //Receive and handle exceptions thrown by plugins here  
	 }  
}
```

**Step 3.** Initializing plugin in your Application class
```java
//Plugin initialization when application is first run  
//Due to some technical issues, you need to launch any activity in your app at least once to start the plugin for the first time.  

public class Applications extends Application {  
    @Override  
  public void onCreate() {  
        super.onCreate();  
        
  //Initialize the plugin  
  Plugin plugin = Plugin.init(/* instance of PluginResponse-implement class */);  
  
  //Setting plugin options  
  plugin.setPluginTitle(/* Your plugin titile */);  
  plugin.setPluginDescription(/* Your plugin description */);  
  plugin.setAppPackageName(/* Plugin application's package name */);  
  plugin.setPluginReady(/* boolean */);  
  plugin.setSettingClass(/* Class<?> */);  
  plugin.setRequireSensitiveAPI(/* boolean*/);  
  }  
}
```
