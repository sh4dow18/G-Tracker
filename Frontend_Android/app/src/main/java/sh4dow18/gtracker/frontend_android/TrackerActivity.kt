package sh4dow18.gtracker.frontend_android

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import sh4dow18.gtracker.frontend_android.databinding.ActivityTrackerBinding
import sh4dow18.gtracker.frontend_android.utils.MyApplication
import sh4dow18.gtracker.frontend_android.view_models.user.StateUser
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModel
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModelFactory

class TrackerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityTrackerBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarTracker.toolbar)
        binding.appBarTracker.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_tracker) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_profile
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tracker, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_tracker)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}