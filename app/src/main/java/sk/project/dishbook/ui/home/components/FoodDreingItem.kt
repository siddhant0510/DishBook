package sk.project.dishbook.ui.home.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import sk.project.dishbook.data.model.Cocktail
import sk.project.dishbook.data.model.Meal

@Composable
fun MealItem(
    meal: Meal,
    onClick: () -> Unit
) {
    FoodDrinkItem(
        imageUrl = meal.strMealThumb,
        title = meal.strMeal,
        category = meal.strCategory ?: "Unknown Category",
        onClick = onClick
    )
}

@Composable
fun CocktailItem(
    cocktail: Cocktail,
    onClick: () -> Unit
) {
    FoodDrinkItem(
        imageUrl = cocktail.strDrinkThumb,
        title = cocktail.strDrink,
        category = cocktail.strCategory ?: "Unknown Category",
        onClick = onClick
    )
}

@Composable
private fun FoodDrinkItem(
    imageUrl: String,
    title: String,
    category: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .weight(0.3f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(0.7f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}