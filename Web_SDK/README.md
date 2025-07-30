### WEB SDK INTEGRATION

### Pre-requisites :

- VS code editor
    - Version : Any
    - Plugins : HTML5 and JS plugins (recommended)
- Node js (To run the SDK in your local server)
    - Source : https://nodejs.org/en/download

### Integration steps :

1. Create a new project in your code editor and start with a basic HTML5 template.

```jsx
<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body>
    <h1>Basic page </h1>
</body>
</html>
```

1. Add a script tag with the SDK link into the headers tag.

```jsx
<script src="https://hv-camera-web-sg.s3-ap-southeast-1.amazonaws.com/hyperverge-web-sdk@<INSERT_LATEST_VERSION_HERE>/src/sdk.min.js"></script>
```

1. If you want to add prefetch, Add the below snippet in the `<body>` 

```jsx
window.HyperKYCModule.prefetch(
appId: "<app-id>",          
workflowId: "<workflow-id>" 
)
```

<aside>
💡

What is a prefetch?

While launching the SDK, The necessary assets will be loaded before the workflow actually starts. This makes the loading time a bit longer. To avoid this, we are adding the prefetch functionality so that all the necessary resources are fetched before the flow even starts.

</aside>

1. Once the above steps are done, Create an instance of `HyperKycConfig` class with the parameters `accessToken` , `workflowId` and `transactionId`.
    
    ```jsx
    const hyperKycConfig = new window.HyperKycConfig(accessToken, workflowId, transactionId);
    ```
    
    1. To generate accessToken, Execute the below cURL with the required fields.
    
    ```jsx
    curl --location 'https://auth.hyperverge.co/login' \
    --header 'Content-Type: application/json' \
    --data '{
    "appId" : "",//Your appId
    "appKey" : "",//Your appKey
    "expiry" : 3600 //seconds
    }'
    ```
    
    b. The response of the login API would be in the below format.
    
    ```json
    {
        "status": "success",
        "statusCode": "200",
        "result": {
            "token": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6Ino0dnMwbiIsImhhc2giOiJhMzVhODUwYjhmOTQzOTU0NmNiYjdlNDkzM2FhM2U3NzQ3YTE0ZDZhZjY4N2I2ZTFkYmZjMzc1YzQ3NWYzODZlIiwiaWF0IjoxNzQ3NjU1MjMxLCJleHAiOjE3NDc2NTg4MzEsImp0aSI6IjQ1Y2QzZGYwLWUyOTEtNDBlKuBeRaN-idmWas1OHEREI1YyJ9.Y-eaReTpKnwGvA72qK8F5oJctm7BavZhOuUa5iTrwlz7s8SlYYUIY4YPahC1UKnfrVaeFDyH_ReTk3ZMddFgW2zC5uoNC6-EJgoUJwVwTsSjs8b6lZ2eXTKY"
        }
    }
    ```
    
    c. Paste the token in the accessToken field of the object initialization and fill the workflowId as the second parameter.
    
2. Transaction Id needs to be unique every time the SDK is being initialized.
3. Once all the details are filled , we need to handle the results that the SDK is going to return.
    
    ```html
    const handler = (HyperKycResult) => {
    switch (HyperKycResult.status) {
    // ----Incomplete workflow-----
    
    case "user_cancelled":
        // <<Insert Your response for user_cancelled>>
        break;
    case "error":
        // <<you can just console log the result>>
        break;
    
        // ----Complete workflow-----
    
    case "auto_approved":
        // <<Or you can just print the respective status>>
        break;
    case "auto_declined":
        // <<Or you can just keep it empty>>
        break;
        break;
    case "needs_review":
        //<<Insert Or you can navigate to success or failure screen based on the result>>
        break;
     }
    }
    ```
    
4. Now that everything is set up, we can launch the SDK.
    
    ```jsx
    window.HyperKYCModule.launch(hyperKycConfig, handler);
    ```
    
5. If the workflow contains any inputs, we need to pass them once we launch the SDK. **[optional]**
    
    ```jsx
    hyperKycConfig.setInputs({
      "Input1":""
      }) 
    ```
    
6. If the workflow contains different text configs(languages),make sure to add the below line to implement the particular language in which the SDK needs to be initiated. **[optional]**
    
    ```jsx
    hyperKycConfig.setDefaultLangCode("en"); //Same language code that has been used on the workflow
    ```
    

10. If your flow contains CPR , you would need to add uniqueId to the flow **[optional]**

1. UUID creation : 
    
    ```jsx
    Hyperkyc.createUniqueId(uniqueId =>{
    console.log(uniqueId)
    configDictionary["uniqueId"] = uniqueId;})  
    ```
    

ii. Passing the UUID to the flow : 

```jsx
 hyperKycConfig.setUniqueId(<generated uniqueId from above step>);
```

### Execution :

1. Open the terminal of the folder where the SDK is present.
2. Type in node to ensure that node.js has been installed properly.
3. Type `http-server` in the terminal and you will get the local host URLs like below
    
    ![image.png](attachment:c4de5f6c-ffa9-4130-b1f4-49428c7e620a:26311120-9af1-40ff-9ea4-326ad9855ddf.png)
    
4. Open any of the link in your browser and you will be able to see your .html file
5. Click on your HTML file and you will be good to go!

## Possible issues and debug tips :

1. **Initial debug :**
If anything did not go as expected ,
    1. Before the flow even starts : 
        
        Inspect the page and go to the console tag. Mostly the error will be directly thrown in the screen.
        
        If you got the error 403 access denied , it would either be because of : 
        
        1. Wrong workflow id might be passed or the flow might not be pushed to the respective appId.
        2. Kindly ensure the SDK version given on the SDK link while initiaing.
        3. Access token might have been expired. you can paste it in : https://jwt.io/ 
            
            Paste the access token without the word Bearer, you will get the expiry details on the sidebar, hover over the exp object to find out the expiry time of your token.
            
            ![image.png](attachment:87f1e9f1-9fbb-42ab-bb9b-336a58a5921d:7c4cdb15-75d2-401d-9b0d-015d2e30a030.png)
            
2. If the flow is started and there is some issue during the flow, Go to console tab and type the below command.
    
    ```java
    GlobalWebSDKObject.globals.moduleVariablesMap
    ```
    
    This would give you the module by module output throughout the flow. You can look at the modules’ output and debug the flow.