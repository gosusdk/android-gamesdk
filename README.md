# GoSu SDK Demo for Android

A comprehensive demo application showcasing the integration and usage of GoSu SDK v1.2.13 for Android games and applications.

## 📋 Table of Contents

1. [🚀 Features](#-features)
2. [📋 Prerequisites](#-prerequisites)
3. [⚡ Quick Start](#-quick-start)
4. [🔧 Installation & Configuration](#-installation--configuration)
5. [📱 SDK Integration Guide](#-sdk-integration-guide)
6. [📁 Project Structure](#-project-structure)
7. [💡 Usage Examples](#-usage-examples)
8. [🏗️ Building & Testing](#️-building--testing)
9. [🔍 Troubleshooting](#-troubleshooting)
10. [🤝 Contributing](#-contributing)

## 🚀 Features

- **GoSu SDK Integration**: Complete implementation of GoSu SDK v1.2.13
- **Social Login**: Facebook and Google authentication
- **In-App Billing**: Google Play Billing integration  
- **Analytics**: Firebase Analytics and Crashlytics
- **Push Notifications**: Firebase Cloud Messaging
- **Attribution Tracking**: AppFlyer & Airbridge integration for user acquisition tracking

## 📋 Prerequisites

| Requirement | Version |
|-------------|---------|
| **Android Studio** | Arctic Fox or later |
| **JDK** | 17 or later |
| **Android SDK API** | 35 |
| **Minimum SDK** | API Level 23 (Android 6.0) |
| **Gradle** | 8.1.4 or higher |

## ⚡ Quick Start

### 1. Clone & Setup

```bash
git clone https://github.com/gosusdk/android-gamesdk.git
cd android-gamesdk
```

### 2. Configure Services

| Service | Configuration |
|---------|--------------|
| **Firebase** | 1. Create project in [Firebase Console](https://console.firebase.google.com/)<br/>2. Download `google-services.json` → `app/` directory |
| **Facebook** | 1. Create app in [Facebook Developers](https://developers.facebook.com/)<br/>2. Update `facebook_app_id` in `strings.xml` |
| **GoSu SDK** | 1. Contact GoSu team for latest SDK<br/>2. Configure `gosu-service.json` in `assets/` |
| **AppFlyer** | 1. Create account at [AppFlyer](https://www.appsflyer.com/)<br/>2. Get App ID and Dev Key |
| **Airbridge** | 1. Create account at [Airbridge](https://www.airbridge.io/)<br/>2. Get App Name and App Token |

### 3. Build & Run

```bash
./gradlew assembleDebug
```

## 🔧 Installation & Configuration

### Project-Level Configuration (`build.gradle`)

```gradle
buildscript {
    dependencies {
        classpath 'com.android.tools.build:gradle:8.1.4'
        classpath 'com.google.gms:google-services:4.4.0'
        classpath 'com.google.firebase:firebase-crashlytics-gradle:2.9.9'
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        // Airbridge Maven Repository
        maven { url "https://sdk-download.airbridge.io/maven" }
    }
}
```

### App-Level Configuration (`app/build.gradle`)

<details>
<summary>📋 Click to expand build.gradle configuration</summary>

```gradle
// Required plugins
apply plugin: 'com.android.application'
apply plugin: 'com.google.gms.google-services'
apply plugin: 'com.google.firebase.crashlytics'

android {
    compileSdk 35
    namespace = "vn.gosu.demosdk"
    
    defaultConfig {
        applicationId "com.jwsvn.gg"
        minSdkVersion 23
        targetSdkVersion 35
        versionCode 67
        versionName "14.1.702"
        multiDexEnabled true
    }
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
}

dependencies {
    // AndroidX Core Libraries
    implementation 'androidx.appcompat:appcompat:1.7.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.multidex:multidex:2.0.1'
    
    // GoSu SDK v1.2.13 (Latest Version)
    implementation files('libs/gosusdk_v1.2.13.aar')
    
    // Google Play Billing for in-app purchases
    implementation 'com.android.billingclient:billing:8.0.0'
    
    // Facebook SDK for social login
    implementation 'com.facebook.android:facebook-android-sdk:17.0.0'
    
    // Google Play Services for authentication
    implementation 'com.google.android.gms:play-services-auth:21.0.0'
    
    // Firebase SDK (latest versions)
    implementation platform('com.google.firebase:firebase-bom:32.8.0')
    implementation 'com.google.firebase:firebase-messaging'
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'com.google.firebase:firebase-crashlytics'
    
    // AppFlyer SDK for attribution tracking
    implementation 'com.appsflyer:af-android-sdk:6.14.0'
    implementation 'com.android.installreferrer:installreferrer:2.2'
    
    // Airbridge SDK for attribution tracking
    implementation 'io.airbridge:sdk-android:2.23.4'
    
    // Utility libraries
    implementation 'com.google.guava:guava:32.1.3-android'
    
    // Testing dependencies
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
```
</details>

### Android Manifest Configuration

<details>
<summary>📋 Click to expand AndroidManifest.xml setup</summary>

```xml
<!-- Required Permissions -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
<uses-permission android:name="com.android.vending.BILLING" />
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
<uses-permission android:name="com.google.android.gms.permission.AD_ID" />

<application android:name=".YourApplication" ... >

    <!-- Facebook SDK Configuration -->
    <meta-data android:name="com.facebook.sdk.ApplicationId" 
               android:value="@string/facebook_app_id"/>
    <meta-data android:name="com.facebook.sdk.ClientToken" 
               android:value="@string/facebook_client_token" />
    
    <provider android:authorities="com.facebook.app.FacebookContentProvider{YOUR_FACEBOOK_APP_ID}"
              android:name="com.facebook.FacebookContentProvider" android:exported="true"/>

    <!-- Firebase Messaging Service -->
    <service android:name=".YourFirebaseMessagingService" android:exported="false">
        <intent-filter>
            <action android:name="com.google.firebase.MESSAGING_EVENT" />
        </intent-filter>
    </service>

    <!-- AppFlyer Receivers -->
    <receiver android:name="com.appsflyer.MultipleInstallBroadcastReceiver" android:exported="true">
        <intent-filter><action android:name="com.android.vending.INSTALL_REFERRER" /></intent-filter>
    </receiver>
    <receiver android:name="com.appsflyer.SingleInstallBroadcastReceiver" android:exported="true">
        <intent-filter><action android:name="com.android.vending.INSTALL_REFERRER" /></intent-filter>
    </receiver>

    <!-- Airbridge Configuration -->
    <receiver android:name="io.airbridge.sdk.AirbridgeInstallReferrerReceiver" android:exported="true">
        <intent-filter android:priority="1000">
            <action android:name="com.android.vending.INSTALL_REFERRER" />
        </intent-filter>
    </receiver>

</application>
```
</details>

### Resource & Configuration Files

| File | Location | Purpose |
|------|----------|---------|
| **strings.xml** | `app/src/main/res/values/` | Facebook App ID & Client Token |
| **google-services.json** | `app/` | Firebase configuration |
| **gosu-service.json** | `app/src/main/assets/` | GoSu SDK configuration |

<details>
<summary>📋 Click to expand configuration examples</summary>

**strings.xml:**
```xml
<string name="facebook_app_id">YOUR_FACEBOOK_APP_ID</string>
<string name="fb_login_protocol_scheme">fbYOUR_FACEBOOK_APP_ID</string>
<string name="facebook_client_token">YOUR_FACEBOOK_CLIENT_TOKEN</string>
```

**gosu-service.json:**
```json
{
  "gosu_service": {
    "project_info": {
      "project_number": "YOUR_PROJECT_NUMBER",
      "project_id": "your-project-id"
    },
    "client": [
      {
        "client_info": {
          "mobilesdk_app_id": "YOUR_MOBILE_SDK_APP_ID",
          "android_client_info": {
            "package_name": "your.package.name"
          }
        }
      }
    ]
  }
}
```
</details>

## 📱 SDK Integration Guide

### 🔧 SDK Initialization

**Application Level:**
```java
public class YourApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Gosu.getSharedInstance().initialize(this);
    }
}
```

**Activity Level:**

```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize SDK
        Gosu.getSharedInstance().initialize(this);
        Gosu.getSharedInstance().showFloating18Plus(this); // Optional
        
        // Get SDK Version
        String version = Gosu.getSharedInstance().getVersion();
        Log.d("GoSuSDK", "SDK Version: " + version);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GosuSDKConstant.IAP_RQ_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
        } else if(requestCode == GosuSDKConstant.FLOATING_RQ_CODE) {
            Gosu.getSharedInstance().checkResultForFloatingView(requestCode, resultCode, data);
        } else {
            Gosu.getSharedInstance().onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
```

### 🔐 Authentication

**Login & Logout:**
```java
// Show Login Dialog
Gosu.getSharedInstance().showLogin(new DialogLoginID.OnLoginListener() {
    @Override
    public void onLoginSuccessful(String userId, String userName, String accessToken) {
        Log.d("Auth", "Login successful: " + userName);
        handleUserLogin(userId, userName, accessToken);
    }
    
    @Override
    public void onLoginFailed() {
        Log.e("Auth", "Login failed");
        showLoginError();
    }
});

// Logout
Gosu.getSharedInstance().logOut(new OnLogoutListener() {
    @Override
    public void onLogoutSuccessful() {
        Log.d("Auth", "Logout successful");
        clearUserData();
    }
    @Override
    public void onLogoutFailed() {
        Log.e("Auth", "Logout failed");
    }
});
```

> **📝 Important Notes:**
> - **Google Login**: Send SHA-1 certificate to GoSu team ([Guide](https://developers.google.com/android/guides/client-auth))
> - **Facebook Login**: Send key hash to GoSu team ([Guide](https://developers.facebook.com/docs/facebook-login/android))

### 💳 In-App Billing

```java
public void performPayment() {
    Gosu.getSharedInstance().payment_iap(
        this,           // context
        username,       // username from login
        sku,           // product ID
        serverId,      // server ID
        orderId,       // your order ID
        orderInfo,     // order description
        amount,        // price amount
        characterId,   // character ID
        extraInfo,     // additional info
        new Gosu.OnPaymentIAPListener() {
            @Override
            public void onPaymentSuccessful(String message) {
                Log.d("Billing", "Payment successful: " + message);
                handlePaymentSuccess(message);
            }

            @Override
            public void onPaymentFailed(String message, int errorCode, String iapToken) {
                Log.e("Billing", "Payment failed: " + message + ", Code: " + errorCode);
                handlePaymentError(message, errorCode);
            }
        }
    );
}
```

<details>
<summary>📋 Payment Parameters Reference</summary>

| Parameter | Type | Description |
|-----------|------|-------------|
| `context` | Context | Activity context |
| `username` | String | Username from successful login |
| `sku` | String | Product ID from Google Play Console |
| `serverId` | String | Game server identifier |
| `orderId` | String | Unique order identifier |
| `orderInfo` | String | Order description/notes |
| `amount` | double | Item price |
| `characterId` | String | Character identifier |
| `extraInfo` | String | Additional data sent to payment API |

</details>

### 📊 Analytics & Tracking

**GoSu SDK Tracking:**
```java
// Predefined events
GTrackingManager.getInstance().trackingStartTrial();
GTrackingManager.getInstance().trackingTutorialCompleted();
GTrackingManager.getInstance().doneNRU("server_id", "role_id", "Role Name");

// Custom events
GTrackingManager.getInstance().trackingEvent("level_20");
GTrackingManager.getInstance().trackingEvent("level_20", "{\"customer_id\":\"1234\"}");
```

**Advanced GoSu Tracking:**
```java
JSONObject eventData = new JSONObject();
try {
    eventData.put("character", "CharacterName");
    eventData.put("server", "ServerID");
    eventData.put("level", 25);
    eventData.put("score", 1500);
    
    GTrackingManager.getInstance().trackingEvent("level_completed", eventData);
} catch (JSONException e) {
    Log.e("Tracking", "Error creating event data", e);
}
```

**AppFlyer Attribution Tracking:**
```java
// Initialize AppFlyer in Application class
public class YourApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // AppFlyer initialization
        AppsFlyerLib.getInstance().init("YOUR_APPSFLYER_DEV_KEY", null, this);
        AppsFlyerLib.getInstance().start(this);
    }
}

// Track custom events
Map<String, Object> eventParams = new HashMap<>();
eventParams.put("level", 25);
eventParams.put("score", 1500);
AppsFlyerLib.getInstance().logEvent(this, "level_completed", eventParams);
```

**Airbridge Attribution Tracking:**
```java
// Initialize Airbridge in Application class
public class YourApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Airbridge initialization
        AirbridgeConfig config = new AirbridgeConfig.Builder("YOUR_APP_NAME", "YOUR_APP_TOKEN")
            .setLogLevel(AirbridgeConfig.Logger.DEBUG) // Only for debug builds
            .build();
        Airbridge.init(this, config);
    }
}

// Track custom events
Map<String, Object> eventParams = new HashMap<>();
eventParams.put("level", 25);
eventParams.put("score", 1500);
Airbridge.trackEvent("level_completed", eventParams);

// Track purchase events
Airbridge.trackEvent("purchase", "USD", 9.99, null);
```

### 🔔 Push Notifications

```java
public class YourFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        
        Log.d("FCM", "From: " + remoteMessage.getFrom());
        
        // Handle data payload
        if (remoteMessage.getData().size() > 0) {
            Log.d("FCM", "Message data payload: " + remoteMessage.getData());
            handleGoSuMessage(remoteMessage.getData());
        }

        // Handle notification payload
        if (remoteMessage.getNotification() != null) {
            Log.d("FCM", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            showNotification(remoteMessage.getNotification());
        }
    }
    
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d("FCM", "Refreshed token: " + token);
        sendTokenToGoSuSDK(token);
    }
    
    private void sendTokenToGoSuSDK(String token) {
        // Register token with GoSu SDK
    }
}
```

### ♻️ Lifecycle Management

```java
@Override
protected void onResume() {
    super.onResume();
    if (Gosu.getSharedInstance() != null) {
        Gosu.getSharedInstance().onResume(this);
    }
}

@Override
protected void onPause() {
    super.onPause();
    if (Gosu.getSharedInstance() != null) {
        Gosu.getSharedInstance().onPause(this);
    }
}

@Override
protected void onDestroy() {
    super.onDestroy();
    if (Gosu.getSharedInstance() != null) {
        Gosu.getSharedInstance().onDestroy(this);
    }
}
```

## 📁 Project Structure

```
android-gamesdk/
├── 📁 app/
│   ├── 📁 libs/                       # SDK libraries
│   │   └── 📄 gosusdk_v1.2.13.aar     # GoSu SDK
│   ├── 📁 src/main/
│   │   ├── 📁 assets/                 # Configuration files
│   │   │   ├── 📄 gosu-service.json   # GoSu SDK config
│   │   │   └── 📄 gosu-service.example.json
│   │   ├── 📁 java/vn/gosu/demosdk/   # Source code
│   │   │   ├── 📄 GoSuSDKManager.java # SDK wrapper
│   │   │   ├── 📄 MainActivity.java   # Main activity
│   │   │   └── 📄 SplashActivity.java # Splash screen
│   │   └── 📁 res/                    # App resources
│   ├── 📄 build.gradle                # App build config
│   ├── 📄 google-services.json        # Firebase config
│   └── 📄 proguard-rules.pro         # ProGuard rules
├── 📄 build.gradle                    # Project build config
├── 📄 gradle.properties              # Gradle properties
├── 📄 README.md                       # This documentation
├── 📄 CONTRIBUTING.md                 # Contribution guide
├── 📄 CHANGELOG.md                    # Version history
└── 📄 LICENSE                        # License info
```

### 🎯 Key Components

| Component | Purpose |
|-----------|---------|
| **GoSuSDKManager.java** | SDK initialization, authentication, payments, analytics |
| **MainActivity.java** | Complete integration workflow, UI interactions, examples |
| **SplashActivity.java** | App entry point, SDK initialization sequence |

## 💡 Usage Examples

<details>
<summary>🔍 Click to expand complete integration example</summary>

### Complete Integration Example

Based on the actual project implementation, here's a clean and simple example of integrating GoSu SDK:

```java
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Gosu mGosu = null;
    private String currentUserName = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize GoSu SDK
        initializeGoSuSDK();
        
        // Setup UI components
        setupUIComponents();
    }
    
    private void initializeGoSuSDK() {
        // Initialize SDK
        Gosu.getSharedInstance().initialize(this);
        mGosu = Gosu.getSharedInstance();
        
        // Optional: Show 18+ age verification
        mGosu.showFloating18Plus(this);
        
        // Add GoSu view to your layout
        RelativeLayout mainLayout = findViewById(R.id.main_layout);
        mainLayout.addView(mGosu.addView(this));
        
        Log.d(TAG, "GoSu SDK initialized successfully");
    }
    
    private void setupUIComponents() {
        // Login Button
        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> performLogin());
        
        // Logout Button
        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> performLogout());
        
        // Payment Button
        Button btnPayment = findViewById(R.id.btn_payment);
        btnPayment.setOnClickListener(v -> showPaymentOptions());
        
        // Floating View Button
        Button btnFloating = findViewById(R.id.btn_floating);
        btnFloating.setOnClickListener(v -> toggleFloatingView());
        
        // Tracking Button
        Button btnTracking = findViewById(R.id.btn_tracking);
        btnTracking.setOnClickListener(v -> performTracking());
    }
    
    private void performLogin() {
        mGosu.showLogin(new DialogLoginID.OnLoginListener() {
            @Override
            public void onLoginSuccessful(String userId, String userName, String accessToken) {
                currentUserName = userName;
                Log.d(TAG, "Login successful - User: " + userName);
                
                // Update UI
                TextView userNameView = findViewById(R.id.tv_username);
                userNameView.setText("Welcome: " + userName);
                
                // Show main content
                findViewById(R.id.main_content).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_login).setVisibility(View.GONE);
                
                // Track login event
                trackLoginEvent(userId, userName);
            }
            
            @Override
            public void onLoginFailed() {
                Log.e(TAG, "Login failed");
                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void performLogout() {
        mGosu.logOut(new DialogLoginID.OnLogoutListener() {
            @Override
            public void onLogoutSuccessful() {
                Log.d(TAG, "Logout successful");
                
                // Reset UI
                findViewById(R.id.main_content).setVisibility(View.GONE);
                findViewById(R.id.btn_login).setVisibility(View.VISIBLE);
                
                Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLogoutFailed() {
                Log.e(TAG, "Logout failed");
                Toast.makeText(MainActivity.this, "Logout failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void showPaymentOptions() {
        // Show payment selection dialog
        findViewById(R.id.payment_layout).setVisibility(View.VISIBLE);
        
        // Setup payment buttons
        findViewById(R.id.btn_package_1).setOnClickListener(v -> performPayment(1));
        findViewById(R.id.btn_package_2).setOnClickListener(v -> performPayment(2));
        findViewById(R.id.btn_package_3).setOnClickListener(v -> performPayment(3));
    }
    
    private void performPayment(int packageType) {
        try {
            // Hide payment dialog
            findViewById(R.id.payment_layout).setVisibility(View.GONE);
            
            String sku = "";
            String amount = "";
            String orderInfo = "";
            
            // Configure package details
            switch (packageType) {
                case 1:
                    sku = "com.yourapp.package1";
                    amount = "22000";
                    orderInfo = "Basic Package - 100 Coins";
                    break;
                case 2:
                    sku = "com.yourapp.package2";
                    amount = "44000";
                    orderInfo = "Premium Package - 250 Coins";
                    break;
                case 3:
                    sku = "com.yourapp.package3";
                    amount = "66000";
                    orderInfo = "Deluxe Package - 500 Coins";
                    break;
            }
            
            // Generate unique order ID
            String orderId = "order_" + System.currentTimeMillis();
            String serverId = "server_1";
            String characterId = "character_001";
            String extraInfo = "Demo purchase";
            
            mGosu.payment_iap(
                this,
                currentUserName,
                sku,
                serverId,
                orderId,
                orderInfo,
                amount,
                characterId,
                extraInfo,
                new Gosu.OnPaymentIAPListener() {
                    @Override
                    public void onPaymentSuccessful(String message) {
                        Log.d(TAG, "Payment successful: " + message);
                        mGosu.showConfirmDialog("Success!", message);
                    }

                    @Override
                    public void onPaymentFailed(String message, int errorCode, String iapToken) {
                        Log.e(TAG, "Payment failed: " + message + ", Code: " + errorCode);
                        mGosu.showConfirmDialog("Payment Failed!", message);
                    }
                }
            );
            
        } catch (Exception e) {
            Log.e(TAG, "Error in payment", e);
            Toast.makeText(this, "Payment error occurred", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void toggleFloatingView() {
        try {
            Button btnFloating = findViewById(R.id.btn_floating);
            
            if (FloatingView.instance == null) {
                mGosu.showFloatingView();
                btnFloating.setText("Hide Floating");
            } else {
                mGosu.removeFloatingView();
                btnFloating.setText("Show Floating");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error toggling floating view", e);
        }
    }
    
    private void performTracking() {
        try {
            // Basic tracking events
            GTrackingManager.getInstance().trackingStartTrial();
            GTrackingManager.getInstance().trackingTutorialCompleted();
            GTrackingManager.getInstance().trackingEvent("button_clicked");
            
            // Custom tracking with server/character info
            String serverId = "server_1";
            String roleId = "role_123";
            String roleName = "PlayerName";
            mGosu.gosuTrackingAppOpen(serverId, roleId, roleName);
            
            // Custom event with JSON data
            try {
                JSONObject eventData = new JSONObject();
                eventData.put("action", "feature_used");
                eventData.put("feature_name", "tracking_demo");
                eventData.put("timestamp", System.currentTimeMillis());
                
                GTrackingManager.getInstance().trackingEvent("custom_event", eventData.toString());
            } catch (JSONException e) {
                Log.e(TAG, "Error creating tracking data", e);
            }
            
            Log.d(TAG, "Tracking events sent successfully");
            Toast.makeText(this, "Tracking events sent", Toast.LENGTH_SHORT).show();
            
        } catch (Exception e) {
            Log.e(TAG, "Error in tracking", e);
        }
    }
    
    private void trackLoginEvent(String userId, String userName) {
        try {
            JSONObject loginData = new JSONObject();
            loginData.put("user_id", userId);
            loginData.put("user_name", userName);
            loginData.put("login_time", System.currentTimeMillis());
            
            GTrackingManager.getInstance().trackingEvent("user_login", loginData.toString());
        } catch (JSONException e) {
            Log.e(TAG, "Error tracking login event", e);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GosuSDKConstant.IAP_RQ_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == GosuSDKConstant.FLOATING_RQ_CODE) {
            mGosu.checkResultForFloatingView(requestCode, resultCode, data);
        } else {
            mGosu.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (mGosu != null) {
            mGosu.gosuSDKOnResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGosu != null) {
            mGosu.gosuSDKOnPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGosu != null) {
            mGosu.gosuSDKOnDestroy();
        }
    }
}
```

</details>

## 🏗️ Building & Testing

### Build Commands

| Command | Purpose |
|---------|---------|
| `./gradlew assembleDebug` | Build debug APK |
| `./gradlew assembleRelease` | Build release APK |
| `./gradlew clean build` | Clean and build |
| `./gradlew test` | Run unit tests |
| `./gradlew connectedAndroidTest` | Run instrumented tests |

### Configuration Files Reference

| File | Contents |
|------|----------|
| **google-services.json** | Firebase project credentials, API keys, service configurations |
| **gosu-service.json** | GoSu SDK authentication, service endpoints, feature flags |

> 📞 **Contact GoSu SDK team for proper configuration values**

## 🔍 Troubleshooting

<details>
<summary>🚨 Common Issues & Solutions</summary>

### Build Errors
- **Problem**: Compilation fails with dependency conflicts
- **Solution**: Check dependency tree with `./gradlew dependencies`, clean and rebuild

### Firebase Issues  
- **Problem**: Firebase services not working
- **Solution**: Verify `google-services.json` placement, check Firebase project configuration

### SDK Integration Issues
- **Problem**: GoSu SDK initialization fails  
- **Solution**: Check `gosu-service.json` in assets, verify permissions, check SDK version compatibility

### Authentication Problems
- **Problem**: Social login not working
- **Solution**: Verify platform configurations (SHA-1, Key Hash), test with different accounts

### Billing Problems
- **Problem**: In-app purchases fail
- **Solution**: Test with debug builds first, verify Play Console setup, check product IDs

### Push Notifications Issues
- **Problem**: FCM messages not received
- **Solution**: Verify FCM configuration, check notification permissions, test token registration

### Attribution Tracking Issues
- **Problem**: AppFlyer events not showing
- **Solution**: Verify Dev Key configuration, check network connectivity, ensure proper initialization

- **Problem**: Airbridge attribution not working
- **Solution**: Check App Name and App Token, verify install referrer receiver configuration, test with debug logs enabled

</details>

### Debug Logging

```java
// Enable debug mode (only for debug builds)
if (BuildConfig.DEBUG) {
    Gosu.getSharedInstance().setDebugMode(true);
}
```

### Support Resources

| Resource | Link |
|----------|------|
| **GitHub Issues** | [android-gamesdk/issues](https://github.com/gosusdk/android-gamesdk/issues) |
| **GoSu SDK Support** | Contact GoSu SDK support team |
| **Documentation** | Check INTEGRATION_GUIDE.md |
| **Community** | Join developer discussions |

## 🤝 Contributing

We welcome contributions! See [CONTRIBUTING.md](CONTRIBUTING.md) for detailed guidelines.

### Quick Steps
1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

### Guidelines
- Follow existing code style and conventions
- Add comprehensive comments for complex logic
- Include unit tests for new functionality
- Update documentation for significant changes
- Test on multiple Android versions and devices

---

## 📄 License & Support

**License**: MIT License - See [LICENSE](LICENSE) for details

**Support**: 
- 📧 Contact GoSu SDK support team

**Changelog**: See [CHANGELOG.md](CHANGELOG.md) for version history

> **⚠️ Important**: This is a demo application. For production use, ensure all sensitive credentials are properly secured and not committed to version control.

---

**Made with ❤️ by the GoSu SDK Team**
