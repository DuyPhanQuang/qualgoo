package com.example.search.models

import wangyeo.interview.data.local.room.HistorySearchAddressEntity
import wangyeo.interview.feature.common.models.DataModelMapper
import wangyeo.interview.feature.common.models.ViewData
import javax.inject.Inject

data class HistorySearchAddressViewData(
    val address: String = "",
    val timeSearch: Long = 0,
) : ViewData()

class HistorySearchAddressViewDataMapper @Inject constructor() :
    DataModelMapper<HistorySearchAddressEntity, HistorySearchAddressViewData> {
    override fun mapToModel(viewData: HistorySearchAddressViewData): HistorySearchAddressEntity =
        HistorySearchAddressEntity(
            addressName = viewData.address,
            timeSearch = System.currentTimeMillis(),
        )

    override fun mapToViewData(model: HistorySearchAddressEntity): HistorySearchAddressViewData =
        HistorySearchAddressViewData(
            address = model.addressName,
            timeSearch = model.timeSearch,
        )
}
