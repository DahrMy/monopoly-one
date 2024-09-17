package my.dahr.monopolyone.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentLoginBinding
import my.dahr.monopolyone.domain.model.UndefinedError
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.domain.model.login.TotpToken
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.ui.dialog.error.showErrorDialog
import my.dahr.monopolyone.ui.home.MainFragment
import my.dahr.monopolyone.ui.dialog.totp.TotpDialogFragment
import my.dahr.monopolyone.utils.ProgressState
import my.dahr.monopolyone.utils.endAnimation
import my.dahr.monopolyone.utils.getBitmapById
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
        pageObserver()
        btLoginObserver()
    }


    private fun setListeners() {
        binding.apply {
            btLogin.setOnClickListener(btLoginOnClickListener())
        }
    }


    private fun pageObserver() {
        viewModel.loginResultLiveData.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Session -> moveToMainFragment()
                is TotpToken -> showTotpDialog(data)
                is WrongReturnable -> showErrorDialog(data, requireContext())
                else -> showErrorDialog(UndefinedError(), requireContext())
            }
        }
    }


    private fun btLoginObserver() {
        viewModel.loginProgressStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {

                ProgressState.Success -> lifecycleScope.launch(Dispatchers.Main) {
                    binding.btLogin.endAnimation(
                        color = binding.btLogin.solidColor,
                        bitmap = requireContext().getBitmapById(R.drawable.ic_done)
                    )
                }

                ProgressState.Loading -> {
                    binding.btLogin.startAnimation()
                }

                ProgressState.Error, null -> lifecycleScope.launch(Dispatchers.Main) {
                    binding.btLogin.endAnimation(
                        bitmap = requireContext().getBitmapById(R.drawable.ic_error_outline)
                    )
                }

                ProgressState.Idle -> {
                    binding.btLogin.revertAnimation()
                }

            }
        }
    }


    private fun btLoginOnClickListener() = OnClickListener {
        binding.apply {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (!validEmail(email)) {
                inputLayoutEmail.isErrorEnabled = true
                etEmail.error = resources.getString(R.string.et_email_error)
            }

            if (!validPassword(password)) {
                inputLayoutPassword.isErrorEnabled = true
                etPassword.error = resources.getString(R.string.et_password_error)
                inputLayoutPassword.error
            }

            if (validEmail(email) && validPassword(password)) {
                viewModel.signIn(email, password, requireActivity().applicationContext)
            }

        }
    }


    private fun moveToMainFragment() {
        parentFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN)
            .replace(R.id.fragment_container_view, MainFragment())
            .commit()
    }


    private fun showTotpDialog(totpToken: TotpToken) {
        val newFragment = TotpDialogFragment.newInstance(totpToken)
        val isLargeLayout = resources.getBoolean(R.bool.large_layout)

        if (isLargeLayout) {
            newFragment.show(parentFragmentManager, "dialog")
        } else {
            parentFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(android.R.id.content, newFragment)
                .addToBackStack(null)
                .commit()
        }

    }


}