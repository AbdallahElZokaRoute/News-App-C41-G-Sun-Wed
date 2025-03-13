package com.route.newsappc41gsunwed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.route.newsappc41gsunwed.categories.CategoriesScreen
import com.route.newsappc41gsunwed.news.NewsScreenContent
import com.route.newsappc41gsunwed.ui.theme.NewsAppC41GSunWedTheme
import com.route.newsappc41gsunwed.utils.topBar.NewsToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Side Effect ->
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppC41GSunWedTheme {
                // Don't Call API HERE
                // Toolbars or Bottom Navigation Views or Navigation Drawers
                // Use Scaffold
                Scaffold(
                    topBar = {
                        NewsToolbar(title = "General")
                    },
                    containerColor = Color.Black
                ) { paddingValues ->
                    paddingValues
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = CategoriesScreen,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        // Login Screen - Register Screen- Forgot Password
                        composable<CategoriesScreen> {
                            CategoriesScreen(navController)
                        }
                        composable<NewsScreen> {
                            val endpointId = it.toRoute<NewsScreen>().endpointId
                            NewsScreenContent(endpointId)
                        }
                    }

                }
            }
        }


    }
}
