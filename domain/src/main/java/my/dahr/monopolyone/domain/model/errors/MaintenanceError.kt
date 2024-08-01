package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class MaintenanceError(override val code: Int = 98) : WrongReturnable