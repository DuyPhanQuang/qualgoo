package wangyeo.interview.common.base

import wangyeo.interview.domain.exceptions.AppDomainException

open class ViewState(
    open val isLoading: Boolean = false,
    open val error: AppDomainException? = null,
)
