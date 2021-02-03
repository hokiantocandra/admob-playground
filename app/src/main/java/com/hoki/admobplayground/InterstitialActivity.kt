package com.hoki.admobplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.gms.ads.*
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_interstitial.*

// https://developers.google.com/admob/android/interstitial

class InterstitialActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    val adUnitId = "ca-app-pub-3940256099942544/1033173712" //img
//    val adUnitId = "ca-app-pub-3940256099942544/8691691433" //video

    private val btnShowAd by lazy { findViewById<Button>(R.id.btnShowAd) }
    private val btnShowAdAndFinish by lazy { findViewById<Button>(R.id.btnShowAdAndFinish) }

    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial)

        progressBar.visibility = View.VISIBLE
        btnShowAd.isEnabled = false
        btnShowAdAndFinish.isEnabled = false

        MobileAds.initialize(this) {
            loadAd()
        }
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(p0: InterstitialAd) {
                Log.d(TAG, "onAdLoaded")
                progressBar.visibility = View.INVISIBLE

                mInterstitialAd = p0
                btnShowAd.isEnabled = true
                btnShowAdAndFinish.isEnabled = true

                mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "onAdShowedFullScreenContent")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "onAdDismissedFullScreenContent")
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError?) {
                        Log.d(TAG, "onAdFailedToShowFullScreenContent")
                    }
                }
            }
            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.d(TAG, "onAdFailedToLoad: $p0")
                progressBar.visibility = View.INVISIBLE
                mInterstitialAd = null
            }
        })
    }

    fun btnShowAd(view: View) {
        if (mInterstitialAd != null) {
            mInterstitialAd!!.show(this)
        } else {
            Log.d(TAG, "The interstitial ad wasn't ready yet.")
        }
    }

    fun btnShowAdAndFinish(view: View) {
        if (mInterstitialAd != null) {
            mInterstitialAd!!.show(this)
            finish()
        } else {
            Log.d(TAG, "The interstitial ad wasn't ready yet.")
        }
    }
}