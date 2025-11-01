package sk.project.dishbook.ui.home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import sk.project.dishbook.data.model.Cocktail
import sk.project.dishbook.data.model.Meal
import sk.project.dishbook.ui.common.ErrorScreen
import sk.project.dishbook.ui.common.ShimmerEffect
import sk.project.dishbook.ui.home.components.CocktailItem
import sk.project.dishbook.ui.home.components.MealItem
import sk.project.dishbook.ui.home.components.TabLayout


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        if (uiState.meals.isEmpty() && uiState.cocktails.isEmpty()) {
            viewModel.loadData()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Food & Drink Explorer") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabLayout(
                selectedTabIndex = uiState.selectedTab,
                onTabSelected = viewModel::selectTab
            )

            when {
                uiState.isLoading -> {
                    ShimmerEffect()
                }

                uiState.error != null -> {
                    ErrorScreen(
                        message = uiState.error,
                        onRetry = viewModel::loadData
                    )
                }

                else -> {
                    when (uiState.selectedTab) {
                        0 -> MealList(
                            meals = uiState.meals,
                            onMealClick = { meal ->
                                navController.navigate("detail/meal/${meal.idMeal}")
                            }
                        )
                        1 -> CocktailList(
                            cocktails = uiState.cocktails,
                            onCocktailClick = { cocktail ->
                                navController.navigate("detail/cocktail/${cocktail.idDrink}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MealList(
    meals: List<Meal>,
    onMealClick: (Meal) -> Unit
) {
    if (meals.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text("No meals found")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(meals, key = { it.idMeal }) { meal ->
                MealItem(
                    meal = meal,
                    onClick = { onMealClick(meal) }
                )
            }
        }
    }
}

@Composable
private fun CocktailList(
    cocktails: List<Cocktail>,
    onCocktailClick: (Cocktail) -> Unit
) {
    if (cocktails.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text("No cocktails found")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(cocktails, key = { it.idDrink }) { cocktail ->
                CocktailItem(
                    cocktail = cocktail,
                    onClick = { onCocktailClick(cocktail) }
                )
            }
        }
    }
}