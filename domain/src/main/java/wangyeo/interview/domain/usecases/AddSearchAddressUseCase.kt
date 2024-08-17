package wangyeo.interview.domain.usecases

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import wangyeo.interview.data.exceptions.AppDomainException
import wangyeo.interview.data.repositories.UserRepository
import wangyeo.interview.data.R
import wangyeo.interview.data.local.room.HistorySearchAddressEntity
import wangyeo.interview.domain.di.MainDispatcher
import wangyeo.interview.domain.usecases.base.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddSearchAddressUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    @MainDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : SuspendUseCase<AddSearchAddressUseCase.Params, Unit>(ioDispatcher) {
    override suspend fun execute(params: Params?) {
        if (params != null) {
            userRepository.addSearchAddress(params.searchAddress)
        } else {
            throw AppDomainException.SnackBarException(message = context.getString(R.string.error_message_default))
        }
    }

    data class Params(
        val searchAddress: HistorySearchAddressEntity
    )
}
