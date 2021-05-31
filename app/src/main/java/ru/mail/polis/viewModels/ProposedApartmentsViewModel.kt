package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.apartments.ApartmentService
import ru.mail.polis.dao.apartments.IApartmentService
import ru.mail.polis.dao.propose.IProposeService
import ru.mail.polis.dao.propose.ProposeService
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService
import ru.mail.polis.notification.NotificationKeeperException

class ProposedApartmentsViewModel : ViewModel() {
    private val apartmentService: IApartmentService = ApartmentService.getInstance()
    private val proposeService: IProposeService = ProposeService.getInstance()
    private val userService: IUserService = UserService()

    @Throws(NotificationKeeperException::class)
    suspend fun fetchApartmentsByRenterEmail(email: String): List<ApartmentED> {
        try {
            val proposeList = withContext(Dispatchers.IO) {
                proposeService.findRenterEmail(email)
            }

            if (proposeList.isEmpty()) {
                return emptyList()
            }

            return withContext(Dispatchers.IO) {
                apartmentService.findByEmails(
                    proposeList.map { proposeED -> proposeED.ownerEmail!! }
                        .toSet()
                )
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed fetching apartments by renter email: $email",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }

    suspend fun fetchUsers(emailList: Set<String>): List<UserED> {
        try {
            return withContext(Dispatchers.IO) {
                userService.findUsersByEmails(emailList)
            }
        } catch (e: DaoException) {
            throw NotificationKeeperException(
                "Failed fetching users by email list: $emailList",
                e,
                NotificationKeeperException.NotificationType.DAO_ERROR
            )
        }
    }
}
