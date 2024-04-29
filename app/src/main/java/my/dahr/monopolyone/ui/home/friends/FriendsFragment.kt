package my.dahr.monopolyone.ui.home.friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.databinding.FragmentFriendsBinding
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.ui.home.friends.adapter.FriendsAdapter


@AndroidEntryPoint
class FriendsFragment : Fragment() {
    private val viewModel: FriendsViewModel by viewModels()

    private var _binding: FragmentFriendsBinding? = null
    private val binding get () = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        viewModel.getFriendList()
        viewModel.getFriendRequestsList()
    }
    private fun initObservers(){
        viewModel.friendsResultLiveData.observe(viewLifecycleOwner){
            showFriendsList(it)
        }
    }
    private fun showFriendsList(friends: List<Friend>){
        val adapter = FriendsAdapter()
        adapter.submitList(friends)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFriends.layoutManager = layoutManager
        binding.rvFriends.setHasFixedSize(true)
        binding.rvFriends.adapter = adapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): FriendsFragment = FriendsFragment()
    }
}