package my.dahr.monopolyone.ui.dialog.error

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import my.dahr.monopolyone.R
import my.dahr.monopolyone.domain.model.Failure
import my.dahr.monopolyone.domain.model.ParamInvalidError
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.utils.getErrorMessageStringResource

internal fun showErrorDialog(data: WrongReturnable, context: Context) {

    val title = when(data) {
        is Failure -> context.resources.getString(R.string.dialog_failure_title)
        // TODO: is NoInternetError -> context.resources.getString(R.string.dialog_noInternet_title)
        else -> context.resources.getString(R.string.dialog_error_title)
    }
    val message = context.getErrorMessageStringResource(data)

    val alertBuilder = MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(context.resources.getString(R.string.dialog_bt_ok)) { _, _ -> }

    when (data) {
        is ParamInvalidError -> {
            alertBuilder
                .setView(R.layout.dialog_error)
                .show()

            setAlertContent(data.issues)
        }

        else -> alertBuilder.show()
    }

}

private fun setAlertContent(list: List<ParamInvalidError.Issue>) {
    TODO("Not yet implemented. Create RecyclerViewAdapter for R.layout.dialog_error.")
}