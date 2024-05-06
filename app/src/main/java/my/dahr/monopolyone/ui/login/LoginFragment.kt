package my.dahr.monopolyone.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.data.models.LoginStatus
import my.dahr.monopolyone.databinding.FragmentLoginBinding
import my.dahr.monopolyone.ui.home.MainFragment

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        setListeners()

        return binding.root
    }

    private fun setListeners() {
        binding.apply {
            btLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                btLogin.startAnimation()
                viewModel.signIn(email, password)
                btLoginObserver()
            }
        }
    }

    private fun btLoginObserver() {
        viewModel.loginStatusLiveData.observe(viewLifecycleOwner) { status ->
            if (status == LoginStatus.Success) {

                binding.btLogin.doneLoadingAnimation(
                    binding.btLogin.solidColor,
                    viewModel.loadBitmap(R.drawable.ic_done)
                )

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, MainFragment())
                    .commit()

            }
        }
    }

}