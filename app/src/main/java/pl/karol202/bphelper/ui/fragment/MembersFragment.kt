package pl.karol202.bphelper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_members.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pl.karol202.bphelper.R
import pl.karol202.bphelper.extensions.alertDialog
import pl.karol202.bphelper.extensions.ctx
import pl.karol202.bphelper.extensions.setOnItemSelectedListener
import pl.karol202.bphelper.ui.adapter.MemberAdapter
import pl.karol202.bphelper.ui.adapter.TableConfigurationAdapter
import pl.karol202.bphelper.ui.members.MembersFragmentDirections
import pl.karol202.bphelper.TableConfigurationType
import pl.karol202.bphelper.presentation.viewmodel.MembersViewModel

class MembersFragment : Fragment()
{
	private val navController by lazy { NavHostFragment.findNavController(this) }

	private val membersViewModel by sharedViewModel<MembersViewModel>()

	private val configurationAdapter = TableConfigurationAdapter()
	private val membersAdapter = MemberAdapter(memberAddListener = { showMemberAddDialog() },
	                                           memberUpdateListener = { membersViewModel.updateMember(it) },
	                                           memberRemoveListener = { membersViewModel.removeMember(it) })

	private var tableConfigurationType = TableConfigurationType.TYPE_4X2

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.fragment_members, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		observeMembers()

		initConfigurationSpinner()
		initRecyclerView()
		initDrawButton()
		updateErrorBanner()
	}

	private fun observeMembers()
	{
		membersViewModel.allMembers.observe(viewLifecycleOwner, { members ->
			members?.let { membersAdapter.members = it }
			updateErrorBanner()
		})
	}

	private fun initConfigurationSpinner()
	{
		spinnerTableConfiguration.apply {
			adapter = configurationAdapter
			setSelection(configurationAdapter.getPosition(tableConfigurationType))
			setOnItemSelectedListener { _, _, position, _ ->
				tableConfigurationType = TableConfigurationType.values()[position]
				updateErrorBanner()
			}
		}
	}

	private fun initRecyclerView()
	{
		membersAdapter.memberAddListener =
		membersAdapter.memberUpdateListener =
		membersAdapter.memberRemoveListener =

		recyclerMembers.layoutManager = LinearLayoutManager(ctx)
		recyclerMembers.adapter = membersAdapter
		recyclerMembers.addItemDecoration(DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL))
	}

	private fun initDrawButton()
	{
		fabDraw.setOnClickListener {
			val members = members ?: return@setOnClickListener
			val remainingSeats = members.getRemainingSeatsForMembers()
			val valid = members.isConfigurationValidForMembers()
			when
			{
				remainingSeats != 0 -> showMembersAmountAlert(remainingSeats)
				!valid -> showMembersInvalidAlert()
				else -> navigateToTablesFragment()
			}
		}
	}

	private fun List<MemberEntity>.getRemainingSeatsForMembers() =
		tableConfigurationType.getRemainingSeatsForMembers(this)

	private fun List<MemberEntity>.isConfigurationValidForMembers() =
		tableConfigurationType.isPossibleForMembers(this)

	private fun navigateToTablesFragment()
	{
		if(navController.currentDestination?.id != R.id.membersFragment) return
		val action = MembersFragmentDirections.toTablesFragment(tableConfigurationType.name)
		navController.navigate(action)
	}

	private fun showMembersAmountAlert(remainingSeats: Int)
	{
		if(remainingSeats > 0)
			showMembersAlert(resources.getQuantityString(R.plurals.text_too_few_members, remainingSeats, remainingSeats))
		else if(remainingSeats < 0)
			showMembersAlert(resources.getQuantityString(R.plurals.text_too_many_members, -remainingSeats, -remainingSeats))
	}

	private fun showMembersInvalidAlert() = showMembersAlert(getString(R.string.text_configuration_invalid))

	private fun showMembersAlert(message: String)
	{
		ctx.alertDialog {
			setTitle(getString(R.string.alert_cannot_draw_title))
			setMessage(message)
			setNegativeButton(R.string.action_cancel) { dialog, _ ->
				dialog.dismiss()
			}
		}.show()
	}

	private fun updateErrorBanner()
	{
		val remainingSeats = members?.getRemainingSeatsForMembers()
		val valid = members?.isConfigurationValidForMembers()
		textMembersError.text = when
		{
			remainingSeats == null -> getString(R.string.text_loading)
			remainingSeats > 0 -> resources.getQuantityString(R.plurals.text_too_few_members, remainingSeats, remainingSeats)
			remainingSeats < 0 -> resources.getQuantityString(R.plurals.text_too_many_members, -remainingSeats, -remainingSeats)
			valid != true -> getString(R.string.text_configuration_invalid)
			else -> null
		}
	}

	private fun showMemberAddDialog() {
		memberAddDialog {
			setNameValidityChecker { name -> when {
				name.isBlank() -> MemberAddDialogBuilder.Validity.EMPTY
				members.map { it.name }.contains(name) -> MemberAddDialogBuilder.Validity.BUSY
				else -> MemberAddDialogBuilder.Validity.VALID
			} }
			setOnAddListener { name ->
				memberAddListener?.invoke(MemberEntity(name, true))
			}
		}.show()
	}
}
