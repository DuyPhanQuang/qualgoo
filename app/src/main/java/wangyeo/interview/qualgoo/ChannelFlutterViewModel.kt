package wangyeo.interview.qualgoo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChannelFlutterViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableLiveData<ChannelFlutterViewState>()
    val state: LiveData<ChannelFlutterViewState> get() = _state

    init {
        _state.value = ChannelFlutterViewState(
            isStarted = false,
            arguments = null,
        )
    }

    fun onGoToFlutterScreenClick(args: String? = null) {
        _state.value = ChannelFlutterViewState(
            isStarted = true,
            arguments = args,
        )
    }

    // Reset the event if needed
    fun onOpenFlutterComplete() {

    }
}