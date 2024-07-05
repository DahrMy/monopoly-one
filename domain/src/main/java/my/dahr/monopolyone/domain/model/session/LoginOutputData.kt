package my.dahr.monopolyone.domain.model.session

import my.dahr.monopolyone.domain.model.SuccessfulReturnable

/**
 * A parent class for models [Session] and [TotpToken][my.dahr.monopolyone.domain.model.session.login.TotpToken].
 * [SignInUseCase][my.dahr.monopolyone.domain.usecase.login.SignInUseCase] returns it.
 */
internal interface LoginOutputData : SuccessfulReturnable