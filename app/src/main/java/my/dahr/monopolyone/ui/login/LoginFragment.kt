package my.dahr.monopolyone.ui.login

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.dahr.monopolyone.R
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.databinding.FragmentLoginBinding
import my.dahr.monopolyone.ui.home.MainFragment
import my.dahr.monopolyone.ui.login.dialog.totp.TotpDialogFragment
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
            }

            if (!validPassword(password)) {
                inputLayoutPassword.isErrorEnabled = true
                etPassword.error = resources.getString(R.string.et_password_error)
                inputLayoutPassword.error
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

                    RequestStatus.TwoFaCode -> {
                        Toast.makeText(requireContext(), "TOTP not yet implemented", Toast.LENGTH_SHORT).show()
                    }

                    RequestStatus.Failure -> {
                        lifecycleScope.launch(Dispatchers.Main) {
                            btLoginEndAnimation(
                                solidColor, viewModel.loadBitmap(R.drawable.ic_error_outline)
                            )
                        }
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(resources.getString(R.string.dialog_failure_title))
                            .setPositiveButton(resources.getString(R.string.dialog_bt_ok)) { _, _ -> }
                            .setMessage(R.string.dialog_failure_text)
                            .show()
                    }

                    RequestStatus.Loading -> {
                        startAnimation()
                    }

                    else -> {
                        lifecycleScope.launch(Dispatchers.Main) {
                            btLoginEndAnimation(
                                solidColor, viewModel.loadBitmap(R.drawable.ic_error_outline)
                            )
                        }
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(resources.getString(R.string.dialog_error_title))
                            .setPositiveButton(resources.getString(R.string.dialog_bt_ok)) { _, _ -> }
                            .setMessage(viewModel.loadErrorMessage(status.code))
                            .show()
                    }
                }
            }
        }

    }

    private suspend fun btLoginEndAnimation(@ColorInt color: Int, bitmap: Bitmap) {
        binding.btLogin.doneLoadingAnimation(color, bitmap)
        delay(1000)
        binding.btLogin.revertAnimation()
    }

    fun showTotpDialog() {
        val fragmentManager = parentFragmentManager
        val newFragment = TotpDialogFragment.newInstance(viewModel.totpToken)

        if (isLargeLayout) {
            // The device is using a large layout, so show the fragment as a
            // dialog.
            newFragment.show(fragmentManager, "dialog")
        } else {
            // The device is smaller, so show the fragment fullscreen.
            val transaction = fragmentManager.beginTransaction()
            // For a polished look, specify a transition animation.
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity.
            transaction
                .add(android.R.id.content, newFragment)
                .addToBackStack(null)
                .commit()
        }

    }

}