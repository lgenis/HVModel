package test;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import dao.DBConnector;
import dao.DBManager;
import model.Mascota;
import model.Person;

public class TestFindLikeByOwnerName {
	DBConnector dbConnector; 

	@Before
	public void init(){
		dbConnector =   new DBConnector(); 	
		dbConnector.connect();
		dbConnector.deleteAll(Person.class);
		dbConnector.close();
	}
	
	@Test
	public void testSbConection(){	
		dbConnector.connect();
		 	Assert.assertNotNull(dbConnector.getEntityManager()); 
		dbConnector.close();
	}
	
	@Test
	public void testFindLikeByOwnerName(){
		
		String ownerName = "ju";
		Person person1 = getMockPerson("Jose", "Garc�a");
		Person person2 = getMockPerson("Juan", "Perez");
		Person person3 = getMockPerson("Marc", "Bert");
		Person person4 = getMockPerson("Julian", "Reton");
		
		Mascota mascota1 = getMockMascota("Rex", "canido");
		Mascota mascota2 = getMockMascota("Garfield", "felino");
		Mascota mascota3 = getMockMascota("Piolin", "ave");
		Mascota mascota4 = getMockMascota("Ratata", "roedor");
		
		dbConnector.connect();
		
			dbConnector.getEntityManager().getTransaction().begin();
	
				dbConnector.getEntityManager().persist(person1);
				dbConnector.getEntityManager().persist(person2);
				dbConnector.getEntityManager().persist(person3);
				dbConnector.getEntityManager().persist(person4);
				
				person1.getMascotas().add(mascota1);
					mascota1.setOwner(person1);
				person2.getMascotas().add(mascota2);
					mascota2.setOwner(person2);
				person3.getMascotas().add(mascota3);
					mascota3.setOwner(person3);
				person4.getMascotas().add(mascota4);
					mascota4.setOwner(person4);
			
			dbConnector.getEntityManager().getTransaction().commit();
			
	
			HashSet<Person> list = dbConnector.findLikeByOwnerName(ownerName);
			
		dbConnector.close(); 	
		
		

		Assert.assertEquals(2, list.size());
		
		ArrayList<Person> listArray = new ArrayList<>(list);
		
		Assert.assertEquals("Juan", listArray.get(0).getName());
		Assert.assertEquals("Julian", listArray.get(1).getName());
	}
	
	
	// MOCKLIST
	
	private Person getMockPerson(String name, String surname) {
		 Person person = new Person();
		 person.setName(name);
		 person.setSurname(surname);
		 person.setEmail("prueba123@gmail.com");
		 person.setPhone("000000000");
		 person.setAddress("Calle falsa 123");
		 return person;
	}
	
	private	Mascota getMockMascota(String name, String type) {
		Mascota mascota = new Mascota();
		mascota.setName(name);
		mascota.setTypeClass(type);
		mascota.setLength(1.1f);
		mascota.setHeight(0.9f);
		mascota.setWeight(123.321f);
		return  mascota;
	}
	
	
}