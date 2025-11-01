package sk.project.dishbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import sk.project.dishbook.ui.detail.DetailScreen
import sk.project.dishbook.ui.detail.DetailViewModel
import sk.project.dishbook.ui.home.HomeScreen
import sk.project.dishbook.ui.home.HomeViewModel
import sk.project.dishbook.ui.theme.DishBookTheme

class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DishBookTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(
                                viewModel = homeViewModel,
                                navController = navController
                            )
                        }
                        composable("detail/{type}/{id}") { backStackEntry ->

                            val type = backStackEntry.arguments?.getString("type") ?: ""
                            val id = backStackEntry.arguments?.getString("id") ?: ""

                            val detailViewModel: DetailViewModel = koinViewModel()

                            LaunchedEffect(type, id) {
                                if (type.isNotEmpty() && id.isNotEmpty()) {
                                    detailViewModel.initialize(type, id)
                                }
                            }

                            DetailScreen(viewModel = detailViewModel)
                        }
                    }
                }
            }
        }
    }
}