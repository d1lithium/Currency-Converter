import SwiftUI
import UIKit
import shared



@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
        let mainViewController = MainView_iosKt.MainViewiOSController()
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        return true
    }
}
