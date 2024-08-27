package my.dahr.monopolyone.domain.model.friends.params

data class ListParams(
   val userId: Any,
   val online: Boolean,
   val addUser: Boolean,
   val type: String,
   val offset: Int,
   val count: Int,
)
