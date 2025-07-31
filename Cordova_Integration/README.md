# 📱 Hyperverge KYC – Cordova App Integration

This repository demonstrates how to integrate **Hyperverge’s KYC solution** into a **Cordova mobile application**, with guidance for both the **Web SDK** and the fallback **Link-KYC** flow.

---

## ⚙️ What is a Cordova App?

A **Cordova app** is a hybrid mobile application developed using **HTML, CSS, and JavaScript**, which is then wrapped into a native shell to work on Android and iOS. It allows you to write code once and deploy across platforms with native capabilities like camera, GPS, and microphone using plugins.

---

## 📦 Features of Cordova Apps

* **Hybrid:** Runs inside a WebView but looks and behaves like a native app.
* **Cross-platform:** Write once, deploy to Android, iOS, etc.
* **Native API access:** Use plugins to access hardware features like camera, location, etc.

---

## 🧱 Steps to Install, Build & Run a Cordova App

### 📥 1. Installing Cordova

```bash
npm install -g cordova
cordova --version
```

> ⚠️ Prerequisite: Node.js ≥ 6.0.0

---

### 🏗️ 2. Creating and Building a Cordova App

```bash
cordova create hello com.example.hello HelloWorld
cd hello
cordova platform add android
cordova platform add ios
cordova build
cordova run android
```

> 🧪 To check platform readiness:
> `cordova requirements`

---

### 🧩 3. Adding Plugins

To access device features:

```bash
cordova plugin add cordova-plugin-camera
```

You can browse available plugins at:
🔗 [https://cordova.apache.org/plugins/](https://cordova.apache.org/plugins/)

---

## 🌐 Web SDK Integration in Cordova

Once your Cordova app is created:

1. Navigate to the `www/index.html` file.
2. Treat it as a regular HTML file and follow Hyperverge Web SDK integration steps.
3. Use the script:

```html
<script src="https://hv-camera-web-sg.s3-ap-southeast-1.amazonaws.com/hyperverge-web-sdk@<SDK_VERSION>/src/sdk.min.js"></script>
```

4. Create a config and launch SDK like this:

```js
const config = new window.HyperKycConfig(accessToken, workflowId, transactionId);
window.HyperKYCModule.launch(config, handler);
```

> 📝 Full SDK setup steps are available in the [Web SDK README](https://github.com/SolutionsTeamHV/HyperKYC_Sample_Apps/blob/main/Web_SDK/README.md).

---

## 🛑 Known Issue with Web SDK in Cordova (LIC Case)

### ❌ Problem:

* In a Cordova app (especially on Android/iOS), the **Digilocker** module opens in a new tab via Chrome.
* Chrome throws a **429 Too Many Requests** error.
* Returning to the app keeps it stuck at “waiting” for Digilocker completion.

### 📸 Error Screenshot:

> (Attach the image of 429 error page here)

---

## ✅ Solution: Switch to Link-KYC Integration

To fix the Digilocker redirect issue in Cordova, use **Link-KYC** instead of the default Web SDK.

### 🔁 Steps to Integrate Link-KYC in Cordova

1. In `index.html`, update the `startOnboarding()` function:

```js
window.location.href = "<link-kyc-url>";  // Provided by Hyperverge
```

Comment out or remove any other WebSDK initialization code inside this function.

2. In `config.xml`, add the following under the `<widget>` tag:

```xml
<allow-navigation href="*" />
```

3. In your workflow config, ensure this flag is set:

```js
openAsPopUp: false
```

This prevents the SDK from launching Digilocker in a separate popup.

---

## 📂 Project Folder Structure

```
/hello
│
├── /hooks
├── /platforms
├── /plugins
├── /www
│   └── index.html   <-- Your integration code goes here
├── config.xml
└── package.json
```

---

## 🧪 Debugging Tips

* Open browser DevTools → Console tab for JS errors
* Use [https://jwt.io](https://jwt.io) to decode access tokens and check expiry
* Log internal flow state using:

```js
GlobalWebSDKObject.globals.moduleVariablesMap
```

---

## 🔗 Additional Resources

* 📖 Cordova CLI Docs: [cordova.apache.org/docs/en/12.x/guide/cli](https://cordova.apache.org/docs/en/12.x-2025.01/guide/cli/index.html)
* 📦 Cordova Android Requirements: [link](https://cordova.apache.org/docs/en/12.x-2025.01/guide/platforms/android/index.html#requirements-and-support)
* 📱 Cordova iOS Requirements: [link](https://cordova.apache.org/docs/en/12.x-2025.01/guide/platforms/ios/index.html#requirements-and-support)
