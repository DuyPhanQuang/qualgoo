package wangyeo.interview.feature.home.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import wangyeo.interview.core.enums.ActionType
import wangyeo.interview.data.dialog.AlertDialog
import wangyeo.interview.data.exceptions.AppDomainException
import wangyeo.interview.domain.usecases.GetCurrentLocationUseCase
import wangyeo.interview.domain.usecases.GetCurrentWeatherUseCase
import wangyeo.interview.domain.usecases.GetLocationFromTextUseCase
import wangyeo.interview.feature.common.base.BaseViewModel
import wangyeo.interview.feature.common.global.Constants
import wangyeo.interview.feature.home.models.CurrentWeatherMapper
import wangyeo.interview.feature.common.R
import javax.inject.Inject
import io.flutter.embedding.android.FlutterActivity;

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context, // No leak in here!
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val currentWeatherMapper: CurrentWeatherMapper,
    private val getLocationFromTextUseCase: GetLocationFromTextUseCase,
) : BaseViewModel() {
    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> = _state

    private var currentLocation = Constants.Default.LAT_LNG_DEFAULT

    init {
        _state.update {
            it.copy(isRequestPermission = true)
        }
    }

    override fun showError(error: AppDomainException) {
        if (_state.value.error == null) {
            _state.update {
                it.copy(isLoading = false, error = error)
            }
        }
    }

    override fun hideError() {
        _state.update {
            it.copy(isLoading = false, error = null)
        }
    }

    private fun showLoading() {
        _state.update {
            it.copy(isLoading = true)
        }
    }

    override fun hideLoading() {
        _state.update {
            it.copy(
                isLoading = false,
                isRefresh = false,
            )
        }
    }

    fun cleanEvent() {
        _state.update {
            it.copy(
                isRequestPermission = false,
                navigateSearch = null,
            )
        }
    }

    fun getWeatherByLocation(latLng: LatLng) {
        retryViewModelScope {
            showLoading()
            getCurrentWeather(latLng)
        }
    }

    fun getWeatherByAddressName(addressName: String) {
        retryViewModelScope {
            showLoading()
            getLocationFromTextUseCase(GetLocationFromTextUseCase.Params(addressName)).collect {
                val latLng = LatLng(it.latitude, it.longitude)
                getCurrentWeather(latLng)
            }
        }
    }

    private fun getCurrentWeather(latLng: LatLng) {
        retryViewModelScope {
            if (currentLocation != latLng) {
                currentLocation = latLng
            }

            getCurrentWeatherUseCase(GetCurrentWeatherUseCase.Params(currentLocation)).collect { currentWeather ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isRefresh = false,
                        currentWeather = currentWeatherMapper.mapToViewData(currentWeather),
                    )
                }
            }
        }
    }

    fun getCurrentLocation() {
        retryViewModelScope {
            showLoading()
            getCurrentLocationUseCase().collect {
                getCurrentWeather(it)
            }
        }
    }

    fun permissionIsNotGranted() {
        val error = AppDomainException.AlertException(
            code = -1, alertDialog = AlertDialog(
                title = context.getString(R.string.error_title_permission_not_granted),
                message = context.getString(R.string.error_message_permission_not_granted),
                positiveMessage = "Open setting",
                negativeMessage = context.getString(R.string.cancel),
                positiveAction = ActionType.OPEN_PERMISSION,
            )
        )
        showError(error)
    }

    fun onRefreshCurrentWeather(showRefresh: Boolean = true) {
        _state.update {
            it.copy(
                isRefresh = showRefresh,
                isLoading = !showRefresh,
            )
        }

        getCurrentLocation()
    }

    fun navigateToSearchByText() {
        _state.update {
            it.copy(navigateSearch = currentLocation)
        }
    }

    fun onGoToBook() {
        startActivity(
            FlutterActivity
                .withNewEngine()
                .initialRoute("/book_tab")
                .build(this)
        )
    }
}