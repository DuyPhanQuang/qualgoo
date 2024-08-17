package wangyeo.interview.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import wangyeo.interview.data.local.room.HistorySearchAddressEntity
import wangyeo.interview.data.repositories.UserRepository
import wangyeo.interview.domain.di.MainDispatcher
import wangyeo.interview.domain.usecases.base.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSearchAddressUseCase @Inject constructor(
    @MainDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : FlowUseCase<Unit, List<HistorySearchAddressEntity>>(ioDispatcher) {
    override fun execute(params: Unit?): Flow<List<HistorySearchAddressEntity>> = userRepository.getSearchAddress()
}
