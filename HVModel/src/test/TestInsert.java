package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBConnector;
import dao.DBManager;
import model.Mascota;
import model.Person;

public class TestInsert {
	DBConnector dbConnector; 

	@Before
	public void init(){
		dbConnector =   new DBConnector();
		dbConnector.connect();
		dbConnector.deleteAll(Person.class);
		dbConnector.close();
	}
	
	@Test
	public void testInsert(){
		Mascota mascota11 = getMockMascota("Firulais");
		Person person1 = getMockPerson("Federico", "Fernandez");
		
		DBManager dbManager = 
				new DBManager();
		
		dbConnector.insert(person1, mascota11);
		
		dbManager.connect();		
		Person results1 = (Person) dbManager.find(Person.class, person1.getId());
		
		Mascota resultMasc1 = (Mascota) dbManager.find(Mascota.class, mascota11.getId());
		dbManager.close();
		
		
		Assert.assertEquals(person1.getName(), results1.getName());
		Assert.assertEquals(person1.getSurname(), results1.getSurname());
		Assert.assertEquals(person1.getPhone(), results1.getPhone());
		Assert.assertEquals(person1.getAddress(), results1.getAddress());
		Assert.assertEquals(mascota11.getName(), resultMasc1.getName());
		Assert.assertEquals(mascota11.getHeight(), resultMasc1.getHeight(), 0.1);
		Assert.assertEquals(mascota11.getWeight(), resultMasc1.getWeight(), 0.1);
		Assert.assertEquals(mascota11.getLength(), resultMasc1.getLength(), 0.1);
		
		
		
	}
	
	
	
	@Test(expected = RuntimeException.class)
	public void testNullPerson() {
		Mascota mascota11 = getMockMascota("Firulais");
		Person person1 = getMockPerson("Federico", "Fernandez");
		
		DBManager dbManager = 
				new DBManager();
		
		dbConnector.insert(null, mascota11);
		
	}
	
	@Test
	public void testNullMascota(){
		Mascota mascota11 = null;
		Person person1 = getMockPerson("Federico", "Fernandez");
		
		DBManager dbManager = 
				new DBManager();
		
		dbConnector.insert(person1, mascota11);
		
		dbManager.connect();		
		Person results1 = (Person) dbManager.find(Person.class, person1.getId());
		
		dbManager.close();
		
		Assert.assertEquals(person1.getName(), results1.getName());
		Assert.assertEquals(person1.getSurname(), results1.getSurname());
		Assert.assertEquals(person1.getPhone(), results1.getPhone());
		Assert.assertEquals(person1.getAddress(), results1.getAddress());
		
	}
	
	
	
	private Person getMockPerson(String name, String surname){
		Person pers = new Person();
		pers.setName(name);
		pers.setSurname(surname);
		pers.setAddress("Calle Vive "+ name + " " + name.length() + ", Barcelona" );
		pers.setEmail(name + "@" + surname + ".com");
		pers.setPhone("9311111" + name.length() + surname.length());
		return pers;
		
	}
	
	private Mascota getMockMascota(String name){
		Mascota masc = new Mascota();
		masc.setName(name);
		masc.setHeight(0.1f + (float) name.length()/10f);
		masc.setWeight(20f + (float) name.length());
		masc.setLength(1f+(float) name.length()/10f);
		masc.setTypeClass(name.length()%2==0?"Roedor":"Gato");
		return masc;
		
	}
}
