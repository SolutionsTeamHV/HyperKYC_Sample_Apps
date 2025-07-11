import React, {useState} from 'react';
import {NativeModules} from 'react-native';
const {Hyperkyc} = NativeModules;
import {Button, StyleSheet, Text} from 'react-native';

function startKYC(): void {

  // <- prefetch configs ->
  //Hyperkyc.prefetch('<appId>', '<workflowId>');
  console.log('Launched');
  let configDictionary: {[name: string]: any} = {};
  configDictionary.appId = '<appId>';
  configDictionary.appKey = '<appKey>';
  configDictionary.transactionId = '<transaction ID>';
  configDictionary.workflowId = '<workflow ID>';
  
  // <- set unique ID ->
  configDictionary.uniqueId = '<UUID>';

  let inputsDictionary: {[name: string]: string} = {};

  // <- set workflow Inputs ->
  configDictionary.inputs = inputsDictionary;
  //inputsDictionary.enrolTransactionId ='Akshaya';
  //inputsDictionary.ifa_arn='CHANDRA KANTH ERUKULLA';

  console.log(configDictionary);
  
  //Hyperkyc.prefetch('9rdcrc', 'test_flow');
  
  Hyperkyc.launch(configDictionary, function (response: any) {
    console.log(response);
    switch (response.status) {
      case 'auto_approved':
      case 'auto_declined':
      case 'needs_review':
        // Handle Success Workflow
        break;
      case 'user_cancelled':
        // Handle User Cancelled
        break;
      case 'error':
        // Handle Failure Scenario
        break;
    }
  });
}
function App(): JSX.Element {
  return (
    <>
      <Button title="Start KYC" onPress={startKYC} />
    </>
  );
}
export default App;
