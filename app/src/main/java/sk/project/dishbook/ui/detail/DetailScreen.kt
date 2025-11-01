package sk.project.dishbook.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.runtime.collectAsState
import sk.project.dishbook.ui.common.ErrorScreen
import sk.project.dishbook.ui.common.ShimmerEffect
import sk.project.dishbook.data.model.Cocktail
import sk.project.dishbook.data.model.Meal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel
) {
    val uiState = viewModel.uiState.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    // Back button would be handled by navigation
                }
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                ShimmerEffect()
            }

            uiState.error != null -> {
                ErrorScreen(
                    message = uiState.error,
                    onRetry = viewModel::retry
                )
            }

            else -> {
                when (val detailType = uiState.detailType) {
                    is DetailType.MealDetail -> {
                        MealDetailContent(
                            meal = detailType.meal,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                    is DetailType.CocktailDetail -> {
                        CocktailDetailContent(
                            cocktail = detailType.cocktail,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MealDetailContent(
    meal: Meal?,
    modifier: Modifier = Modifier
) {
    if (meal == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Meal not found")
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = meal.strMeal,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                meal.strCategory?.let { category ->
                    Text(
                        text = "Category: $category",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                meal.strArea?.let { area ->
                    Text(
                        text = "Area: $area",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Ingredients:",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                meal.ingredients.forEach { ingredient ->
                    if (ingredient.ingredient.isNotBlank()) {
                        Text(
                            text = "• ${ingredient.ingredient} - ${ingredient.measure}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Instructions:",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = meal.strInstructions ?: "No instructions available",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

@Composable
private fun CocktailDetailContent(
    cocktail: Cocktail?,
    modifier: Modifier = Modifier
) {
    if (cocktail == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Cocktail not found")
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = cocktail.strDrinkThumb,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = cocktail.strDrink,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                cocktail.strCategory?.let { category ->
                    Text(
                        text = "Category: $category",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                cocktail.strAlcoholic?.let { alcoholic ->
                    Text(
                        text = "Type: $alcoholic",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                cocktail.strGlass?.let { glass ->
                    Text(
                        text = "Glass: $glass",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Ingredients:",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                cocktail.ingredients.forEach { ingredient ->
                    if (ingredient.ingredient.isNotBlank()) {
                        Text(
                            text = "• ${ingredient.ingredient} - ${ingredient.measure}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Instructions:",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = cocktail.strInstructions ?: "No instructions available",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}