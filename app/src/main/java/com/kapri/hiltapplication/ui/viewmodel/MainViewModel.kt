package com.kapri.hiltapplication.ui.viewmodel

//import androidx.hilt.Assisted
//import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapri.hiltapplication.data.db.model.Country
import com.kapri.hiltapplication.data.repository.CountryRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//https://stackoverflow.com/questions/66185820/dagger-hilt-assisted-and-viewmodelinject-is-deprecated-in-dagger-hilt-view
//class MainViewModel  @ViewModelInject constructor(
@HiltViewModel  //https://github.com/google/dagger/issues/2287
class MainViewModel  @Inject constructor(
    private val repository: CountryRepo,
//    @Assisted private val savedStateHandle: SavedStateHandle
     private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    suspend fun getAllCountries(): List<Country> = repository.getAllCountries()

    fun insertCountry(name: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.insertCountry(
                Country(name, name, name, name, 100, name)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}