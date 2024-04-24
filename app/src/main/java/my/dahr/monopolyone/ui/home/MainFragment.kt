package my.dahr.monopolyone.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.dahr.monopolyone.R
import my.dahr.monopolyone.ui.home.chat.ChatFragment
import my.dahr.monopolyone.databinding.FragmentMainBinding
import my.dahr.monopolyone.ui.home.friends.FriendsFragment
import my.dahr.monopolyone.ui.home.inventory.InventoryFragment
import my.dahr.monopolyone.listeners.NavigationListener
import my.dahr.monopolyone.ui.home.profile.ProfileFragment

class MainFragment : Fragment(), NavigationListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToFriendsScreen()
        navigationOnScreen()
    }

    private fun navigationOnScreen(){
        binding.bnv.setOnItemSelectedListener {item->
            when(item.itemId) {
                R.id.friends -> {
                    navigateToFriendsScreen()
                    true
                }
                R.id.chat -> {
                    navigateToChatScreen()
                    true
                }
                R.id.inventory -> {
                    navigateToInventoryScreen()
                    true
                }
                R.id.profile -> {
                    navigateToProfileScreen()
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun navigateToProfileScreen() {
        replaceFragment(ProfileFragment.newInstance())
    }

    override fun navigateToFriendsScreen() {
        replaceFragment(FriendsFragment.newInstance())
    }

    override fun navigateToChatScreen() {
        replaceFragment(ChatFragment.newInstance())
    }

    override fun navigateToInventoryScreen() {
        replaceFragment(InventoryFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment){
        parentFragmentManager.beginTransaction()
            .replace(R.id.container,fragment)
            .commit()
    }
}