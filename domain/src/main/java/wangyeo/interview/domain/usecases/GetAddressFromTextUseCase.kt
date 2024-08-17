package wangyeo.interview.domain.usecases

import android.content.Context
import com.google.android.libraries.places.api.model.AutocompletePrediction
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
class GetAddressFromTextUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val locationRepository: LocationRepository,
) : FlowUseCase<GetAddressFromTextUseCase.Params, List<AutocompletePrediction>>(ioDispatcher) {
    override fun execute(params: Params?): Flow<List<AutocompletePrediction>> = if (params == null) {
        AppDomainException.SnackBarException(message = context.getString(R.string.error_message_address_is_not_found))
            .asFlow()
    } else {
        locationRepository.getAddress(params.text)
    }

    data class Params(
        val text: String,
    )
}