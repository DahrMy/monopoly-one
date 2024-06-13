package my.dahr.monopolyone.ui.home.friends.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.databinding.FragmentAddFriendsBinding
import my.dahr.monopolyone.ui.home.friends.FriendsFragment
import my.dahr.monopolyone.ui.home.friends.user.UsersViewModel
import my.dahr.monopolyone.ui.home.friends.user.UserFragment
import my.dahr.monopolyone.utils.LoadingDialog
import kotlin.concurrent.fixedRateTimer

@AndroidEntryPoint
class AddFriendsFragment : Fragment() {
    private val usersViewModel: UsersViewModel by viewModels()

    private lateinit var dialog: LoadingDialog

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
        dialog = LoadingDialog(requireActivity())
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
            clickedOnUser()
        }
    }

    private fun initObservers() {
        usersViewModel.usersResultLiveData.observe(viewLifecycleOwner) {
            val user = it[0]
            usersViewModel.setUser(user)
            setContent()
        }

        usersViewModel.requestStatusLiveData.observe(viewLifecycleOwner) { status ->
            when (status) {
                RequestStatus.Success -> {
                    dialog.isDismiss()
                }

                RequestStatus.Failure -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(resources.getString(R.string.dialog_failure_title))
                        .setPositiveButton(resources.getString(R.string.dialog_bt_ok)) { _, _ -> }
                        .setMessage(R.string.dialog_failure_text)
                        .show()
                }

                RequestStatus.Loading -> {
                    dialog.startLoading()
                }

                else -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(resources.getString(R.string.dialog_error_title))
                        .setPositiveButton(resources.getString(R.string.dialog_bt_ok)) { _, _ -> }
                        .setMessage(usersViewModel.loadErrorMessage(status))
                        .show()
                }
            }
        }
    }

    private fun setContent() {
        val user = usersViewModel.getUser()
        binding.apply {
            layoutUser.visibility = View.VISIBLE
            tvUserNick.text = user?.nick
        }
        Glide.with(this)
            .load(user?.avatar)
            .into(binding.ivUser)
    }

    private fun clickedOnUser() {
        val user = usersViewModel.getUser()
        if (user != null) {
            val fragment = UserFragment.newInstance(
                user.userId,
                user.avatar,
                user.nick,
                user.xpLevel,
                user.xp,
                user.games,
                user.gamesWins
            )
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }

    private fun inputFriendNick() {
        binding.btnAddFriend.setOnClickListener {
            val id = binding.etFriendNick.text.toString()
            usersViewModel.getListOfUsers(id)
            binding.etFriendNick.text = null
            binding.etFriendNick.text?.clear()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}