package wangyeo.interview.qualgoo

import android.annotation.SuppressLint
import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import wangyeo.interview.feature.common.base.BaseViewModel
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ChannelFlutterViewModel @Inject constructor(
    @ApplicationContext private val context: Context, // No leak in here!
) : BaseViewModel() {
    private val _state = MutableStateFlow(ChannelFlutterViewState())
    val state: StateFlow<ChannelFlutterViewState> = _state

    init {
        _state.update {
            it.copy(isStarted = false)
        }
    }

    fun onGoToFlutterScreenClick() {
        _state.update {
            it.copy(isStarted = true)
        }
    }

    fun onOpenFlutterComplete() {

    }
}