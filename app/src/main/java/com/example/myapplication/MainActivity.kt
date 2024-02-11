package com.example.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.BottomNavGraph
import com.example.myapplication.ui.bottomMenu.BottomNavBar
import com.example.myapplication.ui.theme.AppTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ShopApp() }
    }
}

@Composable
fun ShopApp() {
    AppTheme {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = { BottomNavBar(navController = navController) }
        ) {
            BottomNavGraph(navController = navController)
        }
    }
}
