# Android SDK Guide

## Step 1: Set Up Your Environment

Requirements

- **Android Studio**
    - **Download from** https://developer.android.com/studio if you don’t have in your system.

## **Step 2:** Create a new Project

- **Open Android Studio**
- Click on **"New Project"**
- Select **"Empty Activity" and c**lick “Next”
- A form will pop up. Fill in the project details:
    - Name you Project
    - Select the location where you need to save the project
    - Select Build Configuration Language: **Kotlin (Recommended)**
    - Select Minimum SDK: **API 21 (Android 5.0)** or higher
- Click **Finish to create the project.**

## Step 3: Add HyperKYC Android SDK to Your App

### 3.1.  **Add the Maven Repository**

- Add the HyperVerge private Maven repository in your **project-level** `settings.gradle`
    
    
    ```kotlin
    dependencyResolutionManagement {
        ...
        repositories {
            google()
            mavenCentral()
            maven {
                url "https://s3.ap-south-1.amazonaws.com/hvsdk/android/releases"
            }
        }
    }
    ```
    
- Then click “Sync Now” button at the top of the screen to sync the project.

### 3.2.  **Add SDK Dependency**

- Add the SDK dependency with the latest version to your app-level `build.gradle` file:

    
    ```kotlin
    dependencies{
    	   implementation("co.hyperverge:hyperkyc:<latest_version>")
    }
    ```
    
- Click “Sync Now” button at the top of the screen to update your dependencies.

## Step 4: Integrate HyperKYC SDK

### **4.1.  Create HyperKycConfig Instance**

Choose one of the methods:

- Method 1: **Quicker Method - Using App ID & App Key**
    
    ```jsx
    val hyperKycConfig = HyperKycConfig(
    	"<appId>", 
    	"<appKey>", 
    	"<workflowId>",
    	"<transactionId>"
    )
    ```
    
- Method 2: **More Secure Method - Using Access Token**
    
    ```jsx
    
    val hyperKycConfig = HyperKycConfig(
        "<accessToken>",
        "<workflowId>",
    	  "<transactionId>"
    )
    ```
    

### 4.2.  Add additional functionality if needed (Optional)

- 🔠 Set Workflow Inputs (Optional)
    
    ```jsx
    var inputs = HashMap<String, String>()
    inputs.put("key1","value1")
    inputs.put("key2","value2")
    hyperKycConfig.setInputs(inputs)
    ```
    
- 🆔 Set Unique ID for Server Side Resume (Optional)
    
    ```jsx
    varl uniqueId = HyperKyc.createUniqueId();
    hyperKycConfig.setUniqueId(uniqueId)
    
    //sample - hyperKycConfig.setUniqueId(<UUID>)
    ```
    
- 🚀 Prefetch SDK (Optional)
    
    ```jsx
    HyperKyc.prefetch( <activity-context>, "<app-id>", "<workflow-id>" )
    ```
    
- 📍 Use Location (Optional)
    - Add the below config
        
        ```kotlin
        hyperKycConfig.setUseLocation(true/false)
        
        //sample - hyperKycConfig.setUseLocation(true)
        ```
        
    - Add Permissions to  `AndroidManifest.xml` file
        
        ```kotlin
        <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
        ```
        
    - Handle Permissions in app
        
        ```kotlin
        private val PERMISSION_REQUEST_CODE = 100
        
        private fun checkAndRequestPermissions() {
            val requiredPermissions = mutableListOf<String>()
        
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requiredPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        
            if (requiredPermissions.isNotEmpty()) {
                ActivityCompat.requestPermissions(this, requiredPermissions.toTypedArray(), PERMISSION_REQUEST_CODE)
            }
        }
        override fun onRequestPermissionsResult(
                requestCode: Int,
                permissions: Array<out String>,
                grantResults: IntArray
            ) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
                if (requestCode == PERMISSION_REQUEST_CODE) {
                    permissions.forEachIndexed { index, permission ->
                        if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                            Log.d("Permission", "$permission granted")
                        } else {
                            Log.d("Permission", "$permission denied")
                        }
                    }
                }
            }
        ```
        
        - Call the method `checkAndRequestPermissions` to ask user permission once the application is opened
            
            ```kotlin
            checkAndRequestPermissions()
            ```
            
- 🌐 Set Default Language (Optional)
    
    ```jsx
    hyperKycConfig.setDefaultLangCode("en/vi")
    
    //sample - hyperKycConfig.setDefaultLangCode("en")
    ```
    

### **4.3. Implement a Results Handler**

```kotlin
val hyperKycLauncher = registerForActivityResult(HyperKyc.Contract()) { 
result ->
    // handle result post workflow finish/exit
    when (result.status) {
        HyperKycStatus.USER_CANCELLED-> {
            // user cancelled
        }
        HyperKyc.Status.ERROR -> {
            // failure
        }
        HyperKycStatus.AUTO_APPROVED,
        HyperKycStatus.AUTO_DECLINED,
        HyperKycStatus.NEEDS_REVIEW -> {
            // workflow successful
        }
    }
}
```

### **4.4.  Launch the SDK**

```kotlin
  hyperKycLauncher.launch(hyperKycConfig)
```

# Step 5: Connect your device

### Physical device

- **Connect a physical device** via USB
- E**nable Developer Mode + USB Debugging**
- Click **Run ▶️** in Android Studio or press `Shift + F10`

### Virtual Device

- Create or select an emulator.
- Click **Run ▶️** or press `Shift + F10`.