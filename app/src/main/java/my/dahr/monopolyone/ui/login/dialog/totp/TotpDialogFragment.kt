package my.dahr.monopolyone.ui.login.dialog.totp

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import my.dahr.monopolyone.databinding.FragmentDialogTotpBinding

private const val TOTP_TOKEN = "totp_token"

class TotpDialogFragment : DialogFragment() {

    private var _binding: FragmentDialogTotpBinding? = null
    private val binding: FragmentDialogTotpBinding get() = _binding!!

    private var totpToken: String? = null

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using
        // onCreateView() is to modify the dialog characteristics. For example,
        // the dialog includes a title by default, but your custom layout might
        // not need it. Here, you can remove the dialog title, but you must
        // call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
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
