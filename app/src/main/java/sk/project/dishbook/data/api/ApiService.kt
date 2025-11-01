package sk.project.dishbook.data.api


import retrofit2.http.GET
import retrofit2.http.Query
import sk.project.dishbook.data.model.CocktailResponse
import sk.project.dishbook.data.model.MealResponse

interface ApiService {
    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealResponse

    @GET("lookup.php")
    suspend fun getMealDetail(@Query("i") id: String): MealResponse

    @GET("search.php")
    suspend fun searchCocktails(@Query("s") query: String): CocktailResponse

    @GET("lookup.php")
    suspend fun getCocktailDetail(@Query("i") id: String): CocktailResponse
}