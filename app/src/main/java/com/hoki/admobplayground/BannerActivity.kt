package com.hoki.admobplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_banner.*

// https://developers.google.com/admob/android/banner

class BannerActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    val adUnitId = "ca-app-pub-3940256099942544/6300978111"

    private val adviewBanner by lazy { findViewById<AdView>(R.id.adviewBanner) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)

        progressBar.visibility = View.VISIBLE

        MobileAds.initialize(this) {
            loadAd()
        }
    }

    private fun loadAd() {
        adviewBanner.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                progressBar.visibility = View.GONE
                Log.d(TAG, "onAdLoaded")
            }

            override fun onAdFailedToLoad(p0: LoadAdError?) {
                super.onAdFailedToLoad(p0)
                Toast.makeText(this@BannerActivity, "onAdFailedToLoad: ${p0}", Toast.LENGTH_LONG).show()
                Log.d(TAG, "onAdFailedToLoad: ${p0}")
                finish()
            }

            override fun onAdOpened() {
                super.onAdOpened()
                Log.d(TAG, "onAdOpened")
            }

            override fun onAdClicked() {
                super.onAdClicked()
                Log.d(TAG, "onAdClicked")
            }

            override fun onAdClosed() {
                super.onAdClosed()
                Log.d(TAG, "onAdClosed")
            }

            override fun onAdLeftApplication() {
                super.onAdLeftApplication()
                Log.d(TAG, "onAdLeftApplication")
            }

            override fun onAdImpression() {
                super.onAdImpression()
                Log.d(TAG, "onAdImpression")
            }
        }

        val adRequest = AdRequest.Builder().build()
        adviewBanner.loadAd(adRequest)


        // top banner programmatically
        val adviewTop = AdView(this)
        adviewTop.adSize = AdSize.BANNER
        adviewTop.adUnitId = adUnitId
        adviewTop.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            this.addRule(RelativeLayout.ALIGN_PARENT_TOP)
            this.addRule(RelativeLayout.CENTER_HORIZONTAL)
        }
        layout_activity_banner.addView(adviewTop)

        adviewTop.loadAd(AdRequest.Builder().build())
    }
}