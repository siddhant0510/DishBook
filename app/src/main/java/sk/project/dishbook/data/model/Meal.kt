package sk.project.dishbook.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String? = null,
    val strArea: String? = null,
    val strInstructions: String? = null,
    val strYoutube: String? = null,
    val ingredients: List<Ingredient> = emptyList()
)

@Immutable
data class Ingredient(
    val ingredient: String,
    val measure: String
)

@Immutable
data class MealResponse(
    val meals: List<Meal>?
)