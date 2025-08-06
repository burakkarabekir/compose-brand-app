package com.bksd.brandapp.core_ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.LabeledIntent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import java.io.File
import kotlin.reflect.KClass

fun Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> this
        else -> {
            var context = this
            if (context is ContextWrapper) {
                context = context.baseContext
                if (context is Activity) return context
            }
            null
        }
    }
}

fun Context.isLocationEnabled(): Boolean {
    val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return LocationManagerCompat.isLocationEnabled(locationManager)
}

fun Context.isPermissionGranted(name: String): Boolean {
    return ContextCompat.checkSelfPermission(this, name) == PackageManager.PERMISSION_GRANTED
}

fun Context.getAppSettingsIntent(): Intent = Intent(
    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
    Uri.fromParts("package", packageName, null)
)

fun Context.openBrowser(url: String) {
    // for mock data that doesn't include https://
    val formattedUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
        "https://$url"
    } else {
        url
    }
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl))
    startActivity(browserIntent)
}

fun Context.restartApp(activityClass: KClass<out Activity>) {
    val restartIntent = Intent(this, activityClass.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
    this.startActivity(restartIntent)
    (this as? Activity)?.finish()
}

fun Context.openEmailAppsForSendAction(email: String) {
    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
    }
    try {
        this.startActivity(Intent.createChooser(emailIntent, "E-posta uygulaması seçin"))
    } catch (e: Exception) {
        Toast.makeText(this, "E-posta uygulaması bulunamadı", Toast.LENGTH_SHORT).show()
    }
}

fun Context.openEmailAppsForViewAction() {
    val emailIntent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"))
    val resInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.queryIntentActivities(
            emailIntent,
            PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
        )
    } else {
        packageManager.queryIntentActivities(emailIntent, PackageManager.MATCH_DEFAULT_ONLY)
    }
    if (resInfo.isNotEmpty()) {
        val firstIntent =
            packageManager.getLaunchIntentForPackage(resInfo.first().activityInfo.packageName)
        val labeledIntents = resInfo.drop(1).map {
            LabeledIntent(
                packageManager.getLaunchIntentForPackage(it.activityInfo.packageName),
                it.activityInfo.packageName,
                it.loadLabel(packageManager),
                it.icon
            )
        }
        val chooserIntent = Intent.createChooser(firstIntent, "E-posta uygulaması seçin")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, labeledIntents.toTypedArray())
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(chooserIntent)
    } else {
        Toast.makeText(this, "E-posta uygulaması bulunamadı", Toast.LENGTH_SHORT).show()
    }
}

fun Context.createImageFile(
    imageName: String = "client_image",
    extension: String = ".jpg"
): File {
    val storageDir = this.cacheDir
    return File.createTempFile(imageName, extension, storageDir)
}

fun Context.getUriForFile(file: File): Uri {
    return FileProvider.getUriForFile(
        this,
        "${this.packageName}.provider",
        file
    )
}

fun Context.callPhoneNumber(phoneNumber: String) {
    val uri = Uri.parse("tel:$phoneNumber")
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = uri
    }
    this.startActivity(intent)
}


fun Context.getBitmapDescriptor(
    vectorResId: Int,
    isSelected: Boolean = false
): BitmapDescriptor? {
    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(this, vectorResId) ?: return null

    val sizeMultiplier = if (isSelected) 1.5f else 1.0f
    val width = (drawable.intrinsicWidth * sizeMultiplier).toInt()
    val height = (drawable.intrinsicHeight * sizeMultiplier).toInt()

    drawable.setBounds(0, 0, width, height)
    val bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    // draw it onto the bitmap
    val canvas = Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

fun Context.openGoogleMapsNavigation(destination: LatLng, mode: String = "driving") {
    // Create the URI for Google Maps navigation
    val navigationUri =
        Uri.parse("google.navigation:q=${destination.latitude},${destination.longitude}&mode=$mode")

    // Create an intent with the navigation URI
    val mapIntent = Intent(Intent.ACTION_VIEW, navigationUri).apply {
        setPackage("com.google.android.apps.maps") // Ensure it opens in Google Maps app
    }

    // Check if Google Maps is installed
    if (mapIntent.resolveActivity(packageManager) != null) {
        startActivity(mapIntent) // Start navigation
    } else {
        // If Google Maps is not installed, open location in a browser
        val browserUri =
            Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${destination.latitude},${destination.longitude}&travelmode=$mode")
        startActivity(Intent(Intent.ACTION_VIEW, browserUri))
    }
}

fun Context.shareText(text: String) {
    val sendIntent = Intent(
        Intent.ACTION_SEND
    ).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}

fun Context.isAppInstalled(packageName: String): Boolean {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.ApplicationInfoFlags.of(0L)
            ).enabled
        } else {
            packageManager.getApplicationInfo(packageName, 0).enabled
        }
    } catch (e: Exception) {
        false
    }
}

fun Context.openGooglePlay(packageName: String) {
    try {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName")
            )
        )
    } catch (e: ActivityNotFoundException) {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}