package com.hoki.admobplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.android.synthetic.main.activity_rewarded.*

class RewardedActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    val adUnitId = "ca-app-pub-3940256099942544/5224354917"

    private val btnShowAd by lazy { findViewById<Button>(R.id.btnShowAd) }
    private val btnWatchAdToFinish by lazy { findViewById<Button>(R.id.btnWatchAdToFinish) }

    private var mRewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded)

        progressBar.visibility = View.VISIBLE
        btnShowAd.isEnabled = false
        btnWatchAdToFinish.isEnabled = false

        MobileAds.initialize(this) {
            loadAd()
        }
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(this, adUnitId, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdLoaded(p0: RewardedAd) {
                Log.d(TAG, "onAdLoaded")
                progressBar.visibility = View.GONE

                mRewardedAd = p0
                btnShowAd.isEnabled = true
                btnWatchAdToFinish.isEnabled = true

                mRewardedAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "onAdShowedFullScreenContent")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "onAdDismissedFullScreenContent")
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError?) {
                        Log.d(TAG, "onAdFailedToShowFullScreenContent: $p0")
                    }
                }
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.d(TAG, "onAdFailedToLoad")
                progressBar.visibility = View.GONE
            }
        })
    }

    fun btnShowAd(view: View) {
        if (mRewardedAd != null) {
            mRewardedAd!!.show(this) { p0 ->
                val rewardAmount = p0.amount
                val rewardType = p0.type
                Log.d(TAG, "User earned the reward.\nAmount: ${rewardAmount} \nType: ${rewardType}")
                textResult.text = "User earned the reward.\nAmount: ${rewardAmount}\nType: ${rewardType}"
            }
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
            textResult.text = "The rewarded ad wasn't ready yet."
        }
    }

    fun btnWatchAdToFinish(view: View) {
        if (mRewardedAd != null) {
            mRewardedAd!!.show(this) { p0 ->
                val rewardAmount = p0.amount
                val rewardType = p0.type
                Log.d(TAG, "User earned the reward.\nAmount: $rewardAmount \nType: $rewardType")
                textResult.text = "User earned the reward.\nAmount: ${rewardAmount}\nType: ${rewardType}"
            }
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
            textResult.text = "The rewarded ad wasn't ready yet."
        }
    }
}