package com.oompa.loompa.data.database

import com.oompa.loompa.data.model.OompaLoompaFilter

interface OompaLoompaQueryBuilder<T> {
    fun buildQuery(filter: OompaLoompaFilter): T
}