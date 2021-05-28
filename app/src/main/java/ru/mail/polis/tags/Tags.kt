package ru.mail.polis.tags

import ru.mail.polis.R
import ru.mail.polis.room.RoomCount

enum class Tags(val imageOnClick: Int, val imageNotClick: Int) {
    PETS(R.drawable.ic_paw, R.drawable.ic_paw_not_click),
    NOISE(R.drawable.ic_drum, R.drawable.ic_drum_not_click),
    KIDS(R.drawable.ic_kid, R.drawable.ic_kid_not_click),
    CIGARETTE(R.drawable.ic_cigarette, R.drawable.ic_cigarette_not_click);

    companion object {
        fun from(image: Int): Tags {
            values().forEach {
                if (it.imageNotClick == image || it.imageOnClick == image) {
                    return it
                }
            }
            throw IllegalArgumentException("No enum")
        }
    }
}
