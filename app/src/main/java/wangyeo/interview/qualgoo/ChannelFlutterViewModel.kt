package wangyeo.interview.qualgoo

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import wangyeo.interview.feature.common.base.BaseViewModel
import javax.inject.Inject

fun <T> MutableLiveData<T>.mutation(actions: (MutableLiveData<T>) -> Unit) {
    actions(this)
    this.value = this.value
}

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ChannelFlutterViewModel @Inject constructor(
    @ApplicationContext private val context: Context, // No leak in here!
) : BaseViewModel() {
    private val _state = MutableLiveData<ChannelFlutterViewState>(ChannelFlutterViewState())
    val goToFlutterActivity: LiveData<ChannelFlutterViewState> get() = _state

    init {
        _state.mutation {
            it.value?.isStarted = false
        }
    }

    // ready to start flutter activity
    fun onGoToFlutterScreenClick(args: String? = null) {
        _state.mutation {
            it.value?.isStarted = true
            it.value?.arguments = args
        }
    }

    // Reset the event if needed
    fun onOpenFlutterComplete() {

    }
}