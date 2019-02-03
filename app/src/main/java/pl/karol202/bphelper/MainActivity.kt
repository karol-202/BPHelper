package pl.karol202.bphelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import pl.karol202.bphelper.Notifications
import pl.karol202.bphelper.R

class MainActivity : AppCompatActivity()
{
	private val navController by lazy { findNavController(R.id.fragmentNavHost) }
	private val appBarConfiguration by lazy { AppBarConfiguration(navigationView.menu, drawerLayout) }

	override fun onCreate(savedInstanceState: Bundle?)
	{
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		Notifications.registerChannels(this)
		setContentView(R.layout.activity_main)

		initToolbar()
		initNavigationView()
		initDrawerLayout()
	}

	private fun initToolbar()
	{
		toolbar.setupWithNavController(navController, appBarConfiguration)
	}

	private fun initNavigationView()
	{
		navigationView.setupWithNavController(navController)
	}

	private fun initDrawerLayout()
	{
		navController.addOnDestinationChangedListener { _, destination, _ ->
			if(destination.isTopLevel()) drawerLayout.unlock()
			else drawerLayout.closeAndLock()
		}
	}

	private fun NavDestination.isTopLevel() = appBarConfiguration.topLevelDestinations.contains(id)

	private fun DrawerLayout.closeAndLock() = setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

	private fun DrawerLayout.unlock() = setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
}
