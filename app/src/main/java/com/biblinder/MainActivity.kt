package com.biblinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.biblinder.ui.navigation.BiblinderNavGraph
import com.biblinder.ui.theme.BiblinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BiblinderTheme {
                val navController = rememberNavController()
                BiblinderNavGraph(navController = navController)
            }
        }
    }
}
