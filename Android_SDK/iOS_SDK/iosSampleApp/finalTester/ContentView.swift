//
//  ContentView.swift
//  finalTester
//

import SwiftUI
import HyperKYC

struct ContentView: View {
    var body: some View {
        VStack(spacing: 20) {
            Text("Welcome to HyperKYC")
                .font(.largeTitle)
                .fontWeight(.bold)
                .padding()

            MyView()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color(.systemGray6)) // Light gray background
    }
}

class MyViewController: UIViewController {
    @objc func startKyc() {
        print("Pressed Start KYC")

        let config = HyperKycConfig(
            appId: "<appId>",
            appKey: "<appKey>",
            workflowId: "<workflowId>",
            transactionId: "<transactionId>"
        )
        
        
//        <-  Workflow Input ->
//        let customInputs : [String : String] = [
//            "name" : "UserName",
//        ]
//        config.setInputs(inputs: customInputs)

        
//        <-  To set uniqueId ->
//        const uniqueId = HyperKyc.createUniqueId();
//        hyperKycConfig.setUniqueId(uniqueId)

        
//        <-  To set default Language ->
//        hyperKycConfig.setDefaultLangCode("en")
        
//        <-  To access location ->
//        hyperKycConfig.setUseLocation(true)
        
        

        let completionHandler: (_ hyperKycResult: HyperKycResult) -> Void = { hyperKycResult in
            switch hyperKycResult.status {
            case "auto_approved":
                print("Workflow successful - Auto Approved")
            case "auto_declined":
                print("Workflow successful - Auto Declined")
            case "needs_review":
                print("Workflow successful - Needs Review")
            case "error":
                print("Failure:", hyperKycResult)
            case "user_cancelled":
                print("User Cancelled")
            default:
                print("Contact HyperVerge for more details")
            }
        }

        HyperKyc.launch(self, hyperKycConfig: config, completionHandler)
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .white

        let startButton = UIButton(type: .system)
        startButton.setTitle("Start KYC", for: .normal)
        startButton.setTitleColor(.white, for: .normal)
        startButton.backgroundColor = .blue
        startButton.layer.cornerRadius = 10
        startButton.frame = CGRect(x: 50, y: 200, width: 250, height: 50)
        startButton.addTarget(self, action: #selector(startKyc), for: .touchUpInside)

        view.addSubview(startButton)
    }
}

struct MyView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> MyViewController {
        return MyViewController()
    }

    func updateUIViewController(_ uiViewController: MyViewController, context: Context) {}

    typealias UIViewControllerType = MyViewController
}
