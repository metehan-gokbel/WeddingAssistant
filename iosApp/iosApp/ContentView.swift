import SwiftUI
import ComposeApp      // <-- KMP framework module adı (sende büyük ihtimalle bu)


struct ContentView: View {
    var body: some View {
        ComposeViewController()
            .ignoresSafeArea()
            .onAppear {
                // Kotlin’e "Google butonuna basınca bunu çalıştır" diye handler veriyoruz
                IosCallbacksKt.setIosGoogleClickHandler(handler: { GoogleSignInBridge.signIn() })
            }
    }
}

struct ComposeViewController: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
        // ^ Bu isim sende farklı görünebilir:
        // Xcode autocomplete: "MainViewController" yazınca çıkar.
    }
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
