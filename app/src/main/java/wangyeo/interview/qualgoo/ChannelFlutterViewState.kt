package wangyeo.interview.qualgoo

import wangyeo.interview.data.exceptions.AppDomainException
import wangyeo.interview.feature.common.base.ViewState

data class ChannelFlutterViewState(
    override val isLoading: Boolean = false,
    override val error: AppDomainException? = null,
    val isStarted: Boolean = false,
) : ViewState(isLoading, error)