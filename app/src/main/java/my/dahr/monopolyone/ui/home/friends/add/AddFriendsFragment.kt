package my.dahr.monopolyone.ui.home.friends.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentAddFriendsBinding
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.users.Data
import my.dahr.monopolyone.ui.home.friends.FriendsFragment
import my.dahr.monopolyone.ui.home.friends.FriendsViewModel
import my.dahr.monopolyone.ui.home.friends.UsersViewModel
import my.dahr.monopolyone.ui.home.friends.adapters.FriendsAdapter
import my.dahr.monopolyone.ui.home.friends.user.UserFragment

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
        setListeners()
        inputFriendNick()
        initObservers()
    }

    private fun setListeners() {
        binding.ivBack.setOnClickListener {
            val fragment = FriendsFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
        binding.layoutUser.setOnClickListener{

        }
    }

    private fun initObservers() {
        usersViewModel.usersResultLiveData.observe(viewLifecycleOwner) {
            val avatar = it[0].avatar
            val nick = it[0].nick
            binding.apply {
                layoutUser.visibility = View.VISIBLE
                tvUserNick.text = nick

            }
            Glide.with(this)
                .load(avatar)
                .into(binding.ivUser)
        }
    }
    private fun clickedOnUser(user: Data){

    }

    private fun inputFriendNick() {
        binding.btnAddFriend.setOnClickListener {
            val nick = binding.etFriendNick.text.toString()
            usersViewModel.getListOfUsers(nick)
            binding.etFriendNick.text = null
            binding.etFriendNick.text?.clear()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}