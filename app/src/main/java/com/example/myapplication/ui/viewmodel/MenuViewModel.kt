package com.example.myapplication.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.DishModel
import com.example.myapplication.data.model.DrinkModel
import com.example.myapplication.data.model.MenuModel
import com.example.myapplication.data.model.PromotionModel
import com.example.myapplication.domain.dish.*
import com.example.myapplication.domain.drink.GetDrinksUseCase
import com.example.myapplication.domain.drink.RefreshDrinksUseCase
import com.example.myapplication.domain.menu.GetMenusUseCase
import com.example.myapplication.domain.menu.RefreshMenusUseCase
import com.example.myapplication.domain.promotion.GetPromotionsUseCase
import com.example.myapplication.domain.promotion.RefreshPromotionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getPrincipalesUseCase: GetPrincipalesUseCase,
    private val getEntrantesUseCase: GetEntrantesUseCase,
    private val getPostresUseCase: GetPostresUseCase,
    private val refreshDishesUseCase: RefreshDishesUseCase,

    private val getDrinksUseCase: GetDrinksUseCase,
    private val refreshDrinksUseCase: RefreshDrinksUseCase,

    private val getMenusUseCase: GetMenusUseCase,
    private val refreshMenusUseCase: RefreshMenusUseCase,

    private val getPromotionsUseCase: GetPromotionsUseCase,
    private val refreshPromotionsUseCase: RefreshPromotionsUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(MenuScreenState())
    val state: State<MenuScreenState> = _state

    init {
        loadAllCategories()
    }

    fun loadAllCategories() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val result = withContext(Dispatchers.IO) {
                    val principales = async { getPrincipalesUseCase() }
                    val entrantes = async { getEntrantesUseCase() }
                    val postres = async { getPostresUseCase() }
                    val bebidas = async { getDrinksUseCase() }
                    val menus = async { getMenusUseCase() }
                    val promociones = async { getPromotionsUseCase() }

                    MenuData(
                        principales = principales.await(),
                        entrantes = entrantes.await(),
                        postres = postres.await(),
                        bebidas = bebidas.await(),
                        menus = menus.await(),
                        promociones = promociones.await()
                    )
                }

                _state.value = MenuScreenState(
                    data = result,
                    isLoading = false
                )

            } catch (e: Exception) {
                _state.value = MenuScreenState(
                    error = e.message ?: "Error al cargar el menú"
                )
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            refreshDishesUseCase()
            refreshDrinksUseCase()
            refreshMenusUseCase()
            refreshPromotionsUseCase()
            loadAllCategories()
        }
    }
}

data class MenuScreenState(
    val data: MenuData = MenuData(),
    val isLoading: Boolean = true,
    val error: String? = null
)

data class MenuData(
    val principales: List<DishModel> = emptyList(),
    val entrantes: List<DishModel> = emptyList(),
    val postres: List<DishModel> = emptyList(),
    val bebidas: List<DrinkModel> = emptyList(),
    val menus: List<MenuModel> = emptyList(),
    val promociones: List<PromotionModel> = emptyList()
)