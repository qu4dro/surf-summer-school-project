package orlov.surf.summer.school.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavController()
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashFragment -> binding.nvBottomNavigation.visibility = View.GONE
                R.id.loginFragment -> binding.nvBottomNavigation.visibility = View.GONE
                R.id.photoInfoFragment -> binding.nvBottomNavigation.visibility = View.GONE
                else -> binding.nvBottomNavigation.visibility = View.VISIBLE
            }
        }
        binding.nvBottomNavigation.setupWithNavController(navController)
        binding.nvBottomNavigation.setOnItemReselectedListener{}
    }


}