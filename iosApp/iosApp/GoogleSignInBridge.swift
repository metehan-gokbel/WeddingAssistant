import Foundation
import UIKit
import GoogleSignIn
import FirebaseCore
import ComposeApp

final class GoogleSignInBridge {

    static func signIn() {
        guard let clientID = resolveClientID() else {
            GoogleBridgeKt.publishGoogleError(message: "Google clientID missing. Check GoogleService-Info.plist CLIENT_ID.")
            return
        }

        GIDSignIn.sharedInstance.configuration = GIDConfiguration(clientID: clientID)

        guard let vc = topViewController() else {
            print("No top VC")
            return
        }

        GIDSignIn.sharedInstance.signIn(withPresenting: vc) { result, error in
            if let error = error {
                GoogleBridgeKt.publishGoogleError(message: error.localizedDescription)
                return
            }

            guard let idToken = result?.user.idToken?.tokenString else {
                GoogleBridgeKt.publishGoogleError(message: "Google idToken null geldi")
                return
            }

            let accessToken = result?.user.accessToken.tokenString
            GoogleBridgeKt.publishGoogleToken(idToken: idToken, accessToken: accessToken)
        }
    }

    private static func resolveClientID() -> String? {
        if let id = FirebaseApp.app()?.options.clientID, !id.isEmpty {
            return id
        }
        if let id = Bundle.main.object(forInfoDictionaryKey: "GIDClientID") as? String, !id.isEmpty {
            return id
        }
        if let path = Bundle.main.path(forResource: "GoogleService-Info", ofType: "plist"),
           let dict = NSDictionary(contentsOfFile: path),
           let id = dict["CLIENT_ID"] as? String,
           !id.isEmpty {
            return id
        }
        return nil
    }

    private static func topViewController() -> UIViewController? {
        let scenes = UIApplication.shared.connectedScenes.compactMap { $0 as? UIWindowScene }
        let window = scenes.flatMap { $0.windows }.first { $0.isKeyWindow }
        var vc = window?.rootViewController
        while let presented = vc?.presentedViewController { vc = presented }
        return vc
    }
}
