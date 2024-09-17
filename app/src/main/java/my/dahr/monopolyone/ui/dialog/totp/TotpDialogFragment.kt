package my.dahr.monopolyone.ui.dialog.totp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentDialogTotpBinding
import my.dahr.monopolyone.domain.model.UndefinedError
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.domain.model.login.TotpToken
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.ui.dialog.error.showErrorDialog
import my.dahr.monopolyone.ui.home.MainFragment
import my.dahr.monopolyone.utils.ProgressState
import my.dahr.monopolyone.utils.endAnimation
import my.dahr.monopolyone.utils.getBitmapById
import java.util.regex.Pattern

private const val TOTP_TOKEN_KEY = "totp_token"

@AndroidEntryPoint
class TotpDialogFragment : DialogFragment() {


    private var _binding: FragmentDialogTotpBinding? = null
    private val binding: FragmentDialogTotpBinding get() = _binding!!

    private var totpToken: TotpToken? = null

    private val viewModel by viewModels<TotpViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(TOTP_TOKEN_KEY)?.let {
            totpToken = TotpToken(it)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogTotpBinding.inflate(inflater, container, false)

        initObservers()
        setListeners()

        return binding.root
    }


    private fun initObservers() {
        pageObserver()
        btVerifyObserver()
    }


    private fun setListeners() {
        binding.apply {

            btVerify.setOnClickListener {
                val code = et2faCode.text.toString().toInt()
                totpToken?.let {
                    viewModel.verifyCode(code, it, requireActivity().applicationContext)
                }
            }

            btCancel.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            et2faCode.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    val text = s.toString()
                    val length = text.length

                    if (length > 0 && !Pattern.matches("""[0-9]{0,6}""", text)) {
                        s?.delete(length - 1, length)
                    }
                }
            })

        }
    }


    private fun pageObserver() {
        viewModel.totpVerifyResultLiveData.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Session -> moveToMainFragment()
                is WrongReturnable -> showErrorDialog(data, requireContext())
                else -> showErrorDialog(UndefinedError(), requireContext())
            }
        }
    }


    private fun btVerifyObserver() {
        viewModel.totpVerifyProgressStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {

                ProgressState.Success -> lifecycleScope.launch(Dispatchers.Main) {
                    binding.btVerify.endAnimation(
                        color = binding.btVerify.solidColor,
                        bitmap = requireContext().getBitmapById(R.drawable.ic_done)
                    )
                }

                ProgressState.Loading -> {
                    binding.btVerify.startAnimation()
                }

                ProgressState.Error, null -> lifecycleScope.launch(Dispatchers.Main) {
                    binding.btVerify.endAnimation(
                        bitmap = requireContext().getBitmapById(R.drawable.ic_error_outline)
                    )
                }

                ProgressState.Idle -> {
                    binding.btVerify.revertAnimation()
                }

            }
        }
    }


    private fun moveToMainFragment() {
        parentFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN)
            .replace(R.id.fragment_container_view, MainFragment())
            .commit()
    }


    companion object {
        @JvmStatic
        fun newInstance(totpToken: TotpToken) =
            TotpDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(TOTP_TOKEN_KEY, totpToken.toString())
                }
            }
    }


}
