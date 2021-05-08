package ru.mail.polis.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mail.polis.dao.ApartmentED
import ru.mail.polis.dao.ApartmentService
import ru.mail.polis.dao.IApartmentService

class AddApartmentViewModel : ViewModel() {

    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    val list = LinkedHashSet<Bitmap>()

    fun addApartment(apartmentED: ApartmentED) {
        viewModelScope.launch(Dispatchers.IO) {
            apartmentService.addApartment(apartmentED)
        }
    }
}
