package my.dahr.monopolyone.ui.dialog.error

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import my.dahr.monopolyone.R
import my.dahr.monopolyone.domain.model.Failure
import my.dahr.monopolyone.domain.model.NoInternetConnectionError
import my.dahr.monopolyone.domain.model.ParamInvalidError
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.utils.getErrorMessageStringResource


internal fun showErrorDialog(data: WrongReturnable, context: Context) {

    val title = when(data) {
        is Failure -> context.resources.getString(R.string.dialog_failure_title)
        is NoInternetConnectionError -> context.resources.getString(R.string.dialog_noInternet_title)
        else -> context.resources.getString(R.string.dialog_error_title)
    }
    val message = context.getErrorMessageStringResource(data)

    val alertBuilder = MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(context.resources.getString(R.string.dialog_bt_ok)) { _, _ -> }

    when (data) {
        is ParamInvalidError -> {
            val alert = alertBuilder
                .setView(R.layout.dialog_error)
                .show()

            setAlertContent(alert, data.issues)
        }

        else -> alertBuilder.show()
    }

}


private fun setAlertContent(alert: AlertDialog, list: List<ParamInvalidError.Issue>) {
    val adapter = DialogErrorIssuesRvAdapter(alert.context)
    alert.findViewById<RecyclerView>(R.id.root)?.also {
        it.adapter = adapter
    }
    adapter.updateList(list)
}