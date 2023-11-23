package com.mkiperszmid.emptyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.mkiperszmid.emptyapp.login.LoginViewModel
import com.mkiperszmid.emptyapp.navigation.AppNavigation
import com.mkiperszmid.emptyapp.ui.theme.EmptyAppTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        mainViewModel.checkAuthentication(this)
        super.onCreate(savedInstanceState)

        setContent {
            EmptyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigation(mainViewModel, this)
                }
            }
        }

    }

}
