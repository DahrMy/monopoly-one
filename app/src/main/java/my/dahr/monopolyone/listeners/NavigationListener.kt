package my.dahr.monopolyone.listeners

interface NavigationListener {

    fun navigateToUserScreen()

    fun navigateBack()
    fun navigateToProfileScreen()
    fun navigateToFriendsScreen()
    fun navigateToChatScreen()
    fun navigateToInventoryScreen()

    fun navigateToFriendsRequestsScreen()
}