package my.dahr.monopolyone.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.data.models.Session
import my.dahr.monopolyone.databinding.ActivityMainBinding
import my.dahr.monopolyone.ui.login.LoginFragment
import my.dahr.monopolyone.utils.SESSION_KEY
import my.dahr.monopolyone.utils.SHARED_PREFERENCES
import my.dahr.monopolyone.utils.currentTimeInSec
import my.dahr.monopolyone.ui.home.MainFragment
import my.dahr.monopolyone.ui.home.friends.FriendsFragment


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onSessionCheck()

    }

    private fun onSessionCheck() {
        val sessionJson = sharedPreferences.getString(SESSION_KEY, "")
//        val sessionJsonTest = "{\"accessToken\":\"4UC4otPsYyCGnovXy0VNo0ym0zwWA7yE\",\"expiresAt\":1714416142779,\"refreshToken\":\"4ZpBDKV55gNl4cjBWB2KTqvasm0dvMkzHxmmnlqTSGu5lPdckyvh7R3YGMcJQT2YJWr4iOiZ5NN82zYQVezD9hb8bN5jqmIYnQlZ14g\",\"userId\":1363434}"

        if (!sessionJson.isNullOrBlank()) {
            val session = Gson().fromJson(sessionJson, Session::class.java)
            val isNotExpired = (session.expiresAt - currentTimeInSec) >= 10 // Ten seconds for reserve

            if (isNotExpired) {
                setFragment(MainFragment())
            } else {
                sharedPreferences.edit().remove(SESSION_KEY).apply()
                setFragment(LoginFragment())
            }

        } else {
            setFragment(LoginFragment())
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }

}