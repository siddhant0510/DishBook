package sk.project.dishbook.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class Cocktail(
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String,
    val strCategory: String? = null,
    val strAlcoholic: String? = null,
    val strGlass: String? = null,
    val strInstructions: String? = null,
    val ingredients: List<Ingredient> = emptyList()
)

@Immutable
data class CocktailResponse(
    val drinks: List<Cocktail>?
)
