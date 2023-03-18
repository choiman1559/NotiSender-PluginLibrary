# NotiSender-PluginLibrary
[![](https://jitpack.io/v/choiman1559/NotiSender-PluginLibrary.svg)](https://jitpack.io/#choiman1559/NotiSender-PluginLibrary)

Library for developing Plugin for NotiSender

There is a live demo of the plugin!
Download the APK or clone the project and build: [PluginShowcase](https://github.com/choiman1559/NotiSender-PluginShowcase)

## Plugin structure

### Understanding role of plugin

Plugins are full-blown external apps which allow developers to add to the event, states and actions which NotiSender natively supports. Plugins can also easily be added to existing apps via an extra broadcast receiver (for signalling) and activity (to allow the user to edit their data).

Some important characteristics are:
-   the plugin UI is presented via the NotiSender UI just like native NotiSender functions
-   the plugin developer needs no contact with the NotiSender developer to write, change, publish and/or sell their plugin app

eg. [TelephonyPlugin](https://github.com/choiman1559/NotiSender-TelephonyPlugin)

### How to communicate with NotiSender

![Default](https://user-images.githubusercontent.com/43315227/226097642-d2dc11c4-56ca-4b0c-99a2-731a2832e481.png)

![Other Devices](https://user-images.githubusercontent.com/43315227/226097645-74094a9b-b825-464c-b213-9f8211521f0e.png)



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

## Usage of plugin

### Getting paired device list of NotiSender

### Request remote action

### Request remote data

### Getting preference of NotiSender

### Check host service status


