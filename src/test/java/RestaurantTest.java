import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    @Spy
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeEach
    public void populateRestaurant(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        restaurant = Mockito.spy(restaurant);
        Mockito.when(restaurant.getCurrentTime()).thenReturn(LocalTime.of(10,30));
        boolean isRestaurantOpen = restaurant.isRestaurantOpen();
        assertTrue(isRestaurantOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        restaurant = Mockito.spy(restaurant);
        Mockito.when(restaurant.getCurrentTime()).thenReturn(LocalTime.of(9,30));
        boolean isRestaurantOpen = restaurant.isRestaurantOpen();
        assertFalse(isRestaurantOpen);

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
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void all_menu_items_from_the_restaurants_when_selected_should_cost_388rs(){
        List<String> menuItemSelected = new ArrayList<String>();
        menuItemSelected.add("Sweet corn soup");
        menuItemSelected.add("Vegetable lasagne");
        String orderValue = restaurant.calculateOrderValue(menuItemSelected);
        assertEquals("Your order will cost: ₹388", orderValue);
    }

    @Test
    public void order_value_should_be_119_when_selected_sweet_corn_soup(){
        List<String> menuItemSelected = new ArrayList<String>();
        menuItemSelected.add("Sweet corn soup");
        String orderValue = restaurant.calculateOrderValue(menuItemSelected);
        assertEquals("Your order will cost: ₹119", orderValue);
    }

    @Test
    public void order_value_should_be_zero_when_none_selected_in_the_menu(){
        List<String> menuItemSelected = new ArrayList<String>();
        String orderValue = restaurant.calculateOrderValue(menuItemSelected);
        assertEquals("Your order will cost: ₹0", orderValue);
    }
}