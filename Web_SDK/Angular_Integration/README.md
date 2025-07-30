## Pre requisites :

- VS code editor
    - Version : Any
    - Plugins : HTML5 and JS plugins (recommended)
- Node js (To run the SDK in your local server)
    - Source : https://nodejs.org/en/download
- Angular framework
    - Source : https://angular.dev/installation

## Integration steps :

1. Create a new angular project in the CLI using the below command and navigate to your directory.
    
    ```java
    ng new hyperKYC-angular
    cd hyperKYC-angular
    ```
    
2. Add the SDK Script
    
    Go to your directory and include the SDK script in `src > index.html` under the `head` tag:
    
    ```json
    <script src="https://hv-camera-web-sg.s3-ap-southeast-1.amazonaws.com/hyperverge-web-sdk@<Insert_latest_version>/src/sdk.min.js">
    </script>// Make sure to insert the latest SDK version in the link.
    
    ```
    
3. In `src/app/app.component.html` you will find a preset HTML code. under <main class = “main”>, under <div class = “content”>. Create a button to launch the SDK.
    
    ```json
    <h1>Angular Integration</h1>
    <button (click)="launchSDK()">Start KYC</button>
    <h1 id="KYCresult"></h1> //The results go here once the SDK is executed successfully.
    ```
    
4. For the backend go to `src/src/app.component.ts` , which look like below
    
    ![image.png](attachment:2762b73e-e94e-4bef-ab50-3ee7d564b68e:image.png)
    
5. Add the action function inside the `AppComponent` class.
    1. Generate Access token:
        
        Execute the below cURL to generate the access token.
        
        ```json
        curl --location 'https://auth.hyperverge.co/login' \
        --header 'Content-Type: application/json' \
        --data '{
        "appId" : "",//Your appId
        "appKey" : "",//Your appKey
        "expiry" : 3600 //seconds
        }'
        ```
        
    2. Launching the SDK :
        
        ```tsx
        launchSDK() {
            
            const accessToken = "";//Enter Access Token here
            const workflowId = "";//Enter workflow ID here
            const transactionId = ""; //Enter transaction Id here
            
            //Launching the SDK
            const hyperKycConfig = new window.HyperKycConfig(accessToken, workflowId, transactionId);
            
            this.initSDK(hyperKycConfig);
          }
        ```
        
        1. **Adding prefetch:**
        
        Before launching the SDK, add the below snippet to add snippet.
        
        ```tsx
        window.HyperKYCModule.prefetch({
              appId: "",
              workflowId: ""
            });
        ```
        
        **b. Setting Inputs :** 
        
        If the workflow contains Inputs, Add the Input objects in the below format after launching the SDK.
        
        ```tsx
        hyperKycConfig.setInputs({
              Input1: "value"
            });
        ```
        
        **c. Selecting default language code :**
        
        ```tsx
        hyperKycConfig.setDefaultLangCode("en");
        ```
        
        **d. Setting UUID :**
        
        If the flow contains UUID, add the below line to set the unique Id for the transaction.
        
        ```tsx
        [hyperKycConfig.setUniqueId](https://hyperkycconfig.setuniqueid/)("");
        ```
        
        **e. Adding location permission :** 
        
        ```tsx
        hyperKycConfig.setUseLocation({useLocation: "true"})
        ```
        
    3. In the above snippet, initSDK() is a method where the SDK results are getting handled give the function definition inside the same class as given below
        
        ```tsx
        private initSDK(config: any) {
          const handler = (HyperKycResult: any) => {
            let resultText = "";
        
            switch (HyperKycResult.status) {
              case "user_cancelled":
                resultText = "User cancelled";
                break;
              case "error":
                resultText = `Error: ${JSON.stringify(HyperKycResult)}`;
                break;
              case "auto_approved":
                resultText = "auto_approved";
                break;
              case "auto_declined":
                resultText = "auto_declined";
                break;
              case "needs_review":
                resultText = "needs_review";
                break;
              default:
                resultText = `Unhandled status: ${HyperKycResult.status}`;
            }
        
            const resultElement = document.getElementById("KYCresult");
            if (resultElement) {
              resultElement.innerText = resultText;
            } else {
              console.warn("KYCresult element not found in DOM.");
            }
          };
        
          window.HyperKYCModule.launch(config, handler);
        }
        ```
        
6. **Executing the SDK :** 
    1. Goto your file location and enter the command `ng serve` . Upon successful generation.
    
    ![image.png](attachment:b37f3b09-7a05-4b92-9b6f-de5601a660fc:image.png)
    
    ii. Open the local host 4200 and you should see the below screen.
    
    ![image.png](attachment:8fce52e3-dea6-4f67-bfa7-5e6b688eacb3:image.png)
    
    Click on the button to launch the SDK.
    

- **FAQs**
    1. If the hyperkycconfig is not working or shown in red, add the below snippet on the first line of **src > app > app.component.ts** and serve again
        
        ```tsx
        declare global {
          interface Window {
            hyperKYCModule: any;
            HyperKYCModule: any;
            HyperKycConfig: any;
          }
        }
        ```
        
    2. What are the files that we need to edit and what not to edit?
        
        **A :  Once we add the script in the `src > index.html` head tag. No more edits required in that file. `src > app > app.component.html` is used only for the front end text and the buttons. `src > app > app.component.ts` is the most important one used for the backend code.**
        
    
    **If anything breaks or fails after a successful compilation and getting [localhost](http://localhost):4200 there is some problem with the `appComponent.ts` code**
    
    1. I have added the script in the `src > index.html` but the script is not getting imported when I tried to launch the SDK. What could be the reason ? 
        
        **A : If the SDK is not getting imported even after giving correct SDK version, then this might be a SSR<Server Side Rendering>/hydration issue To solve this issue add the link to the scripts array present at the end of `your_project_dir > angular.json`** 
        
        ![image.png](attachment:9e529203-dfa2-4aff-b3fe-ab7cc17a5d66:image.png)
        
    2. Where do I make the changes to make the UI look better?
        
        **Method 1 :** 
        
        **If you make the changes in `src > appcomponent.css` and go to `src > appcomponent.ts` to import the changes.**
        
        ```tsx
        styleUrls: ['./app.component.css']
        ```
        
        **Method 2 :**
        
        **Make changes in `src > app.component.css` and go to `your_project_dir > angular.json` and import the changes in the following way.**
        
        ```tsx
        "styles": [
        "src/app.component.css"
        ]
        ```
        
        <aside>
        💡
        
        If you are using Method 1 or 2 make sure to delete the existing predefined styles present in app.components.html file
        
        </aside>
        
        **Method 3 :**
        
        **Delete all the predefined <style> present in `app.components.html` and define your desired styles directly inside the style tag.**
        
        <aside>
        💡
        
        TIP :
        
        In the body tag of app.components.html delete all the sidebar divs and footer divs those are just angular documentations and angular learning videos. We can remove those to make the UI better.
        
        </aside>