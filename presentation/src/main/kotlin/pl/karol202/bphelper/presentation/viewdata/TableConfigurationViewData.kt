package pl.karol202.bphelper.presentation.viewdata

import pl.karol202.bphelper.domain.entity.TableConfiguration
import java.io.Serializable

sealed class TableConfigurationViewData : Serializable
{
	data class TwoTables(val first: TableViewData,
	                     val second: TableViewData) : TableConfigurationViewData()

	data class FourTables(val first: TableViewData,
	                      val second: TableViewData,
	                      val third: TableViewData,
	                      val fourth: TableViewData) : TableConfigurationViewData()
}

fun TableConfiguration.toViewData() =
	when(tables.size)
	{
		2 -> TableConfigurationViewData.TwoTables(tables[0].toViewData(), tables[1].toViewData())
		4 -> TableConfigurationViewData.FourTables(tables[0].toViewData(), tables[1].toViewData(),
		                                           tables[2].toViewData(), tables[3].toViewData())
		else -> throw IllegalArgumentException("Cannot convert table configuration: $this")
	}
