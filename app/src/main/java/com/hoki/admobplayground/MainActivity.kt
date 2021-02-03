package com.hoki.admobplayground

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

// https://developers.google.com/admob/android/quick-start#kotlin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startActivity(cls: Class<*>) {
        startActivity(Intent(this, cls))
    }

    fun btnBanner(view: View) {
        startActivity(BannerActivity::class.java)
    }

    fun btnInterstitial(view: View) {
        startActivity(InterstitialActivity::class.java)
    }

    fun btnNative(view: View) {
        startActivity(NativeActivity::class.java)
    }

    fun btnRewarded(view: View) {
        startActivity(RewardedActivity::class.java)
    }
}