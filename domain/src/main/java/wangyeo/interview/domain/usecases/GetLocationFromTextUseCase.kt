package wangyeo.interview.domain.usecases

import android.content.Context
import android.location.Address
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import wangyeo.interview.core.extensions.asFlow
import wangyeo.interview.data.exceptions.AppDomainException
import wangyeo.interview.data.repositories.LocationRepository
import wangyeo.interview.data.R
import wangyeo.interview.domain.di.MainDispatcher
import wangyeo.interview.domain.usecases.base.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLocationFromTextUseCase @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    private val locationRepository: LocationRepository,
) : FlowUseCase<GetLocationFromTextUseCase.Params, Address>(mainDispatcher) {
    override fun execute(params: Params?): Flow<Address> = if (params == null) {
        AppDomainException.SnackBarException(message = context.getString(R.string.error_message_lat_lng_are_invalid))
            .asFlow()
    } else {
        locationRepository.getLocationFromText(params.text)
    }

    data class Params(
        val text: String
    )
}