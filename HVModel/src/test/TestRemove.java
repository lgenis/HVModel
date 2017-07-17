package test;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBConnector;
import dao.DBManager;

import model.Mascota;
import model.Person;

public class TestRemove {
	DBConnector dbConnector; 

	@Before
	public void init(){
		dbConnector =   new DBConnector(); 
		
		
		dbConnector.connect();
		dbConnector.deleteAll(Person.class); 		
		dbConnector.close();
	}
	
	
	@Test
	public void testRemove(){
		boolean result = true;
		DBManager dbManager = 
				new DBManager();
		Mascota mascota1 = getMockMascota("Felix");
		Mascota mascota11 = getMockMascota("Firulais");
		Person person1 = getMockPerson("Federico", "Fernandez");
		Mascota mascota2 = getMockMascota("Mickey");
		Person person2 = getMockPerson("Mike", "Mandela");
		
	
		
		
	
			dbManager.connect();
			   EntityManager entityManager = dbManager.getEntityManager();  
			   
			        entityManager.getTransaction().begin();
				
			        entityManager.persist(person1);			      
				        person1.getMascotas().add(mascota1); 
				        person1.getMascotas().add(mascota11); 
				        
				        mascota1.setOwner(person1);
						mascota11.setOwner(person1);
				        
			        entityManager.persist(person2);
			        	person2.getMascotas().add(mascota2); 
			        	
			        	mascota2.setOwner(person2);
				
			        entityManager.getTransaction().commit();

			dbManager.close();
		
			
			
			dbManager.connect();		
					Person results1 = (Person) dbManager.find(Person.class, person1.getId());
					Person results2 = (Person) dbManager.find(Person.class, person2.getId());
					Mascota resultMasc2 = (Mascota) dbManager.find(Mascota.class, mascota2.getId());
			dbManager.close();
			
			
			Assert.assertEquals(2, results1.getMascotas().size());
			Assert.assertEquals(1, results2.getMascotas().size());
			
			
			DBConnector dbConn = new DBConnector(); 	
			dbConn.remove(results1);
				
			dbManager.connect();	
		
			Assert.assertEquals(1, dbManager.selectAll(Person.class).size());
			Assert.assertEquals(1, dbManager.selectAll(Mascota.class).size());
			Assert.assertEquals(0, dbManager.selectEqual(Mascota.class, "owner.name", "Federico").size());
			
			dbManager.close();
			
			//DBConnector dbConn = new DBConnector(); 	
			dbConn.remove(resultMasc2);
			
			dbManager.connect();	
			
			Assert.assertEquals(1, dbManager.selectAll(Person.class).size());
			Assert.assertEquals(0, dbManager.selectAll(Mascota.class).size());
			
			
			dbManager.close();
			
	}
	
	@Test(expected=RuntimeException.class)
	public void testNullRemove(){
		boolean result = true;
		DBManager dbManager = 
				new DBManager();
		Mascota mascota1 = getMockMascota("Felix");
		Mascota mascota11 =null;
		Person person1 = getMockPerson("Federico", "Fernandez");

		
	
		
		
	
			dbManager.connect();
			   EntityManager entityManager = dbManager.getEntityManager();  
			   
			        entityManager.getTransaction().begin();
				
			        entityManager.persist(person1);			      
				        person1.getMascotas().add(mascota1); 
				        person1.getMascotas().add(mascota11); 
				        
				        mascota1.setOwner(person1);
						mascota11.setOwner(person1);
				        

				
			        entityManager.getTransaction().commit();

			dbManager.close();
		
			
			
			dbManager.connect();		
					Person results1 = (Person) dbManager.find(Person.class, person1.getId());

			dbManager.close();
			
			
			Assert.assertEquals(1, results1.getMascotas().size());

			
			
			DBConnector dbConn = new DBConnector(); 	
			dbConn.remove(results1);
				
			dbManager.connect();	
		
			Assert.assertEquals(0, dbManager.selectAll(Person.class).size());
			Assert.assertEquals(0, dbManager.selectAll(Mascota.class).size());

			
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
		masc.setTypeClass(name.length()%2==0?"Roedor":"Gato");
		return masc;
		
	}
	
		
}
