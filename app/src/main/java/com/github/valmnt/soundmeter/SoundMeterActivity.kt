package com.github.valmnt.soundmeter

import android.Manifest
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationListener
import android.location.LocationManager
import android.os.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.slider.Slider

class SoundMeterActivity: AppCompatActivity() {

    companion object {
        const val POPUP_DISPLAYED = "popupAlreadyDisplayed"
        const val TAG_PERMISSION_FRAGMENT = "permissionDialogFragment"
        const val REQUEST_CODE_PERMISSION = 1
        const val LOCATION_UPDATE_MS = 10_000L
        const val LOCATION_UPDATE_RADIUS = 100f
    }

    private var popupAlreadyDisplayed = false
    private var locationManager: LocationManager? = null

    private lateinit var latitudeView: TextView
    private lateinit var longitudeView: TextView
    private lateinit var speedLocationView: TextView
    private lateinit var slider: Slider
    private var maxValue: Float = 0F


    private var locationListener = LocationListener { location ->
        // TODO
        latitudeView.text = "lat : " + location.latitude.toString()
        longitudeView.text = "long : " + location.longitude.toString()
        speedLocationView.text = "speed : " + location.speed.toString()

        if (location.hasSpeed() && location.speed > maxValue) {
            Toast.makeText(this, "Vibrate", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_meter)
        popupAlreadyDisplayed = savedInstanceState?.getBoolean(POPUP_DISPLAYED) ?: false
        latitudeView = findViewById(R.id.latitude)
        longitudeView = findViewById(R.id.longitude)
        speedLocationView = findViewById(R.id.speedLocation)
        slider = findViewById(R.id.speed)

        slider.addOnChangeListener { slider, value, fromUser ->
            maxValue = value
        }
    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (supportFragmentManager.findFragmentByTag(TAG_PERMISSION_FRAGMENT) == null
                    && !popupAlreadyDisplayed) {
                supportFragmentManager
                        .beginTransaction()
                        .add(PermissionPopupFragment(), TAG_PERMISSION_FRAGMENT)
                        .commit()
                popupAlreadyDisplayed = true
            }
        } else {
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            locationManager?.requestLocationUpdates(
                    LOCATION_UPDATE_MS,
                    LOCATION_UPDATE_RADIUS,
                    Criteria().apply {
                        accuracy = Criteria.ACCURACY_FINE
                    }, locationListener, Looper.myLooper())
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager?.removeUpdates(locationListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(POPUP_DISPLAYED, popupAlreadyDisplayed)
    }
}
