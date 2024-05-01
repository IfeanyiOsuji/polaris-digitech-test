package com.ify.box_drone;

import com.ify.box_drone.exceptions.BoxCannotBeLoadedException;
import com.ify.box_drone.models.dtos.BoxRequest;
import com.ify.box_drone.models.entities.Box;
import com.ify.box_drone.models.entities.Item;
import com.ify.box_drone.models.enums.State;
import com.ify.box_drone.repository.BoxRepository;
import com.ify.box_drone.services.box.BoxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class BoxServiceTest {

	@Autowired
	private BoxService boxService;
	@Autowired
	private BoxRepository repository;

	private final DataSource dataSource = DataSourceBuilder.create()
			.url("jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1")
			.username("sa")
			.password("123")
			.build();

	private static final Logger log = LoggerFactory.getLogger(BoxServiceTest.class);

	@Test
	@BeforeEach
	void contextLoads() {

	}

	@Test
	public void testDbConnection() {
		try {
			Connection connection = dataSource.getConnection();
			assertThat(connection).isNotNull();
		} catch (SQLException exception) {
			log.error("An exception occurred -> {}", exception.getMessage());
		}
	}

	 @Test
	 @DirtiesContext
	 void testThatBoxIsCreated(){

		// Given
	 	BoxRequest boxRequest = new BoxRequest(50, 70.5);

		 //when
	 	Box box = boxService.createBox(boxRequest);

		 //assert
	 	assertThat(box).isNotNull();
	 	log.info("Box -> {}", box);
	 }

	 @Test
	@DirtiesContext
	 void testThatItemsCanBeLoadedIntoBox() throws BoxCannotBeLoadedException {
		 // Given
		 BoxRequest boxRequest = new BoxRequest(50, 70.5);
		 Box box = boxService.createBox(boxRequest);

		 Item item = new Item("Panadol", "AAA_122", 5.5);
		 Item item1 = new Item("Cafein", "AAA_133", 4.5);
		 Item item2 = new Item("Procold", "AAA_144", 5.7);
		 Item item3= new Item("Bodqzol", "AAA_155", 3.2);
		 List<Item> items = new ArrayList<>();
		 items.add(item);
		 items.add(item1);
		 items.add(item2);
		 items.add(item3);


		 // when
		 boxService.loadBoxWithItems(box.getTxref(), items);
		 Box loadedBox = repository.findByTxref(box.getTxref());

		 // assert
		 assertThat(loadedBox.getLoadedItems().size()).isEqualTo(4);

		 log.info("Loaded Box -> {}", loadedBox);

	 }
	@Test
	@DirtiesContext
	void test_That_Box_Cannot_Carry_Weight_More_Than_500(){
		//given
		 BoxRequest boxRequest = new BoxRequest(50, 70.5);
		 Box box = boxService.createBox(boxRequest);

		 Item item = new Item("Panadol", "AAA-122", 400.5);
		 Item item1 = new Item("Cafein", "AAA-133", 50.5);
		 Item item2 = new Item("Procold", "AAA-144", 50.7);
		 Item item3= new Item("Bodqzol", "AAA-155", 3.2);
		 List<Item> items = new ArrayList<>();
		 items.add(item);
		 items.add(item1);
		 items.add(item2);
		 items.add(item3);

		// when
		try{
			boxService.loadBoxWithItems(box.getTxref(), items);
		}
		catch (BoxCannotBeLoadedException ex){}

		Box loadedBox = repository.findByTxref(box.getTxref());
		log.info("Loaded Box2 -> {}", loadedBox);
		// assert
		assertThat(loadedBox.getLoadedItems().size()).isEqualTo(2); // The box already has an initial weight of 50


	 }

	@Test
	@DirtiesContext
	void test_That_Box_Cannot_Be_Loaded_With_LessThan_25pcent_Battery(){
		//given
		BoxRequest boxRequest = new BoxRequest(50, 24.9);
		Box box = boxService.createBox(boxRequest);

		Item item = new Item("Panadol", "AAA-122", 400.5);
		Item item1 = new Item("Cafein", "AAA-133", 50.5);
		Item item2 = new Item("Procold", "AAA-144", 50.7);
		Item item3= new Item("Bodqzol", "AAA-155", 3.2);
		List<Item> items = new ArrayList<>();
		items.add(item);
		items.add(item1);
		items.add(item2);
		items.add(item3);

		// when
		try {
			boxService.loadBoxWithItems(box.getTxref(), items);
		}
		catch (BoxCannotBeLoadedException ex){

		}
		Box loadedBox = repository.findByTxref(box.getTxref());
		// assert
		assertThat(loadedBox.getLoadedItems().size()).isEqualTo(0); // The box already has an initial weight of 50
		log.info("Loaded Box3-> {}", loadedBox);
	}

	@Test
	@DirtiesContext
    void test_Throws_Exception_If_Battery_Level_Is_lower_Than_25_Percent(){
		//given
		BoxRequest boxRequest = new BoxRequest(50, 24.9);
		Box box = boxService.createBox(boxRequest);

		//when
		Item item = new Item("Panadol", "AAA-122", 400.5);
		Item item1 = new Item("Cafein", "AAA-133", 50.5);
		Item item2 = new Item("Procold", "AAA-144", 50.7);
		Item item3= new Item("Bodqzol", "AAA-155", 3.2);
		List<Item> items = new ArrayList<>();
		items.add(item);
		items.add(item1);
		items.add(item2);
		items.add(item3);

		// when
		assertThrows(BoxCannotBeLoadedException.class, ()->boxService.loadBoxWithItems(box.getTxref(), items));
	}

	@Test
	void test_That_Box_State_Is_Not_LOADING_If_Battery_Level_Is_lower_Than_25_Percent(){
		//given
		BoxRequest boxRequest = new BoxRequest(50, 24.9);
		Box box = boxService.createBox(boxRequest);

		Item item = new Item("Panadol", "AAA-122", 400.5);
		Item item1 = new Item("Cafein", "AAA-133", 50.5);
		Item item2 = new Item("Procold", "AAA-144", 50.7);
		Item item3= new Item("Bodqzol", "AAA-155", 3.2);
		List<Item> items = new ArrayList<>();
		items.add(item);
		items.add(item1);
		items.add(item2);
		items.add(item3);

		// when
		try {
			boxService.loadBoxWithItems(box.getTxref(), items);
		}
		catch (BoxCannotBeLoadedException ex){

		}
		Box loadedBox = repository.findByTxref(box.getTxref());

		//assert

		assertThat(loadedBox.getState()).isNotEqualTo(State.LOADING);
	}
}
