import Foundation
import UIKit
import GoogleSignIn
import FirebaseCore

final class GoogleSignInBridge {

    static func signIn() {
        guard let clientID = FirebaseApp.app()?.options.clientID else {
            print("Firebase clientID missing")
            return
        }

        GIDSignIn.sharedInstance.configuration = GIDConfiguration(clientID: clientID)

        guard let vc = topViewController() else {
            print("No top VC")
            return
        }

        GIDSignIn.sharedInstance.signIn(withPresenting: vc) { result, error in
            if let error = error {
                print("Google sign-in error: \(error.localizedDescription)")
                return
            }

            guard let idToken = result?.user.idToken?.tokenString else {
                print("idToken null")
                return
            }

            let accessToken = result?.user.accessToken.tokenString
            print("Google OK idToken: \(idToken.prefix(10))... accessToken: \(accessToken?.prefix(10) ?? "")")

            // Buradan sonra Kotlin repo’na login yaptıracağız (sonraki adım)
        }
    }

    private static func topViewController() -> UIViewController? {
        let scenes = UIApplication.shared.connectedScenes.compactMap { $0 as? UIWindowScene }
        let window = scenes.flatMap { $0.windows }.first { $0.isKeyWindow }
        var vc = window?.rootViewController
        while let presented = vc?.presentedViewController { vc = presented }
        return vc
    }
}