package com.route.newsappc41gsunwed

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.route.newsappc41gsunwed.api.ApiManager
import com.route.newsappc41gsunwed.api.model.SourcesResponse
import com.route.newsappc41gsunwed.ui.theme.NewsAppC41GSunWedTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppC41GSunWedTheme {
                // Don't Call API HERE
                LaunchedEffect(Unit) {
                    ApiManager.newsServices.getSources()
//                        .execute() // Run On Main Thread
                        .enqueue(object : Callback<SourcesResponse> {
                            override fun onFailure(
                                p0: Call<SourcesResponse>,
                                throwable: Throwable
                            ) {
                                Log.e("TAG", "onFailure: ${throwable.message}")
                            }

                            override fun onResponse(
                                call: Call<SourcesResponse>,
                                response: Response<SourcesResponse>
                            ) {
                                Log.e("TAG", "onResponse: ${response}")
                                Log.e("TAG", "onResponse: ${response.body()?.sources}")
                            }
                        }) // Run on Background Thread and Returns Result On Main Thread
                }

            }
        }
    }
}
