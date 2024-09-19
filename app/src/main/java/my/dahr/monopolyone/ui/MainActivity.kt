package my.dahr.monopolyone.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.ActivityMainBinding
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.domain.usecase.session.RequireSessionUseCase
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var requireSessionUseCase: RequireSessionUseCase

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

        defineStartingFragment()

    }

    private fun defineStartingFragment() {

        val session = CoroutineScope(Dispatchers.IO).async { requireSessionUseCase() }

        MainScope().launch {
            when (session.await()) { // TODO: Test speed of the app loading
                is Session -> setFragment(MainFragment())
                else -> setFragment(MainFragment())
//                null -> setFragment(LoginFragment())
//                else -> setFragment(LoginFragment()) // TODO: Add crashlytics and toast for showing reason of logout
            }
        }

    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }

}