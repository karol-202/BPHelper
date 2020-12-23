package pl.karol202.bphelper.ui.activity

import android.media.AudioManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.karol202.bphelper.presentation.viewdata.PermissionRequestViewData
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.databinding.ActivityMainBinding
import pl.karol202.bphelper.ui.extensions.androidName
import pl.karol202.bphelper.ui.extensions.collectIn
import pl.karol202.bphelper.ui.extensions.findByAndroidName
import pl.karol202.bphelper.ui.extensions.viewBinding
import pl.karol202.bphelper.ui.viewmodel.AndroidPermissionViewModel

class MainActivity : AppCompatActivity()
{
	private val permissionViewModel by viewModel<AndroidPermissionViewModel>()

	private val navController by lazy { findNavController(R.id.fragmentNavHost) }
	private val appBarConfiguration by lazy { AppBarConfiguration(views.navigationView.menu, views.drawerLayout) }

	private val views by viewBinding(ActivityMainBinding::inflate)

	override fun onCreate(savedInstanceState: Bundle?)
	{
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		setContentView(views.root)

		observePermissionRequests()

		initToolbar()
		initNavigationView()
		initDrawerLayout()
	}

	private fun observePermissionRequests() = permissionViewModel.permissionRequests.collectIn(lifecycleScope) { request ->
		requestPermissions(arrayOf(request.type.androidName), request.id)
	}

	private fun initToolbar()
	{
		views.toolbar.setupWithNavController(navController, appBarConfiguration)
	}

	private fun initNavigationView()
	{
		views.navigationView.setupWithNavController(navController)
	}

	private fun initDrawerLayout()
	{
		navController.addOnDestinationChangedListener { _, destination, _ ->
			if(destination.isTopLevel()) views.drawerLayout.unlock()
			else views.drawerLayout.closeAndLock()
		}
	}

	private fun NavDestination.isTopLevel() = appBarConfiguration.topLevelDestinations.contains(id)

	private fun DrawerLayout.closeAndLock() = setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

	private fun DrawerLayout.unlock() = setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

	override fun onResume()
	{
		super.onResume()
		volumeControlStream = AudioManager.STREAM_MUSIC
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		permissions.forEach {
			permissionViewModel.markPermissionRequestProcessed(PermissionRequestViewData.Type.findByAndroidName(it))
		}
	}
}
