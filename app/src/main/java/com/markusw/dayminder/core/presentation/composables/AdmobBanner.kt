package com.markusw.dayminder.core.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.markusw.dayminder.core.utils.Constants.ADMOB_BANNER_AD_ID

/**
 * Created by Markus on 29-10-2023.
 * Admob banner ad
 * @param modifier - modifier for the view
 * @param onAdClick - called when user clicks on the ad
 * @param onAdLoaded - called when ad is loaded
 * @param onAdClosed - called when ad is closed
 * @param onAdOpened - called when ad is opened
 * @param onAdFailedToLoad - called when ad fails to load
 */
@Composable
fun AdmobBanner(
    modifier: Modifier = Modifier,
    onAdClick: () -> Unit = {},
    onAdLoaded: () -> Unit = {},
    onAdClosed: () -> Unit = {},
    onAdOpened: () -> Unit = {},
    onAdFailedToLoad: (LoadAdError) -> Unit = { _ -> },
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val height = 60

    AndroidView(factory = { context ->
        AdView(context).apply {
            setAdSize(AdSize(screenWidth, height))
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

