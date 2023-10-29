package com.markusw.dayminder.core.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.markusw.dayminder.core.utils.Constants.ADMOB_BANNER_AD_ID

@Composable
fun AdmobBanner(
    modifier: Modifier = Modifier,
    onAdClick: () -> Unit = {},
    onAdLoaded: () -> Unit = {},
    onAdClosed: () -> Unit = {},
    onAdOpened: () -> Unit = {},
    onAdFailedToLoad: (LoadAdError) -> Unit = { _ -> },
) {

    AndroidView(factory = { context ->
        AdView(context).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = ADMOB_BANNER_AD_ID
            adListener = object: AdListener() {
                override fun onAdClicked() {
                    super.onAdClicked()
                    onAdClick()
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    onAdFailedToLoad(p0)
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    onAdLoaded()
                }

                override fun onAdClosed() {
                    super.onAdClosed()
                    onAdClosed()
                }

                override fun onAdOpened() {
                    super.onAdOpened()
                    onAdOpened()
                }

            }

            loadAd(AdRequest.Builder().build())
        }
    }, modifier = modifier)

}