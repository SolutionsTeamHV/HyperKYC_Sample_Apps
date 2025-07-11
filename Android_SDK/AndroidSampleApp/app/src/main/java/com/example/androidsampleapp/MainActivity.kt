package com.example.androidsampleapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import co.hyperverge.hyperkyc.HyperKyc
import co.hyperverge.hyperkyc.data.models.HyperKycConfig
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import co.hyperverge.hyperkyc.data.models.result.HyperKycStatus
import com.example.androidsampleapp.ui.theme.AndroidSampleAppTheme

class MainActivity : ComponentActivity() {
    private val PERMISSION_REQUEST_CODE = 100

    private fun checkAndRequestPermissions() {
        val requiredPermissions = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requiredPermissions.add(Manifest.permission.CAMERA)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requiredPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (requiredPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, requiredPermissions.toTypedArray(), PERMISSION_REQUEST_CODE)
        }
    }
`

    val hyperKycConfig = HyperKycConfig(
        "<appId>",
        "<appKey>",
        "<workflow ID>",
        "<transaction ID>"
    )
    val hyperKycLauncher = registerForActivityResult(HyperKyc.Contract()) {
            result ->
        // handle result post workflow finish/exit
        when (result.status) {
            HyperKycStatus.USER_CANCELLED-> {
                // user cancelled
            }
            HyperKycStatus.ERROR -> {
                // failure
            }
            HyperKycStatus.AUTO_APPROVED,
            HyperKycStatus.AUTO_DECLINED,
            HyperKycStatus.NEEDS_REVIEW -> {
                // workflow successful
            }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestPermissions()

        HyperKyc.prefetch( context = this , "9rdcrc", "document_flow" )
        setContent {
            Button(onClick = {
                try {
                    //<-   Workflow Inputs  ->
//                    var inputs = HashMap<String, String>()
//                    inputs.put("id_type","pan")
//                    hyperKycConfig.setInputs(inputs)

                    //<-  To set uniqueId  ->
                    // val uniqueId = HyperKyc.createUniqueId();
                    // hyperKycConfig.setUniqueId(uniqueId)


                    //<-  To set default Language ->
                    //hyperKycConfig.setDefaultLangCode("en")

                    //<-  To use Location ->
                    hyperKycConfig.setUseLocation(false)


                    hyperKycLauncher.launch(hyperKycConfig)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
            {
                Text(text="Start KYC")
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidSampleAppTheme {
        Greeting("Android")
    }
}