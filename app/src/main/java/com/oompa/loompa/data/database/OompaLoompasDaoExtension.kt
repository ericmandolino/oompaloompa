package com.oompa.loompa.data.database

import androidx.paging.PagingSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.data.model.OompaLoompaFilter

fun OompaLoompasDao.getOompaLoompas(
    filter: OompaLoompaFilter,
    queryBuilder: OompaLoompaQueryBuilder<SupportSQLiteQuery>
): PagingSource<Int, OompaLoompa> {
    return this.getOompaLoompas(queryBuilder.buildQuery(filter))
}