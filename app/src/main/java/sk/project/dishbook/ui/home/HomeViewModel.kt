package sk.project.dishbook.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sk.project.dishbook.data.model.Cocktail
import sk.project.dishbook.data.model.Meal
import sk.project.dishbook.data.repository.Repository

data class HomeUiState(
    val meals: List<Meal> = emptyList(),
    val cocktails: List<Cocktail> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedTab: Int = 0
)

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            try {
                val result = repository.getMealsAndCocktails()
                    .onErrorReturn { throwable ->
                        throwable.printStackTrace()
                        Pair(emptyList(), emptyList())
                    }
                    .blockingGet()
                _uiState.value = _uiState.value.copy(
                    meals = result.first,
                    cocktails = result.second,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load data"
                )
            }
        }
    }

    fun selectTab(tabIndex: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = tabIndex)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}