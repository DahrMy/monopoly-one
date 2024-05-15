package my.dahr.monopolyone.ui.home.friends.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.databinding.FragmentAddFriendsBinding
import my.dahr.monopolyone.ui.home.friends.FriendsViewModel
import my.dahr.monopolyone.ui.home.friends.UsersViewModel

@AndroidEntryPoint
class AddFriendsFragment : Fragment() {
    private val usersViewModel: UsersViewModel by viewModels()

    private var _binding: FragmentAddFriendsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputFriendNick()
    }


    private fun inputFriendNick(){
        binding.btnAddFriend.setOnClickListener {
            val nick = binding.etFriendNick.text.toString()
            usersViewModel.getListOfUsers(nick)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}