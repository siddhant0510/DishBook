package sk.project.dishbook.data.repository

import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import sk.project.dishbook.data.api.ApiClient
import sk.project.dishbook.data.model.Cocktail
import sk.project.dishbook.data.model.Meal

class Repository {
    fun getMealsAndCocktails(): Single<Pair<List<Meal>, List<Cocktail>>> {
        val mealsSingle = Single.fromCallable {
            runBlocking {
               val response =  ApiClient.mealApiService.searchMeals("chicken")
                response.meals ?: emptyList()
            }
        }

        val cocktailsSingle = Single.fromCallable {
            runBlocking {
               val response =  ApiClient.cocktailApiService.searchCocktails("margarita")
                response.drinks ?: emptyList()
            }
        }

        return Single.zip(mealsSingle, cocktailsSingle) { meals, cocktails ->
            try {
                val processedMeals = processMeals(meals)
                val processedCocktails = processCocktails(cocktails)
                Pair(processedMeals, processedCocktails)
            } catch (e: Exception) {
                e.printStackTrace()
                Pair(emptyList(), emptyList())
            }
        }
    }

    suspend fun getMealDetail(id: String): Meal? {
        try {
            val response = ApiClient.mealApiService.getMealDetail(id)
            val meal = response.meals?.firstOrNull()
            return meal?.let {
                it.copy(ingredients = emptyList())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun getCocktailDetail(id: String): Cocktail? {
        try {
            val response = ApiClient.cocktailApiService.getCocktailDetail(id)
            val cocktail = response.drinks?.firstOrNull()
            return cocktail?.let {
                it.copy(ingredients = emptyList())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun processMeals(meals: List<Meal>): List<Meal> {
        return meals.map { meal ->
            meal.copy(ingredients = emptyList())
        }
    }

    private fun processCocktails(cocktails: List<Cocktail>): List<Cocktail> {
        return cocktails.map { cocktail ->
            cocktail.copy(ingredients = emptyList())
        }
    }

//    private fun Meal.toMap(): Map<String, Any?> {
//        return mapOf(
//            "strIngredient1" to this::class.java.getDeclaredField("strIngredient1").get(this),
//            "strMeasure1" to this::class.java.getDeclaredField("strMeasure1").get(this),
//        )
//    }
//
//    private fun Cocktail.toMap(): Map<String, Any?> {
//        return mapOf(
//            "strIngredient1" to this::class.java.getDeclaredField("strIngredient1").get(this),
//            "strMeasure1" to this::class.java.getDeclaredField("strMeasure1").get(this),
//        )
//    }
}
