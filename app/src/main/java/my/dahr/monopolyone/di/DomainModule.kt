package my.dahr.monopolyone.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.dahr.monopolyone.domain.repository.NetworkRepository
import my.dahr.monopolyone.domain.repository.SessionRepository
import my.dahr.monopolyone.domain.usecase.login.RefreshSessionUseCase
import my.dahr.monopolyone.domain.usecase.login.SignInUseCase
import my.dahr.monopolyone.domain.usecase.login.VerifyTotpUseCase
import my.dahr.monopolyone.domain.usecase.session.RequireSessionUseCase

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    // USE CASES
    @Provides
    internal fun provideRequireSessionUseCase(
        sessionRepository: SessionRepository,
        networkRepository: NetworkRepository
    ): RequireSessionUseCase = RequireSessionUseCase(sessionRepository, networkRepository)

    @Provides
    internal fun provideRefreshSessionUseCase(
        sessionRepository: SessionRepository
    ): RefreshSessionUseCase = RefreshSessionUseCase(sessionRepository)

    @Provides
    internal fun provideSignInUseCase(sessionRepository: SessionRepository): SignInUseCase =
        SignInUseCase(sessionRepository)

    @Provides
    internal fun provideVerifyTotpUseCase(sessionRepository: SessionRepository): VerifyTotpUseCase =
        VerifyTotpUseCase(sessionRepository)

}