package my.dahr.monopolyone.ui.login

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.dahr.monopolyone.R
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.databinding.FragmentLoginBinding
import my.dahr.monopolyone.ui.home.MainFragment
import my.dahr.monopolyone.utils.validEmail
import my.dahr.monopolyone.utils.validPassword

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        initObservers()
        setListeners()

        return binding.root
    }

    private fun initObservers() {
        btLoginObserver()
    }

    private fun setListeners() {
        binding.apply {
            btLogin.setOnClickListener { btLoginOnClickListener() }
        }
    }

    private fun btLoginOnClickListener() {
        binding.apply {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (!validEmail(email)) {
                inputLayoutEmail.isErrorEnabled = true
                etEmail.error = resources.getString(R.string.et_email_error)
            } else {
                inputLayoutEmail.isErrorEnabled = false
                etEmail.error = null
            }

            if (!validPassword(password)) {
                inputLayoutPassword.isErrorEnabled = true
                etPassword.error = resources.getString(R.string.et_password_error)
                inputLayoutPassword.error
            } else {
                inputLayoutPassword.isErrorEnabled = false
                etPassword.error = null
            }

            if (validEmail(email) && validPassword(password)) {
                viewModel.signIn(email, password)
            }

        }

    }

    private fun btLoginObserver() {
        binding.btLogin.apply {
            viewModel.requestStatusLiveData.observe(viewLifecycleOwner) { status ->
                when (status) {

                    RequestStatus.Success -> {
                        lifecycleScope.launch(Dispatchers.Main) {
                            btLoginEndAnimation(
                                solidColor, viewModel.loadBitmap(R.drawable.ic_done)
                            )

                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container_view, MainFragment())
                                .commit()
                        }
                    }

                    RequestStatus.Failure -> {
                        lifecycleScope.launch(Dispatchers.Main) {
                            btLoginEndAnimation(
                                solidColor, viewModel.loadBitmap(R.drawable.ic_error_outline)
                            )
                        }
                    }

                    RequestStatus.Loading -> {
                        startAnimation()
                    }

                    else -> {}
                }
            }
        }

    }

    private suspend fun btLoginEndAnimation(@ColorInt color: Int, bitmap: Bitmap) {
        binding.btLogin.doneLoadingAnimation(color, bitmap)
        delay(1000)
        binding.btLogin.revertAnimation()
    }

}