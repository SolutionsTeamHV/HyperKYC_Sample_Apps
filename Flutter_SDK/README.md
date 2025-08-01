## Pre-requisites:

- **Flutter SDK**
    - Version: Any (latest recommended)
    - Installation: https://docs.flutter.dev/get-started/install
- **VS Code or Android Studio**
    - Plugins: Flutter, Dart
- **Node.js** *(Only if using backend services)*
    - Source: https://nodejs.org/en/download
- **HyperVerge Credentials**
    - `appId`, `appKey`, and `workflowId` from the [HyperVerge Dashboard](https://dashboard.hyperverge.co/)

## Integration Steps:

### 1. Create a new Flutter project

```bash
flutter create hyperKYC_demo
cd hyperKYC_demo

```

### 2. Add the SDK dependency

Run the following command:

```bash
flutter pub add hyperkyc_flutter

```

### 3. Android Configuration

In `android/build.gradle`, inside `allprojects > repositories`, add:

```groovy
maven {
    url = "https://s3.ap-south-1.amazonaws.com/hvsdk/android/releases"
}

```

### 4. iOS Configuration

Navigate to the `ios` folder and run:

```bash
cd ios
pod install

```

Then, in your `ios/Runner/Info.plist`, add:

```xml
<key>NSCameraUsageDescription</key>
<string>Access to camera is needed for document and face capture</string>
<key>NSMicrophoneUsageDescription</key>
<string>Granting mic permission allows you to complete video statement</string>

```

### 5. Add correct SDK version :

Go to `your_proj_dir > pubsec.yaml` - under dependencies, add the following change the line of code.

```tsx
hyperkyc_flutter: ^0.38.0 //Insert latest version
```

### 6. Creating HyperKYC instance and launch the SDK :

Go to `your_proj_dir > lib > main.dart` 

1. import the below three packages in `main.dart` :
    
    ```dart
    import 'package:hyperkyc_flutter/hyperkyc_config.dart';
    import 'package:hyperkyc_flutter/hyperkyc_flutter.dart';
    import 'package:hyperkyc_flutter/hyperkyc_result.dart';
    ```
    
2. Inside the MyApp class that extends statelessWidget add the below snippet , This will be the home page of your flutter application : 
    
    ```dart
    @override
      Widget build(BuildContext context) {
        return MaterialApp(
          title: 'Flutter Demo',
          theme: ThemeData(
            colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
            useMaterial3: true,
          ),
          home: const MyHomePage(title: 'Flutter - HyperKYC Home Page'),
        );
      }
    ```
    
    ![image.png](attachment:ad4e3772-6d29-45b0-b60e-16a1ec4af88f:image.png)
    
3. Inside the `_MyhomePage` class , create an instance of the `hyperKYC` class with the `appId`, `appKey`, `workflowId` and `transactionId` as  below
    
    ```dart
    final hyperKycConfig = HyperKycConfig.fromAppIdAppKey(
          appId: "",
          appKey: "",
          workflowId: "",
          transactionId: ""
          );
    ```
    
4. If your flow contains inputs add the inputs as stated below : 
    
    ```dart
    final Map<String, String> customInputs = {
          "key1": "value1",
          "key2": "value2",
        };
    ```
    
5. Launching the SDK and handling results : 
    
    ```dart
    void startKyc() async {
        hyperKycConfig.setInputs(inputs: customInputs); //If your flow contains Inputs
        
        HyperKycResult hyperKycResult =
            await HyperKyc.launch(hyperKycConfig: hyperKycConfig);
        String? status = hyperKycResult.status?.value;
        switch (status) {
          case 'auto_approved':
            // workflow successful
            print('workflow successful - auto approved');
          case 'auto_declined':
            // workflow successful
            print('workflow successful - auto declined');
          case 'needs_review':
            // workflow successful
            print('workflow successful - needs review');
          case 'error':
            // failure
            print('failure');
          case 'user_cancelled':
            // user cancelled
            print('user cancelled');
          default:
            print('contact HyperVerge for more details');
        }
      }
    
    ```
    
6. Now that our SDK backend is complete, add the button that triggers the SDK. In order to do that, add the button widget outside the `void startKyc()` function.
    
    ```dart
     @override
      Widget build(BuildContext context) {
        return Scaffold(
          appBar: AppBar(
            backgroundColor: Theme.of(context).colorScheme.inversePrimary,
            title: Text(widget.title),
          ),
          body: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                TextButton(onPressed: startKyc, child: const Text("Start KYC"))
              ],
            ),
          ), 
        );
      }
    ```
    

### 7.Executing the Application :

Go to the terminal of the application and run the command. Make sure that you are executing this command inside your root directory where your `pubsec.yaml` present.

```tsx
flutter run
```

- **FAQs and common errors**
    1. If you are facing flutter errors that is caused because of a dependency, execute the below command to get it sorted.
        
        ```tsx
        flutter clean
        del /s /q build
        rd /s /q .dart_tool
        ```
        
    2. If you face any unknown error, execute the below command for a detailed explanation and debug tips.
        
        ```tsx
        flutter doctor
        ```
        
    3. If you have added the UI configs but the fonts are not getting reflected in the application.
        
        ```tsx
        hyperKycConfig.setCustomFontStylesheet('https://fonts.googleapis.com/css2?family=Lexend+Deca:wght@100..900&display=swap');
        ```
        
        Download and add the `.ttf` file of all your desired font in the fonts folder of your root directory as below.
        
        ![image.png](attachment:40702cad-4e89-4a54-8ede-447baf150c0a:image.png)
        
    
    <aside>
    💡
    
    It is always a best practice to close all the other vs code , android studio, xcode windows other than the root directory to bypass the unwanted dependencies and conflict errors.
    
    </aside>
    

### 1. **Install CocoaPods**

Run this in your terminal:

```bash

sudo gem install cocoapods

```

> You'll be prompted to enter your Mac password (it's the system password).
> 

---

### 2. **Verify Installation**

After installation, check that it works:

```bash

pod --version
```

You should see a version number (e.g., `1.15.2`).

---

### 3. **(Optional) Add CocoaPods to PATH**

If `pod` still isn’t recognized after install, your shell might not be sourcing Ruby gems correctly.

Add this line to your shell profile (based on your shell):

### If you're using **zsh** (default on macOS Catalina+):

```bash

echo 'export PATH="/opt/homebrew/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

```

### If you're using **bash**:

```bash

echo 'export PATH="/opt/homebrew/bin:$PATH"' >> ~/.bash_profile
source ~/.bash_profile

```

Then try:

```bash

pod --version

```

---

### 4. **Run Pod Install**

Once CocoaPods is installed, go back to your iOS folder and run:

```bash

cd ios
pod install

```