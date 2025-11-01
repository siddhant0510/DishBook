package sk.project.dishbook.ui.detail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sk.project.dishbook.data.model.Cocktail
import sk.project.dishbook.data.model.Meal
import sk.project.dishbook.data.repository.Repository

sealed class DetailType {
    data class MealDetail(val meal: Meal? = null) : DetailType()
    data class CocktailDetail(val cocktail: Cocktail? = null) : DetailType()
}

data class DetailUiState(
    val detailType: DetailType = DetailType.MealDetail(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class DetailViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private var currentType: String = ""
    private var currentId: String = ""

    fun initialize(type: String, id: String) {
        if (type != currentType || id != currentId) {
            currentType = type
            currentId = id
            loadDetail()
        }
    }

    private fun loadDetail() {

        _uiState.update { currentState ->
            currentState.copy(
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            try {
                when (currentType) {
                    "meal" -> {
                        val meal = repository.getMealDetail(currentId)
                        _uiState.value = _uiState.value.copy(
                            detailType = DetailType.MealDetail(meal),
                            isLoading = false
                        )
                    }
                    "cocktail" -> {
                        val cocktail = repository.getCocktailDetail(currentId)
                        _uiState.value = _uiState.value.copy(
                            detailType = DetailType.CocktailDetail(cocktail),
                            isLoading = false
                        )
                    }
                    else -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Invalid detail type"
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load detail"
                )
            }
        }
    }

    fun retry() {
        loadDetail()
    }
}