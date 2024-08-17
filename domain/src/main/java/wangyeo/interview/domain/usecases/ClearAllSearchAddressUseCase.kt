package wangyeo.interview.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import wangyeo.interview.data.repositories.UserRepository
import wangyeo.interview.domain.di.MainDispatcher
import wangyeo.interview.domain.usecases.base.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearAllSearchAddressUseCase @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : SuspendUseCase<Unit, Unit>(mainDispatcher) {
    override suspend fun execute(params: Unit?) = userRepository.clearAllSearchAddress()
}
