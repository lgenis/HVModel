package test;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBConnector;
import dao.DBManager;
import model.Mascota;
import model.Person;

public class TestUpdate {
	DBConnector dbConnector; 

	@Before
	public void init(){
		dbConnector =   new DBConnector(); 
		
		dbConnector.connect();
		dbConnector.deleteAll(Person.class); 		
		dbConnector.close();
	}
	
	@Test
	public void testUpdate(){
		DBManager dbManager = 
				new DBManager();
		Mascota mascota2 = getMockMascota("Mickey");
		Person person2 = getMockPerson("Mike", "Mandela");
		
		
		//Insert without insert mock person & pet
		dbManager.connect();
		   EntityManager entityManager = dbManager.getEntityManager();  
		   
		        entityManager.getTransaction().begin();
					        
		        entityManager.persist(person2);
		        	person2.getMascotas().add(mascota2); 
		        	
		        	mascota2.setOwner(person2);
			
		        entityManager.getTransaction().commit();

		dbManager.close();
		
		//Get info
		dbManager.connect();		

		Person person2update = (Person) dbManager.find(Person.class, person2.getId());
		Mascota masc2update = (Mascota) dbManager.find(Mascota.class, mascota2.getId());
		dbManager.close();
		
		
		//Update object
		person2update.setName("Peter");
		person2update.setSurname("Parra");
		
		masc2update.setName("Perico");
		//masc2update.setOwner(person2update);
		
		//Update db
		DBConnector dbConn = new DBConnector();

		dbManager.connect();
			dbConn.update(person2update);
			dbConn.update(masc2update);
			
			
			Assert.assertEquals(1, dbManager.selectEqual(Person.class, "name", "Peter").size());
			Assert.assertEquals("Peter", dbManager.selectEqual(Person.class, "name", "Peter").get(0).getName());
			Assert.assertEquals("Parra", dbManager.selectEqual(Person.class, "surname", "Parra").get(0).getSurname());
			
			Assert.assertEquals(1, dbManager.selectAll(Mascota.class).size());
			Assert.assertEquals(1, dbManager.selectEqual(Mascota.class, "name", "Perico").size());
			Assert.assertEquals(1, dbManager.selectEqual(Mascota.class, "owner.name", "Peter").size());
			
			dbManager.close();
		
		
		
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
		masc.setTypeClass(name.length()%2==0?"Roedor":"Ave");
		return masc;
		
	}
	
}
