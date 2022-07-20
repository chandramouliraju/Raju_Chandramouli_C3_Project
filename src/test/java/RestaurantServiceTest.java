import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

     RestaurantService service = new RestaurantService();
     Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setupRestaurant(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        String name = "Amelie's cafe";
        Restaurant restaurant = service.findRestaurantByName(name);
        assertEquals(name, restaurant.getName());
        assertEquals(LocalTime.of(10,30),restaurant.openingTime);
        assertEquals(LocalTime.of(22,00),restaurant.closingTime);
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        String name = "Emelia's Cafe";
        assertThrows(restaurantNotFoundException.class,()->service.findRestaurantByName(name));
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void all_menu_items_from_the_restaurants_when_selected_should_cost_388rs(){
        List<String> menuItemSelected = new ArrayList<String>();
        menuItemSelected.add("Sweet corn soup");
        menuItemSelected.add("Vegetable lasagne");
        String orderValue = service.calculateOrderValue(menuItemSelected);
        assertEquals("Your order will cost: ₹388", orderValue);
    }

    @Test
    public void order_value_should_be_119_when_selected_sweet_corn_soup(){
        List<String> menuItemSelected = new ArrayList<String>();
        menuItemSelected.add("Sweet corn soup");
        String orderValue = service.calculateOrderValue(menuItemSelected);
        assertEquals("Your order will cost: ₹119", orderValue);
    }

    @Test
    public void order_value_should_be_zero_when_none_selected_in_the_menu(){
        List<String> menuItemSelected = new ArrayList<String>();
        String orderValue = service.calculateOrderValue(menuItemSelected);
        assertEquals("Your order will cost: ₹0", orderValue);
    }
}