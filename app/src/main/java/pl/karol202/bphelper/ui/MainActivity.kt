package pl.karol202.bphelper.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import pl.karol202.bphelper.R

class MainActivity : AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		initToolbar()
	}

	private fun initToolbar()
	{
		val navController = findNavController(R.id.fragmentNavHost)
		val appBarConfiguration = AppBarConfiguration(navController.graph)
		toolbar.setupWithNavController(navController, appBarConfiguration)
	}
}
