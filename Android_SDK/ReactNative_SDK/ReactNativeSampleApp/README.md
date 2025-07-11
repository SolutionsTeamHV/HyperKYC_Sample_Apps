# Step 1: Set Up Your Environment

- Install a Code Editor
    - Go to https://code.visualstudio.com/ and install **Visual Studio Code**.
- Install Node.js & npm
    - Download & install: https://nodejs.org/
    - Verify the installation:
        
        ```jsx
        node -v
        npm -v
        ```
        
- Install **Expo CLI**
    - Open your terminal, run the below command
        
        ```jsx
        npm install -g expo-cli
        ```
        
- Install Xcode (for iOS development)
    - Install via the Mac App Store.

# Step 2: Create a Project

- Open Terminal, Create a new react native project using Expo
    
    ```jsx
    npx create-expo-app MyFirstApp
    ```
    

# Step 3: Integrate HyperKYC React Native SDK

- Install dependencies
- In the project root directory, run:
    
    ```jsx
    npm install
    ```
    
- Install HyperKYC SDK
    
    ```jsx
    npm install react-native-hyperkyc-sdk
    ```
    

# Step 4: Integrate HyperKYC React Native SDK

1. **Create a config instance**

```jsx
import { NativeModules } from 'react-native';
const { Hyperkyc } = NativeModules

const configDictionary = {};
configDictionary["appId"] = {{appId}}
configDictionary["appKey"] = {{appKey}}
configDictionary["transactionId"] = "<transactionId>"
configDictionary["workflowId"] = "<workflow-id>"
```

1. Optional Configurations
    - Workflow Inputs (Optional)
        
        ```jsx
         configDictiolet inputsDictionary: {[name: string]: string} = {};
          configDictionary.inputs = inputsDictionary;
          inputsDictionary.name ='Name';
          inputsDictionary.gender ='female';
        ```
        
    - Unique ID (Optional)
        
        ```jsx
        Hyperkyc.createUniqueId(uniqueId) =>{
           console.log(uniqueId)
        	 configDictionary["uniqueId"] = uniqueId;  
        }
        //sample - configDictionary.uniqueId = '5a93aad4-65ce-43df-8d8b-179c3c469d85';
        ```
        
    - prefetch (Optional)
        
        ```jsx
        Hyperkyc.prefetch('{{appId}}', '{{workflowId}}');
        ```
        
    - **Set Use Location** (Optional)
        
        ```jsx
        configDictionary["useLocation"] = <true/false>
        //sample - configDictionary["useLocation"] = true
        ```
        
    - Set Default Language Code (Optional)
        
        ```jsx
        configDictionary["defaultLangCode"] = "<the_alpha_2_language_code>" 
        //sample - configDictionary["defaultLangCode"] = "en" 
        ```
        
2. **IImplement a Result Handler**
    
    ```jsx
    let completionHandler :(_ hyperKycResult: HyperKycResult)->Void = {
        hyperKycResult in
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
    
3. **Launch the SDK**
    
    ```jsx
    HyperKyc.launch(self, hyperKycConfig: config, completionHandler)
    ```
    

# Step 5: Build and Run the App

- Connect a device
    - Physical Device
        - **Steps to connect device wirelessly when both devices are in same network:**
            - Connect Your Device via USB
            - **Enable ADB Over TCP/IP**:
                
                ```bash
                adb tcpip 5555
                ```
                
            - **Verify the Device is Listening**:
            
            ```jsx
            adb devices
            ```
            
            - Disconnect USB Cable
            - **Connect Over Wi-Fi**:
                
                ```jsx
                adb connect 192.168.1.107:5555
                // 192.168.1.107 is the IP of the device
                ```
                
    - Virtual Device