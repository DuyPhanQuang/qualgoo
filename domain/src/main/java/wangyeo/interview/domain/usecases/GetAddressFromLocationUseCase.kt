package wangyeo.interview.domain.usecases

import android.content.Context
import android.location.Address
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import wangyeo.interview.core.extensions.asFlow
import wangyeo.interview.data.exceptions.AppDomainException
import wangyeo.interview.data.repositories.LocationRepository
import wangyeo.interview.data.R
import wangyeo.interview.domain.di.IoDispatcher
import wangyeo.interview.domain.usecases.base.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAddressFromLocationUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val locationRepository: LocationRepository,
) : FlowUseCase<GetAddressFromLocationUseCase.Params, Address>(ioDispatcher) {
    override fun execute(params: Params?): Flow<Address> = if (params == null) {
        AppDomainException.SnackBarException(message = context.getString(R.string.error_message_lat_lng_are_invalid))
            .asFlow()
    } else {
        locationRepository.getAddressFromLocation(params.latLng)
    }

    data class Params(
        val latLng: LatLng,
    )
}