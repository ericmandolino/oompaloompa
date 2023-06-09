package com.oompa.loompa.data.database

import com.oompa.loompa.data.model.OompaLoompaFilter
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class OompaLoompaSQLiteQueryBuilderTest(
    private val filter: OompaLoompaFilter,
    private val expectedQuery: String,
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}")
        fun data(): Iterable<Array<Any>> {
            return arrayListOf(
                arrayOf(
                    OompaLoompaFilter(),
                    "Select * From oompa_loompas Order By id",
                ),
                arrayOf(
                    OompaLoompaFilter(
                        genders = listOf("M"),
                    ),
                    "Select * From oompa_loompas Where gender In ('M') Order By id",
                ),
                arrayOf(
                    OompaLoompaFilter(
                        professions = listOf("Medic"),
                    ),
                    "Select * From oompa_loompas Where profession In ('Medic') Order By id",
                ),
                arrayOf(
                    OompaLoompaFilter(
                        genders = listOf("F"),
                        professions = listOf("Medic", "GemCutter"),
                    ),
                    "Select * From oompa_loompas Where gender In ('F') And profession In ('Medic', 'GemCutter') Order By id",
                ),
            )
        }
    }

    @Test
    fun buildQuery_returnsExpectedQuery() {
        // Arrange
        val queryBuilder = OompaLoompaSQLiteQueryBuilder()

        // Act
        val result = queryBuilder.buildQuery(filter)

        // Assert
        Assert.assertEquals(expectedQuery, result.sql)
    }
}