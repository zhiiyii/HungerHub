package my.edu.tarc.hungerhub.ui.donation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Donor (
    @PrimaryKey var cardNumber : String,
    var cardHolderName:String,
    var cardCVC: String,
    var donateAmount: String
)
