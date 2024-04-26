package my.dahr.monopolyone.ui.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.databinding.FragmentLoginBinding
import my.dahr.monopolyone.utils.SESSION_KEY
import my.dahr.monopolyone.utils.SHARED_PREFERENCES

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.apply {
            btLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                viewModel.signIn(email, password)
            }
        }

        val sharedPreferences =
            requireContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        sharedPreferences.getString(SESSION_KEY, "")?.let {
            Log.i("SHARED_PREFERENCES", it)
        }

        return binding.root
    }

}