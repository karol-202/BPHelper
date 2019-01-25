package pl.karol202.bphelper

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Spinner
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener

class MembersActivity : AppCompatActivity()
{
	private lateinit var membersViewModel: MembersViewModel
	private var tableConfigurationType: TableConfigurationType? = null

	private val ui = MembersActivityUI()
	private val configurationAdapter = TableConfigurationAdapter()
	private val membersAdapter = MembersAdapter()

	override fun onCreate(savedInstanceState: Bundle?)
	{
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		initViewModel()

		ui.setContentView(this)
		initToolbar()
		initConfigurationSpinner()
		initRecyclerView()
		initDrawButton()
		updateErrorBanner()
	}

	private fun initViewModel()
	{
		membersViewModel = ViewModelProviders.of(this).get(MembersViewModel::class.java)
		membersViewModel.allMembers.observe(this, Observer { members ->
			members?.let { membersAdapter.members = it }
			updateErrorBanner()
		})
	}

	private fun initToolbar()
	{
		setSupportActionBar(ui.toolbar)
	}

	private fun initConfigurationSpinner()
	{
		ui.configurationSpinner.apply {
			adapter = configurationAdapter
			onItemSelectedListener {
				onItemSelected { _, _, position, _ ->
					tableConfigurationType = TableConfigurationType.values()[position]
					updateErrorBanner()
				}
			}
		}
	}

	private fun initRecyclerView()
	{
		membersAdapter.callback = object : MembersAdapter.Callback {
			override fun addMember(member: Member)
			{
				membersViewModel.addMember(member)
			}

			override fun updateMember(member: Member)
			{
				membersViewModel.updateMember(member)
			}

			override fun removeMember(member: Member)
			{
				membersViewModel.removeMember(member)
			}
		}

		ui.recyclerView.adapter = membersAdapter
	}

	private fun initDrawButton()
	{
		ui.drawButton.onClick {
			val remainingSeats = getRemainingSeatsForMembers()
			if(remainingSeats == 0) tableConfigurationType?.let { type ->
				startActivity<TablesActivity>(TablesActivity.KEY_TABLE_CONFIGURATION_NAME to type.name)
			}
			else
			{
				val message = when
				{
					remainingSeats == null -> resources.getString(R.string.alert_cannot_draw_error)
					remainingSeats > 0 -> resources.getQuantityString(R.plurals.text_too_few_members, remainingSeats, remainingSeats)
					else -> resources.getQuantityString(R.plurals.text_too_many_members, -remainingSeats, -remainingSeats)
				}
				alert(Appcompat, message, getString(R.string.alert_cannot_draw_title)) {
					negativeButton(R.string.action_cancel) { dialog ->
						dialog.dismiss()
					}
				}.show()
			}
		}
	}

	private fun updateErrorBanner()
	{
		val remainingSeats = getRemainingSeatsForMembers()
		if(remainingSeats != null && remainingSeats != 0)
		{
			val text = if(remainingSeats > 0) resources.getQuantityString(R.plurals.text_too_few_members, remainingSeats, remainingSeats)
					   else resources.getQuantityString(R.plurals.text_too_many_members, -remainingSeats, -remainingSeats)
			ui.errorBanner.show(text)
		}
		else ui.errorBanner.hide()
	}

	private fun getRemainingSeatsForMembers(): Int?
	{
		val factory = tableConfigurationType?.factory ?: return null
		val members = membersViewModel.allMembers.value ?: return null
		return factory.getRemainingSeatsForMembers(members)
	}
}

private class MembersActivityUI : AnkoComponent<MembersActivity>
{
	lateinit var toolbar: Toolbar
		private set
	lateinit var configurationSpinner: Spinner
		private set
	lateinit var errorBanner: Banner
		private set
	lateinit var recyclerView: RecyclerView
		private set
	lateinit var drawButton: FloatingActionButton
		private set

	override fun createView(ui: AnkoContext<MembersActivity>) = with(ui) {
		coordinatorLayout {
			appBarLayout {
				toolbar {
					toolbar = this
					doOnApi(Build.VERSION_CODES.LOLLIPOP) { elevation = dip(4).toFloat() }
				}.lparams(width = MATCH_PARENT)

				spinner {
					configurationSpinner = this
				}.lparams(width = WRAP_CONTENT) {
					marginStart = dip(16)
				}
			}.lparams(width = MATCH_PARENT)

			verticalLayout {
				banner {
					errorBanner = this
					visibility = View.GONE
				}.lparams(width = MATCH_PARENT)

				recyclerView {
					recyclerView = this
					layoutManager = LinearLayoutManager(ctx)
					addItemDecoration(ItemDivider(ctx.getDrawableCompat(R.drawable.divider)))
				}.lparams(width = MATCH_PARENT, height = MATCH_PARENT)
			}.lparams(width = MATCH_PARENT) {
				behavior = AppBarLayout.ScrollingViewBehavior()
			}

			floatingActionButton {
				drawButton = this
				size = FloatingActionButton.SIZE_NORMAL
				imageResource = R.drawable.ic_random_white_24dp
				doOnApi(Build.VERSION_CODES.LOLLIPOP) { elevation = dip(6).toFloat() }
			}.lparams {
				gravity = Gravity.END or Gravity.BOTTOM
				margin = dip(16)
			}
		}
	}
}
