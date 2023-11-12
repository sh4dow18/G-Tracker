package sh4dow18.gtracker.frontend_android
// Main Activity Dependencies
import android.os.Bundle
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
import androidx.navigation.fragment.NavHostFragment
import sh4dow18.gtracker.frontend_android.databinding.ActivityMainBinding
// Main Activity Class. This one inherits from AppCompatActivity that is an Android Base Class
class MainActivity : AppCompatActivity() {
    // Variables Declaration
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // Use the default configuration from the function onCreate
        super.onCreate(savedInstanceState)
        // Inflate Main Activity View
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Establishes the Main Activity View as the Activity Content
        setContentView(binding.root)
        // Set the Toolbar as App Bar
        setSupportActionBar(binding.appBarMain.toolbar)
        // Fab Button Click Listener
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        // Get Burger Menu
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        // Get Navigation Fragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        // Get Navigation Controller
        val navController = navHostFragment.navController
        // Set Application Bar with the Fragments Ids
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        // Set the Navigation Controller with the New Application Bar Configuration
        setupActionBarWithNavController(navController, appBarConfiguration)
        // Set the Navigation Controller with the Navigation View and shows like a Burger Menu Icon
        navView.setupWithNavController(navController)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the Action Bar Menu
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    // Manage the Android Navigation Up
    override fun onSupportNavigateUp(): Boolean {
        // Gets the Main Activity Navigation Controller
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Tries to do the Navigation Up from the Navigation Controller, but if it is in Home Page, use the default configuration
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}