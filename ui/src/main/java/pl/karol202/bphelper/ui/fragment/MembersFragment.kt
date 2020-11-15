package pl.karol202.bphelper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_members.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pl.karol202.bphelper.presentation.viewdata.MemberViewData
import pl.karol202.bphelper.presentation.viewdata.TableConfigurationErrorViewData
import pl.karol202.bphelper.presentation.viewmodel.MembersViewModel
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.adapter.MemberAdapter
import pl.karol202.bphelper.ui.dialog.MemberAddDialogBuilder
import pl.karol202.bphelper.ui.extensions.*
import pl.karol202.bphelper.ui.viewmodel.AndroidMembersViewModel

class MembersFragment : Fragment()
{
	private val navController by lazy { NavHostFragment.findNavController(this) }

	private val membersViewModel by sharedViewModel<AndroidMembersViewModel>()

	private val membersAdapter = MemberAdapter(onMemberAdd = { showMemberAddDialog() },
	                                           onMemberUpdate = { membersViewModel.updateMember(it) },
	                                           onMemberRemove = { showMemberRemoveDialog(it) })

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.fragment_members, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		observeMembers()
		observeTableDrawResult()

		initRecyclerView()
		initDrawButton()
		//updateErrorBanner()
	}

	private fun observeMembers()
	{
		membersViewModel.allMembers.collectIn(lifecycleScope) { members ->
			membersAdapter.members = members
		}
	}

	private fun observeTableDrawResult()
	{
		membersViewModel.tableDrawResult.handleEventsIn(lifecycleScope) { result ->
			when(result)
			{
				is MembersViewModel.TableDrawResult.GoToTables -> navigateToTablesFragment()
				is MembersViewModel.TableDrawResult.Error -> showErrorSnackbar(result.error)
			}
		}
	}

	private fun initRecyclerView()
	{
		recycler_members.layoutManager = LinearLayoutManager(ctx)
		recycler_members.adapter = membersAdapter
		recycler_members.addItemDecoration(DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL))
		recycler_members.simpleItemAnimator?.supportsChangeAnimations = false
	}

	private fun initDrawButton()
	{
		button_draw.setOnClickListener {
			membersViewModel.tryToDrawTables()
		}
	}

	private fun navigateToTablesFragment()
	{
		println("tables")
		/*if(navController.currentDestination?.id != R.id.membersFragment) return
		val action = MembersFragmentDirections.toTablesFragment(tableConfigurationType.name)
		navController.navigate(action)*/
	}

	private fun showErrorSnackbar(error: TableConfigurationErrorViewData) = when(error)
	{
		is TableConfigurationErrorViewData.TooFewMembers ->
			showSnackbar(resources.getQuantityString(R.plurals.text_too_few_members,
			                                         error.missingAmount, error.missingAmount))
		is TableConfigurationErrorViewData.TooManyMembers ->
			showSnackbar(resources.getQuantityString(R.plurals.text_too_many_members,
			                                         error.exceedingAmount, error.exceedingAmount))
		is TableConfigurationErrorViewData.ConfigurationImpossible ->
			showSnackbar(R.string.text_configuration_invalid)
	}

	private fun showMemberAddDialog() {
		MemberAddDialogBuilder(
			context = ctx,
		    nameValidator = { name -> when {
			    name.isBlank() -> MemberAddDialogBuilder.Validity.EMPTY
			    else -> MemberAddDialogBuilder.Validity.VALID
		    } },
		    onApply = { name ->
			    membersViewModel.addMember(name)
		    }).show()
	}

	private fun showMemberRemoveDialog(member: MemberViewData)
	{
		alertDialog {
			setTitle(getString(R.string.alert_remove_member_title))
			setMessage(getString(R.string.alert_remove_member, member.name))
			setPositiveButton(R.string.action_remove) { _, _ ->
				membersViewModel.removeMember(member.id)
			}
			setNegativeButton(R.string.action_cancel, null)
		}.show()
	}
}
