package com.augustin.thestarclub.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.augustin.thestarclub.Navigator
import com.augustin.thestarclub.ui.theme.VolleyRequestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VolleyRequestTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigator()
                }
            }
        }
    }
}



