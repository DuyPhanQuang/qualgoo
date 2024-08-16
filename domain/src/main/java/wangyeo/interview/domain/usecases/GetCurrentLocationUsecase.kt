package wangyeo.interview.domain.usecases

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import wangyeo.interview.core.di.MainDispatcher
import wangyeo.interview.domain.repositories.LocationRepository
import wangyeo.interview.domain.usecases.base.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCurrentLocationUseCase @Inject constructor(
    @MainDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val locationRepository: LocationRepository,
) : FlowUseCase<Unit, LatLng>(coroutineDispatcher) {
    override fun execute(params: Unit?): Flow<LatLng> = locationRepository.getCurrentLocation()
}
