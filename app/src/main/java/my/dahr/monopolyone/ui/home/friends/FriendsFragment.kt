package my.dahr.monopolyone.ui.home.friends

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.databinding.FragmentFriendsBinding
import my.dahr.monopolyone.ui.home.MainFragment


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
        initObservers()
        binding.ibAdd.setOnClickListener {
            viewModel.getFriendList()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    private fun initObservers(){
        viewModel.friendsResultLiveData.observe(viewLifecycleOwner){
            binding.tvFriendsTitle.text = it.toString()
            Log.e("result", it.toString())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): FriendsFragment = FriendsFragment()
    }
}