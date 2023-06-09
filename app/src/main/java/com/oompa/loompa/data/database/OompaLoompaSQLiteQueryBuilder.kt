package com.oompa.loompa.data.database

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.oompa.loompa.data.model.OompaLoompaFilter

class OompaLoompaSQLiteQueryBuilder : OompaLoompaQueryBuilder<SupportSQLiteQuery> {
    override fun buildQuery(filter: OompaLoompaFilter): SupportSQLiteQuery {
        return createOompaLoompasQuery(filter)
    }

    private fun createOompaLoompasQuery(oompaLoompaFilter: OompaLoompaFilter): SupportSQLiteQuery {
        val whereBuilder = StringBuilder()

        if (oompaLoompaFilter.genders.isNotEmpty()) {
            addWhereFilterClause(whereBuilder, "gender In (${oompaLoompaFilter.genders.joinToString { "'$it'" }})")
        }

        if (oompaLoompaFilter.professions.isNotEmpty()) {
            addWhereFilterClause(whereBuilder, "profession In (${oompaLoompaFilter.professions.joinToString { "'$it'" }})")
        }

        return if (whereBuilder.isNotEmpty()) {
            SimpleSQLiteQuery("Select * From oompa_loompas Where $whereBuilder Order By id")
        } else {
            SimpleSQLiteQuery("Select * From oompa_loompas Order By id")
        }
    }

    private fun addWhereFilterClause(filterBuilder: StringBuilder, filter: String) {
        if (filterBuilder.isNotEmpty()) {
            filterBuilder.append(" And ")
        }
        filterBuilder.append(filter)
    }
}