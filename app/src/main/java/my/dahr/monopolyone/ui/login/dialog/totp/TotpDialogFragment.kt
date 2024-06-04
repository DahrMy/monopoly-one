package my.dahr.monopolyone.ui.login.dialog.totp

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.fragment.app.DialogFragment
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
import my.dahr.monopolyone.databinding.FragmentDialogTotpBinding
import my.dahr.monopolyone.ui.home.MainFragment

private const val TOTP_TOKEN = "totp_token"

@AndroidEntryPoint
class TotpDialogFragment : DialogFragment() {

    private var _binding: FragmentDialogTotpBinding? = null
    private val binding: FragmentDialogTotpBinding get() = _binding!!

    private var totpToken: String? = null

    private val viewModel by viewModels<TotpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            totpToken = it.getString(TOTP_TOKEN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogTotpBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun btVerifyOnClickListener() {
        binding.apply {
            val code = et2faCode.text.toString().toInt()
            totpToken?.let {
                viewModel.verifyCode(code, it)
            }

        }

    }

    private fun btVerifyObserver() {
        binding.btVerify.apply {
            viewModel.requestStatusLiveData.observe(viewLifecycleOwner) { status ->
                when (status) {

                    RequestStatus.Success -> {
                        lifecycleScope.launch(Dispatchers.Main) {
                            btLoginEndAnimation(
                                solidColor, viewModel.loadBitmap(R.drawable.ic_done)
                            )

                            parentFragmentManager.beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN)
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
        binding.btVerify.doneLoadingAnimation(color, bitmap)
        delay(1000)
        binding.btVerify.revertAnimation()
    }

    companion object {
        @JvmStatic
        fun newInstance(totpToken: String) =
            TotpDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(TOTP_TOKEN, totpToken)
                }
            }
    }

}
