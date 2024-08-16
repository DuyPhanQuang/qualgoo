package wangyeo.interview.feature.common.base

import wangyeo.interview.data.exceptions.AppDomainException

open class ViewState(
    open val isLoading: Boolean = false,
    open val error: AppDomainException? = null,
)
