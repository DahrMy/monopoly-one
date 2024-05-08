package my.dahr.monopolyone.ui.login

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import my.dahr.monopolyone.R
import my.dahr.monopolyone.data.models.Session
import my.dahr.monopolyone.data.network.api.AuthorizationApi
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.utils.SESSION_KEY
import my.dahr.monopolyone.utils.SHARED_PREFERENCES
import retrofit2.Callback
import javax.inject.Inject

@ViewModelScoped
class LoginRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: AuthorizationApi
) {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun postSignIn(email: String, password: String, callback: () -> Callback<SessionResponse>) {
        val requestBody = mapOf(
            "email" to email,
            "password" to password
        )
        val call = api.authSignInRequest(requestBody)
        call.enqueue(callback.invoke())
    }

    fun saveSession(session: Session) {
        val serializedData = Gson().toJson(session)
        sharedPreferences.edit()
            .putString(SESSION_KEY, serializedData)
            .apply()

        Log.i("SharedPreferences", "Session saved with data:\n$serializedData")
    }

    fun getBitmapFromDrawableRes(@DrawableRes id: Int): Bitmap {

        val drawable = AppCompatResources.getDrawable(context, id)
        if (drawable != null) {
            return drawable.toBitmap()
        } else {
            val reserveDrawable =
                AppCompatResources.getDrawable(context, R.drawable.ic_disabled_by_default)
            return reserveDrawable!!.toBitmap()
        }

    }

    fun getErrorMessageStringResource(code: Int) = when (code) {
        1 -> context.resources.getString(R.string.dialog_error_1_text)
        2 -> context.resources.getString(R.string.dialog_error_2_text)
        3 -> context.resources.getString(R.string.dialog_error_3_text)
        4 -> context.resources.getString(R.string.dialog_error_4_text)
        5 -> context.resources.getString(R.string.dialog_error_5_text)
        6 -> context.resources.getString(R.string.dialog_error_6_text)
        7 -> context.resources.getString(R.string.dialog_error_7_text)
        8 -> context.resources.getString(R.string.dialog_error_8_text)
        10 -> context.resources.getString(R.string.dialog_error_10_text)
        11 -> context.resources.getString(R.string.dialog_error_11_text)
        12 -> context.resources.getString(R.string.dialog_error_12_text)
        98 -> context.resources.getString(R.string.dialog_error_98_text)
        else -> { context.resources.getString(R.string.dialog_error_99_text) }
    }

}