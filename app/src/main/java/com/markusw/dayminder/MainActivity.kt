package com.markusw.dayminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.markusw.dayminder.home.presentation.HomeScreen
import com.markusw.dayminder.ui.theme.DayMinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DayMinderTheme {
                Surface {
                    HomeScreen()
                }
            }
        }
    }
}

