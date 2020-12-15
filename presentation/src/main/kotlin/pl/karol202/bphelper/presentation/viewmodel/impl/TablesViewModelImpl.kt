package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import pl.karol202.bphelper.domain.util.Either
import pl.karol202.bphelper.interactors.usecases.table.DrawTableConfigurationUseCase
import pl.karol202.bphelper.presentation.viewdata.TableConfigurationErrorViewData
import pl.karol202.bphelper.presentation.viewdata.TableConfigurationViewData
import pl.karol202.bphelper.presentation.viewdata.toViewData
import pl.karol202.bphelper.presentation.viewmodel.TablesViewModel

class TablesViewModelImpl(private val drawTableConfigurationUseCase: DrawTableConfigurationUseCase) :
	BaseViewModel(), TablesViewModel
{
	override val tableConfiguration = MutableStateFlow<TableConfigurationViewData?>(null)
	override val error = MutableSharedFlow<TableConfigurationErrorViewData>()

	override fun draw() = launch {
		drawTableConfigurationUseCase().let { result ->
			tableConfiguration.emit(result.rightOrNull()?.toViewData())
			if(result is Either.Left) error.emit(result.value.toViewData())
		}
	}
}
