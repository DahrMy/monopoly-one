package my.dahr.monopolyone.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.data.repository.SessionRepository
import my.dahr.monopolyone.databinding.ActivityMainBinding
import my.dahr.monopolyone.ui.home.MainFragment
import my.dahr.monopolyone.ui.login.LoginFragment
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var sessionRepository: SessionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE).edit()
//            .remove(SESSION_KEY)
//            .apply()
        onSessionCheck()

    }

    private fun onSessionCheck() {
        when (sessionRepository.isSessionNotExpired()) {
            true -> setFragment(MainFragment())
            false -> setFragment(LoginFragment())
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }

}