package com.culqilib.restServices.deserializer

import com.fasterxml.jackson.annotation.JsonProperty

class TokenDeserializer {
    class CardRequest(@field:JsonProperty("card_number") var cardNumber: String,
                          @field:JsonProperty var cvv: String,
                          @field:JsonProperty("expiration_month") var expirationMonth: Int,
                          @field:JsonProperty("expiration_year") var expirationYear: Int,
                          @field:JsonProperty var email: String)
}