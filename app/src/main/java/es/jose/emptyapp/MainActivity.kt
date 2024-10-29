package es.jose.emptyapp

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import es.jose.emptyapp.login.LoginViewModel
import es.jose.emptyapp.navigation.AppNavigation
import es.jose.emptyapp.ui.theme.EmptyAppTheme
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        mainViewModel.checkAuthentication(this)
        super.onCreate(savedInstanceState)

        var appContext = applicationContext

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

        Configuration.getInstance().load(appContext, PreferenceManager.getDefaultSharedPreferences(appContext))
        Configuration.getInstance().userAgentValue = "MapApp"

    }

}
