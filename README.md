# NotiSender-PluginLibrary
[![](https://jitpack.io/v/choiman1559/NotiSender-PluginLibrary.svg)](https://jitpack.io/#choiman1559/NotiSender-PluginLibrary)

Library for developing Plugin for NotiSender

There is a live demo of the plugin!
Download the APK or clone the project and build: [PluginShowcase](https://github.com/choiman1559/NotiSender-PluginShowcase)

## Plugin structure

### Understanding role of plugin

Plugins are full-blown external apps which allow developers to add to the event, states and actions which NotiSender natively supports. Plugins can also easily be added to existing apps via an extra broadcast receiver (for signalling) and activity (to allow the user to edit their data).

Some important characteristics are:
-   the plugin UI is presented via the NotiSender UI just like native NotiSender functions (eg. [TelephonyPlugin](https://github.com/choiman1559/NotiSender-TelephonyPlugin))
-   the plugin developer needs no contact with the NotiSender developer to write, change, publish and/or sell their plugin app

### How to communicate with NotiSender

**Local task**
<img src="https://user-images.githubusercontent.com/43315227/226097642-d2dc11c4-56ca-4b0c-99a2-731a2832e481.png" height="300">

By default, NotiSender and the plugin communicate through [BroadcastReceiver](https://developer.android.com/reference/android/content/BroadcastReceiver), Android's interprocess Pub/Sub implementation. 

**Remote Task**
<img src="https://user-images.githubusercontent.com/43315227/226097645-74094a9b-b825-464c-b213-9f8211521f0e.png" height="300">

To communicate with other devices, NotiSender uses [SyncProtocol](https://github.com/choiman1559/RemoteSync) with FCM as a backend to receive/send data.

The following is an example of the process for getting remote data.
1. the plugin in Device#1 requests data from the NotiSender in Device#1.
2. Device#1's NotiSender sends the data request information over the protocol.
3. Device#2's NotiSender receives the requested data and sends it to Device#2's plugin.
4. Set the data to be sent in Device#2's plugin and send it to Device#1 in the same way as in steps 1-3.

### Action types
 - request Device List: 
  Get a list of paired devices from NotiSender.
  
 - request Remote Action: 
  Run a remote task from the same plugin installed on another device. 
  
 - request & response Remote Data: 
  Get specific data from the same plugin installed on different devices.
  
 - request Preferences: 
  Gets the Preference value stored in NotiSender.
  
 - request Service Status: 
 Check the service status of NotiSender.

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
For reference: [PluginResponses.java](https://github.com/choiman1559/NotiSender-PluginShowcase/blob/master/app/src/main/java/com/noti/plugin/showcase/PluginResponses.java)

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
For reference: [Applications.java](https://github.com/choiman1559/NotiSender-PluginShowcase/blob/master/app/src/main/java/com/noti/plugin/showcase/Applications.java)
```java
//Plugin initialization when application is first run  
//Due to some technical issues, you need to launch any activity in your app at least once to start the plugin for the first time.  

public class Applications extends Application {  
    @Override  
  public void onCreate() {  
        super.onCreate();  
        
  //Initialize the plugin  
  Plugin plugin = Plugin.init(/* instance of PluginResponse-implement class */); 
  //For Example:
  Plugin plugin = Plugin.init(new PluginResponses());
  
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

| Method | Argument Type | Description |
|--|--|--|
| setPluginTitle | String | The title of the plugin as it will appear in NotiSender |
| setPluginDescription | String | Description of the plugin as it will appear in NotiSender |
| setAppPackageName | String | The plugin's [application package name](https://developer.android.com/studio/build/application-id?hl=ko) |
| setPluginReady | boolean | Whether the plugin is ready for use (e.g. false if required permissions are not granted) <br><br> Note: If the value is false, users will not be able to activate the plugin in NotiSender. |
| setRequireSensitiveAPI | boolean | Use of NotiSender's sensitive APIs (e.g. getting Preference data, etc.)<br><br>Note: When the value is false, an exception is thrown when accessing a sensitive API |
| setSettingClass | Class<?> | The name of the component (class) of the activity to be displayed in the "Setting" menu when displaying the plug-in in NotiSender<br><br>Note: The argument must be a class inheriting "Activity" or "AppCompatActivity"|

## Usage of plugin

All methods below are declared in [PluginAction](https://github.com/choiman1559/NotiSender-PluginLibrary/blob/master/library/src/main/java/com/noti/plugin/process/PluginAction.java) class.

For example: [MainActivity.java](https://github.com/choiman1559/NotiSender-PluginShowcase/blob/master/app/src/main/java/com/noti/plugin/showcase/MainActivity.java)


### Getting paired device list of NotiSender
```java
public static void requestDeviceList(Context context, DeviceListListener.onReceivedListener callback)
```
Or, alternatively, use
```java
public static void requestDeviceList(Context context)
```
with Listener _(at [DeviceListListener](https://github.com/choiman1559/NotiSender-PluginLibrary/blob/master/library/src/main/java/com/noti/plugin/listener/DeviceListListener.java) class)_
```java
public synchronized static void addOnDataReceivedListener(onReceivedListener mOnReceivedListener)
```
| Argument  | Type | Description |
| -- | -- | -- |
| context | ```Context``` | Context for broadcast processing |
| callback | ```DeviceListListener.onReceivedListener``` | A listener object to receive device list data sent by NotiSender. |

### Request remote action

```java
public static void requestRemoteAction(Context context, PairDeviceInfo device, String type, String data)
```

| Argument  | Type | Description |
| -- | -- | -- |
| context | ```Context``` | Context for broadcast processing |
| device | ```PairDeviceInfo``` | The target device on which the action will be requested |
| type | ```String``` | Types of actions to run in other plugins |
| data | ```String``` | Additional custom data arguments for action behavior |

### Request remote data
```java
public static void requestRemoteData(Context context, PairDeviceInfo device, String type, RemoteDataListener.onReceivedListener callback) {
```
Or, alternatively, use
```java
public static void requestRemoteData(Context context, PairDeviceInfo device, String type)
```
with Listener _(at [RemoteDataListener](https://github.com/choiman1559/NotiSender-PluginLibrary/blob/master/library/src/main/java/com/noti/plugin/listener/RemoteDataListener.java) class)_
```java
public synchronized static void addOnDataReceivedListener(onReceivedListener mOnReceivedListener)
```
| Argument  | Type | Description |
| -- | -- | -- |
| context | ```Context``` | Context for broadcast processing |
| device | ```PairDeviceInfo``` | The target device on which the data will be requested |
| type | ```String``` | Types of data to request in other plugins |
| callback | ```RemoteDataListener.onReceivedListener``` | A listener object to receive data sent by NotiSender. |

### Getting preference of NotiSender
```java
public static void requestPreferences(Context context, String key, PrefsDataListener.onReceivedListener callback)
```
Or, alternatively, use
```java
public static void requestPreferences(Context context, String key)
```
with Listener _(at [PrefsDataListener](https://github.com/choiman1559/NotiSender-PluginLibrary/blob/master/library/src/main/java/com/noti/plugin/listener/PrefsDataListener.java) class)_
```java
public synchronized static void addOnDataReceivedListener(onReceivedListener mOnReceivedListener)
```
| Argument  | Type | Description |
| -- | -- | -- |
| context | ```Context``` | Context for broadcast processing |
| key | ```String``` | Key of preference to get from NotiSender |
| callback | ```PrefsDataListener.onReceivedListener``` | A listener object to receive data sent by NotiSender. |

Note: For security reasons, preference requests for the following keys are restricted

```FirebaseIIDPrefix```, ```GUIDPrefix```, ```MacIDPrefix```, ```AndroidIDPrefix```, ```ApiKey_Billing```, ```ApiKey_FCM```, ```ApiKey_Pushy```, ```UID```, ```EncryptionPassword```

### Check host service status
```java
public static void requestServiceStatus(Context context, ServiceStatusListener.onReceivedListener callback)
```
Or, alternatively, use
```java
public static void requestServiceStatus(Context context)
```
with Listener _(at [ServiceStatusListener](https://github.com/choiman1559/NotiSender-PluginLibrary/blob/master/library/src/main/java/com/noti/plugin/listener/ServiceStatusListener.java) class)_
```java
public synchronized static void addOnDataReceivedListener(onReceivedListener mOnReceivedListener)
```
| Argument  | Type | Description |
| -- | -- | -- |
| context | ```Context``` | Context for broadcast processing |
| callback | ```ServiceStatusListener.onReceivedListener``` | A listener object to receive data sent by NotiSender. |
