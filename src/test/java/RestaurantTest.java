import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RestaurantTest {
    //REFACTOR ALL THE REPEATED LINES OF CODE
    //Declare Restaurant which is used in almost all the test cases
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    Restaurant restaurant;

    {
        restaurant = new Restaurant("Shoeb Saifee Cafe", "Indore", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 100);
        restaurant.addToMenu("Vegetable lasagne", 200);

    }



    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        //ACT
        Restaurant spyRestaurant = Mockito.spy(restaurant);

        //Arrange
        when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("18:00:00"));

        //Assert
        assertTrue(spyRestaurant.isRestaurantOpen());

        //Check edge case that the restaurant should be open at 10:30:00
        when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:30:00"));
        assertTrue(spyRestaurant.isRestaurantOpen());


    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){


        //ACT
        Restaurant spyRestaurant = Mockito.spy(restaurant);

        //Arrange
        when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("23:00:00"));

        //Assert
        assertFalse(spyRestaurant.isRestaurantOpen());

        //Check edge case that the restaurant should be closed at 22:00:00
        when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("22:00:00"));
        assertFalse(spyRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    //New Test Case to check if the total cost of all the items in the menu is equal to sum of the price of all the items
    @Test
    public void selecting_items_from_menu_and_checking_if_the_total_cost_is_equal_to_the_sum_of_price_of_all_items_added_in_the_menu(){

        //Act
        List<Item> selectedItems = new ArrayList<>();
        Item temp = restaurant.findItemByName("Sweet corn soup");
        if (temp!=null)
            selectedItems.add(temp);
        temp = restaurant.findItemByName("Vegetable lasagne");
        if (temp!=null)
            selectedItems.add(temp);
        // Arrange
        int totalCost = restaurant.getTotalCostOfItems(selectedItems);

        // Assert
        assertEquals(totalCost,300);

        //Add more item and check the sum again
        restaurant.addToMenu("Indori Kabab", 300);
        temp = restaurant.findItemByName("Indori Kabab");
        if (temp!=null)
            selectedItems.add(temp);
        totalCost = restaurant.getTotalCostOfItems(selectedItems);
        assertEquals(totalCost,600);



    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}