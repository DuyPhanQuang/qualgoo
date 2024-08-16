package wangyeo.interview.feature.home.models

import wangyeo.interview.feature.common.models.ViewData
import wangyeo.interview.data.models.Model

interface DataModelMapper<M : Model, VD : ViewData> {
    fun mapToModel(viewData: VD): M

    fun mapToViewData(model: M): VD
}
