import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    @BeforeEach
    public void initRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("pasta",112);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant restaurantSpy = Mockito.spy(restaurant);
        Mockito.doReturn(LocalTime.parse("20:00:00")).when(restaurantSpy).getCurrentTime();
        assertTrue(restaurantSpy.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant restaurantSpy = Mockito.spy(restaurant);
        Mockito.doReturn(LocalTime.parse("10:00:00")).when(restaurantSpy).getCurrentTime();
        assertFalse(restaurantSpy.isRestaurantOpen());
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
    // Added below testcases to check the totalOrderValue method

    @Test
    public void total_value_of_selected_items_from_menu_is_500(){

        List<String> selectedItems = new ArrayList<>();
        selectedItems.add("pasta");
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");
        assertEquals(500,restaurant.totalOrderValue(selectedItems));
    }

    @Test
    public void total_order_value_is_0_if_no_item_is_selected_from_menu(){

        List<String> selectedItems = new ArrayList<>();
        assertEquals(0,restaurant.totalOrderValue(selectedItems));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}