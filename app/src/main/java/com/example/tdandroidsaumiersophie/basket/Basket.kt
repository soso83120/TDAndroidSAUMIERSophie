package com.example.tdandroidsaumiersophie.basket

import com.example.tdandroidsaumiersophie.network.Dish
import java.io.Serializable

class Basket(val items: List<BasketItem>): Serializable{
}
// serializable la rendre encodable et décodable
class BasketItem(val dish: Dish, val count: Int): Serializable{

}