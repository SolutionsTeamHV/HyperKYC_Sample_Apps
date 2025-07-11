# Step 1: Set Up Your Environment

- **Mac**: iOS apps require macOS. (IOS can’t be done in other OS like Windows)
- **Xcode**:
    - [Download](https://apps.apple.com/us/app/xcode/id497799835?mt=12) from the Mac App Store.
    - **Sign in to Xcode with your Apple ID.**

# Step 2: Create a Project

- Open **Xcode** →  "Create a new Xcode project"
- Choose **App** under iOS tab
- Fill the following details:
    - **Project Name:** Name of your IOS Application
    - **Team:** your Apple ID
    - **Language**: Swift
    - **Interface**: SwiftUI
- Click **Next**, choose location, and **Create**

# Step 3: Set up your pod file

- Open Terminal, go to your project directory.
- Install CocoaPods,
    
    ```jsx
    sudo gem install cocoapods
    ```
    
- Initialize Pods in Your Xcode Project
    
    ```jsx
    pod init
    ```
    
- Add HyperKYC SDK into your pod file
    - Open the Podfile
    - Edit the pod file
    
    ```jsx
    target '<Your App Name>' do
      use_frameworks!
      pod 'HyperKYC', '~> 0.45.1'
    end
    ```
    
- Install Pods
    
    ```jsx
    pod install
    ```
    

# Step 4: Integrate HyperKYC IOS SDK

1. **Create a config instance**
    - Method 1: **Quicker Method**
        
        ```jsx
        let hyperKycConfig = HyperKycConfig(
        	appId: "<Enter_the_application_ID>",
        	appKey: "<Enter_the_application_key>",
        	workFlowId: "<Enter_the_workflow_ID>", 
        	transactionId: "<Enter_the_transaction_ID>"
        )
        ```
        
    - Method 2: **More Secure Method**
        
        ```jsx
        let hyperKycConfig = HyperKycConfig(
        	accessToken: "<Enter_the_access_token>",
        	workFlowId: "<Enter_the_workflow_ID>", 
        	transactionId: "<Enter_the_transaction_ID>"
        )
        ```
        
2. Add additional functionality if needed (Optional)
    - Workflow Inputs (Optional)
        
        ```jsx
        let customInputs : [String : String] = [
        					"key1" : "value1", 
        					"key2" : "value2",
        					...]
        hyperKycConfig.setInputs(inputs: customInputs)
        ```
        
    - Unique ID (Optional)
        
        ```jsx
        const uniqueId = HyperKyc.createUniqueId(); 
        hyperKycConfig.setUniqueId(uniqueId)
        ```
        
    - prefetch (Optional)
        
        ```jsx
        HyperKyc.prefetch( "<app-id>", "<workflow-id>")
        ```
        
    - **Set Use Location** (Optional)
        
        ```jsx
        hyperKycConfig.setUseLocation(useLocation: <true/false>)
        ```
        
    - Set Default Language Code (Optional)
        
        ```jsx
        hyperKycConfig.setDefaultLangCode(language: defaultLangCode)
        
        //sample - hyperKycConfig.setDefaultLangCode(language: en)
        ```
        
3. **Implement a results handler**
    
    ```jsx
    let completionHandler :(_ hyperKycResult: HyperKycResult)->Void = {
        hyperKycResult in
    //  Please refer to 'Understanding SDK Response' page for more info
    		let status = hyperKycResult.status
        switch status {
        case "auto_approved":
    				// workflow successful
            print("workflow successful - auto approved")
        case "auto_declined":
    				// workflow successful
            print("workflow successful - auto declined")
        case "needs_review":
    				// workflow successful
            print("workflow successful - needs review")
        case "error":
    				// failure
            print("failure")
        case "user_cancelled":
    				// user cancelled
            print("user cancelled")
        default :
            print("contact HyperVerge for more details")
        }
    }
    ```
    
4. **Launch the SDK**
    
    ```jsx
    HyperKyc.launch(self, hyperKycConfig: config, completionHandler)
    ```
    

# Step 5: Build and Run the App

- Open the file - <YourAppName>.xcworkspace.
    
    ```jsx
    open <YourAppName>.xcworkspace
    ```
    
- Select the target device
    - Physical device
        - Select the physical device from the device list in the top panel.
    - Virtual Device - Stimulator
        - Select a virtual device from the device list in the top panel.
- Build your project using `Cmd + B` or click **Run** ▶️
- Once build is successful, the app will be launched in the device.