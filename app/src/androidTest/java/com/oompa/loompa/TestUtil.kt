package com.oompa.loompa

import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.data.model.OompaLoompaFavorite

object TestUtil {
    fun createOompaLoompa(
        id: Long = 1,
        profession: String? = null,
        gender: String? = null,
    ): OompaLoompa {
        return OompaLoompa(
            id,
            firstName = "firstName$id",
            lastName = "lastName$id",
            favorite = OompaLoompaFavorite(
                color = "color$id",
                food = "food$id",
                song = "song$id",
                randomString = "randomString$id",
            ),
            gender = gender ?: "F",
            profession = profession ?: "Medic",
            image = "https://url$id.com",
            email = "email$id@oompa.loompa.com",
            age = 40,
            height = 90,
            country = "Loompalandia",
        )
    }
}