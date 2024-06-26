Gosu SDK for Android
========================

FEATURES *version: 1.2.10* (2024-03-21)
--------
* Authenticate
* Billing
* Tracking

**Download the official version: [click here](https://github.com/gosusdk/android-gamesdk/releases)**

INSTALLATION
------------

#### 1. In your root-level (project-level) Gradle file `<project>/build.gradle`, add more plugins dependency to your `build.gradle` file:

```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url "https://sdk-download.airbridge.io/maven" }
    }
}
dependencies {
    // ...
    // google service (use firebase tracking)
    classpath 'com.google.gms:google-services:4.3.15'
}
```	
#### 2. In your module (app-level) Gradle file `<project>/<app-module>/build.gradle`, add more plugins dependency to your `build.gradle` file:

```gradle
// google service plugin (use firebase tracking)
apply plugin: 'com.google.gms.google-services'

dependencies {
    // ...
    // GosuSDK
    implementation files('libs/gosusdk_v1.2.6.aar')
    //for in app billing
    implementation 'com.android.billingclient:billing:6.0.1'
    // FacebookSDK
    implementation 'com.facebook.android:facebook-android-sdk:latest.release'
    //for sigin GG SDK
    implementation 'com.google.android.gms:play-services-auth:20.6.0'
    //for firebase
    // Import the BoM for the Firebase platform
    implementation platform('com.google.firebase:firebase-bom:31.1.0')
    implementation 'com.google.guava:guava:31.1-android'
    implementation 'com.google.firebase:firebase-messaging:23.2.1'
    implementation 'com.google.firebase:firebase-analytics'
    //airbridge
    implementation "io.airbridge:sdk-android:2.22.2"
}
```	
**-Move config file (google-services.json) into the module (app-level) root directory of your app.**
```
app/
  google-services.json
```

**- Add gosu-service.json file to folder main/assets**
```json
{
  "client_id": "",
  "airb_app_name": "sdkgosutest",
  "airb_app_token": "d878da2af447440385fe9a4fe37b06a0"
}
```
#### 4. Edit Your Resources and Manifest
**- Open the /app/res/values/strings.xml file.**
```xml
<string name="facebook_app_id">1234</string>
<string name="fb_login_protocol_scheme">fb1234</string>
<string name="facebook_client_token">56789</string>
```
**-Open the /app/manifest/AndroidManifest.xml file.**
```xml
<!-- ============ PERMISSION ============== -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<!-- use for Push GSM -->
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
<!-- use for iab -->
<uses-permission android:name="com.android.vending.BILLING" />
<uses-permission android:name="com.google.android.gms.permission.AD_ID" />

<!-- ============ Facebook Login SDK ============== -->
<meta-data
    android:name="com.facebook.sdk.ApplicationId"
    android:value="@string/facebook_app_id"/>
<meta-data
    android:name="com.facebook.sdk.ClientToken"
    android:value="@string/facebook_client_token" />
<provider android:authorities="com.facebook.app.FacebookContentProvider116350609033094"
    android:name="com.facebook.FacebookContentProvider"
    android:exported="true"/>

<service
    android:name="com.gosu.gstracking.GFirebaseMessagingService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>

<!-- ======= AF Tracking ======= -->
<receiver
    android:name="com.appsflyer.MultipleInstallBroadcastReceiver"
    android:exported="true" >
    <intent-filter>
        <action android:name="com.android.vending.INSTALL_REFERRER" />
    </intent-filter>
</receiver>

<receiver
    android:name="com.appsflyer.SingleInstallBroadcastReceiver"
    android:exported="true" >
    <intent-filter>
        <action android:name="com.android.vending.INSTALL_REFERRER" />
    </intent-filter>
</receiver>
```
USAGE GOSU LOGIN SDK
--------------------
1. Initialize configuration for GosuSDK
---
```java
    protected void onCreate(Bundle savedInstanceState)()
    {
        // ...
        //Initialize SDK
        Gosu.getSharedInstance().initialize( this);
        Gosu.getSharedInstance().showFloating18Plus(this); //optional
        Gosu.getSharedInstance().getVersion(); //GET SDK Version
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GosuSDKConstant.IAP_RQ_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
        } else if(requestCode == GosuSDKConstant.FLOATING_RQ_CODE){
            Gosu.getSharedInstance().checkResultForFloatingView(requestCode, resultCode, data);
        } else {
            Gosu.getSharedInstance().onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
```
**NOTE**
* Login with Google: You send SHA-1 us [click here](https://developers.google.com/android/guides/client-auth)
* Login with Facebook: You send hash key us [more here](https://developers.facebook.com/docs/facebook-login/android)

2. GosuSDK Basic Functions
---
```java
//login
Gosu.getSharedInstance().showLogin(new DialogLoginID.OnLoginListener() {
    @Override
    public void onLoginSuccessful(String UserId, String UserName, String accesstoken) {
        //invoked when login successfully
    }
    
    @Override
    public void onLoginFailed() {
        //invoked when login failed
    }
});
//Logout
Gosu.getSharedInstance().logOut(new OnLogoutListener() {
    public void onLogoutSuccessful() {
        
    }

    public void onLogoutFailed() {
    
    }
});

```
3. Paying on Google Play with GosuSDK
---
```java
public void call_billing()
{
    Gosu.getSharedInstance().payment_iap(
        context, 
        username, 
        sku, 
        server, 
        orderid, 
        orderInfo, 
        amount, 
        character, 
        extraInfo,
        new Gosu.OnPaymentIAPListener() {
            public void onPaymentSuccessful(String msg) {
                
            }

            @Override
            public void onPaymentFailed(String msg, int errCode, String iapToken) {
                
            }
    });

    /**
     * context:	Context	Context for a activity window on the given Display.
     * username:	String	Username after login  in onLoginSuccessful
     * sku:	String	ProductID
     * server: ID of the server
     * orderid:	String	OrderId your create
     * orderinfo:	String	Note information your
     * amount:	Price of the item
     * character: ID of the character
     * extraInfo: Additional information that partners can send, which will be sent to the API to add gold after IAP payment.
     */
}
```

USAGE GOSU TRACKING SDK
--------------------

```java
GTrackingManger.getInstance().trackingStartTrial();
GTrackingManger.getInstance().trackingTutorialCompleted();
GTrackingManger.getInstance().doneNRU(
        "server_id",
        "role_id",
        "Role Name"
);
/* custom event */
GTrackingManger.getInstance().trackingEvent("level_20");
GTrackingManger.getInstance().trackingEvent("level_20", "{\"customer_id\":\"1234\"}");
/* example: 
jsonContent = {"event": "event_name", "params": {"key": "value", "key2": "value2"} }
*/
JSONObject jsonContent = new JSONObject();
jsonRole.put("character", "CharacterName");
jsonRole.put("server", "ServerID");        
GTrackingManger.getInstance().trackingEvent("event_name", jsonContent);
```
