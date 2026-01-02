package org.wedding.assistant.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.UIKit.*
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberDatePickerLauncher(
    initialYear: Int,
    initialMonth: Int,
    initialDay: Int,
    onSelected: (year: Int, month: Int, day: Int) -> Unit
): () -> Unit {
    return remember(initialYear, initialMonth, initialDay) {
        {
            dispatch_async(dispatch_get_main_queue()) {
                val presenter = topViewController() ?: return@dispatch_async

                val sheetVc = buildDatePickerSheet(
                    initialYear = initialYear,
                    initialMonth = initialMonth,
                    initialDay = initialDay,
                    onSelected = onSelected
                )


                presenter.presentViewController(sheetVc, animated = true, completion = null)
            }
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun buildDatePickerSheet(
    initialYear: Int,
    initialMonth: Int,
    initialDay: Int,
    onSelected: (year: Int, month: Int, day: Int) -> Unit
): UIViewController {
    val vc = UIViewController()

    vc.view.backgroundColor = UIColor.systemBackgroundColor

    // Header row (Cancel / Title / Done)
    val header = UIView().apply {
        translatesAutoresizingMaskIntoConstraints = false
    }

    val btnCancel = UIButton.buttonWithType(UIButtonTypeSystem) as UIButton
    btnCancel.setTitle("İptal", forState = UIControlStateNormal)
    btnCancel.translatesAutoresizingMaskIntoConstraints = false

    val title = UILabel().apply {
        text = "Tarih Seç"
        font = UIFont.boldSystemFontOfSize(17.0)
        textAlignment = NSTextAlignmentCenter
        translatesAutoresizingMaskIntoConstraints = false
    }

    val btnDone = UIButton.buttonWithType(UIButtonTypeSystem) as UIButton
    btnDone.setTitle("Seç", forState = UIControlStateNormal)
    btnDone.titleLabel?.font = UIFont.boldSystemFontOfSize(17.0)
    btnDone.translatesAutoresizingMaskIntoConstraints = false

    header.addSubview(btnCancel)
    header.addSubview(title)
    header.addSubview(btnDone)

    // Picker
    val picker = UIDatePicker().apply {
        translatesAutoresizingMaskIntoConstraints = false
        datePickerMode = UIDatePickerMode.UIDatePickerModeDate
        preferredDatePickerStyle = UIDatePickerStyle.UIDatePickerStyleWheels

        val cal = NSCalendar.currentCalendar
        val comp = NSDateComponents().apply {
            year = initialYear.toLong()
            month = initialMonth.toLong()
            day = initialDay.toLong()
        }
        val initialDate = cal.dateFromComponents(comp) ?: NSDate()
        setDate(initialDate, animated = false)
    }

    vc.view.addSubview(header)
    vc.view.addSubview(picker)

    val safe = vc.view.safeAreaLayoutGuide

    // Header constraints
    header.topAnchor.constraintEqualToAnchor(safe.topAnchor, constant = 8.0).active = true
    header.leadingAnchor.constraintEqualToAnchor(safe.leadingAnchor, constant = 16.0).active = true
    header.trailingAnchor.constraintEqualToAnchor(safe.trailingAnchor, constant = -16.0).active = true
    header.heightAnchor.constraintEqualToConstant(44.0).active = true

    btnCancel.leadingAnchor.constraintEqualToAnchor(header.leadingAnchor).active = true
    btnCancel.centerYAnchor.constraintEqualToAnchor(header.centerYAnchor).active = true

    btnDone.trailingAnchor.constraintEqualToAnchor(header.trailingAnchor).active = true
    btnDone.centerYAnchor.constraintEqualToAnchor(header.centerYAnchor).active = true

    title.centerXAnchor.constraintEqualToAnchor(header.centerXAnchor).active = true
    title.centerYAnchor.constraintEqualToAnchor(header.centerYAnchor).active = true

    // Picker constraints (butonların ALTINA, safe area içinde)
    picker.topAnchor.constraintEqualToAnchor(header.bottomAnchor, constant = 8.0).active = true
    picker.leadingAnchor.constraintEqualToAnchor(safe.leadingAnchor).active = true
    picker.trailingAnchor.constraintEqualToAnchor(safe.trailingAnchor).active = true
    picker.bottomAnchor.constraintEqualToAnchor(safe.bottomAnchor, constant = -8.0).active = true

    // Actions
    btnCancel.addAction(
        UIAction.actionWithHandler { _ ->
            vc.dismissViewControllerAnimated(true, completion = null)
        },
        forControlEvents = UIControlEventTouchUpInside
    )

    btnDone.addAction(
        UIAction.actionWithHandler { _ ->
            val cal = NSCalendar.currentCalendar
            val comps = cal.components(
                unitFlags = (NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay),
                fromDate = picker.date
            )
            onSelected(comps.year.toInt(), comps.month.toInt(), comps.day.toInt())
            vc.dismissViewControllerAnimated(true, completion = null)
        },
        forControlEvents = UIControlEventTouchUpInside
    )

    return vc
}

private fun topViewController(): UIViewController? {
    val app = UIApplication.sharedApplication
    val windowScene = app.connectedScenes
        .filterIsInstance<UIWindowScene>()
        .firstOrNull()

    val keyWindow = windowScene
        ?.windows
        ?.firstOrNull { (it as UIWindow).isKeyWindow() } as? UIWindow

    var vc = keyWindow?.rootViewController ?: app.keyWindow?.rootViewController
    while (true) {
        vc = when (vc) {
            is UINavigationController -> (vc as UINavigationController).visibleViewController
            is UITabBarController -> (vc as UITabBarController).selectedViewController
            else -> vc?.presentedViewController ?: break
        }
    }
    return vc
}