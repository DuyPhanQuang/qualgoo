package wangyeo.interview.feature.common.models

import wangyeo.interview.data.models.Model

interface DataModelMapper<M : Model, VD : ViewData> {
    fun mapToModel(viewData: VD): M

    fun mapToViewData(model: M): VD
}
