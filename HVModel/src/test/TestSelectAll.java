package test;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBConnector;
import model.Mascota;
import model.Person;

public class TestSelectAll {
	
	DBConnector dbConnector; 

	@Before
	public void init(){
		dbConnector =   new DBConnector(); 	
		dbConnector.connect();
		dbConnector.deleteAll(Person.class);
		//dbConnector.deleteAll(Mascota.class);
		dbConnector.close();
	}
		
	
	@Test
	public void testSelectAllMascota(){
		
		Person person = getMockPerson("Juan","Aguilar","0034100200300","juan_aguilar@gmail.com","carrer sepulveda");		
				
		Mascota mascota1 = getMockMascota("Garfiel", 1.9f, 2.7f, 5.3f, "Felino");		
		Mascota mascota2 = getMockMascota("Miki", 8.9f,9.7f,10.3f,"Roedor");		
		Mascota mascota3 = getMockMascota("Toby",56.9f,90.7f,60.3f,"Canido");
	
				
		dbConnector.connect();
		dbConnector.getEntityManager().getTransaction().begin();
		
			dbConnector.getEntityManager().persist(person);	
				
			//dbConnector.getEntityManager().persist(mascota1);	
			mascota1.setOwner(person);		
			person.getMascotas().add(mascota1); 
						
			//dbConnector.getEntityManager().persist(mascota2);			
			mascota2.setOwner(person);
			person.getMascotas().add(mascota2); 
			
			//dbConnector.getEntityManager().persist(mascota3);
			mascota3.setOwner(person);			
			person.getMascotas().add(mascota3);							
			
		dbConnector.getEntityManager().getTransaction().commit();
		dbConnector.close();		
		
		
		HashSet<Mascota> recovered = dbConnector.selectAllMascota();
		
		Assert.assertEquals(3, recovered.size());
				
		Assert.assertNotNull(findByNameUser(recovered, "Miki"));
		Assert.assertNotNull(findByNameUser(recovered, "Garfiel"));
		Assert.assertNotNull(findByNameUser(recovered, "Toby"));
		
		
	}
	
	
	
	
	@Test
	public void testSelectAllPersona(){
		
		Person person1 = getMockPerson("Juan","Aguilar","0034100200300","juan_aguilar@gmail.com","carrer sepulveda");	
		Person person2 = getMockPerson("Pablo","Montilla","0034789456123","pabloe@yahoo.es","Horta");
		Person person3 = getMockPerson("Pedro","Zapata","0034666333222","zapata_pedro@poo.es","Arago");
		
		
		Mascota mascota1 = getMockMascota("Garfiel", 1.9f, 2.7f, 5.3f, "Felino");		
		Mascota mascota2 = getMockMascota("Miki", 8.9f,9.7f,10.3f,"Roedor");		
		Mascota mascota3 = getMockMascota("Toby",56.9f,90.7f,60.3f,"Canido");
				
		
		
		dbConnector.connect();		
		dbConnector.getEntityManager().getTransaction().begin();		
		
			dbConnector.getEntityManager().persist(person1);	
			mascota1.setOwner(person1);		
			person1.getMascotas().add(mascota1); 
		
		
			dbConnector.getEntityManager().persist(person2);	
			mascota2.setOwner(person2);		
			person2.getMascotas().add(mascota2); 
			
			
			dbConnector.getEntityManager().persist(person3);	
			mascota3.setOwner(person3);		
			person3.getMascotas().add(mascota3); 
		
		dbConnector.getEntityManager().getTransaction().commit();
		
		dbConnector.close();
		
		
		HashSet<Person> recovered = dbConnector.selectAllPerson();
		
		Assert.assertEquals(3, recovered.size());
				
		Assert.assertNotNull(findByNameUserPerson(recovered, "Juan"));
		Assert.assertNotNull(findByNameUserPerson(recovered, "Pablo"));
		Assert.assertNotNull(findByNameUserPerson(recovered, "Pedro"));
	}
	
	

	private static Person findByNameUserPerson(HashSet<Person> set, String nameMascota){ 
		for(Person person: set){
				if(person.getName().equals(nameMascota))
				  return person; 
		}
		return null;
	}
	
	
	
	
	private static Mascota findByNameUser(HashSet<Mascota> set, String nameMascota){ 
		for(Mascota mascota: set){
				if(mascota.getName().equals(nameMascota))
				  return mascota; 
		}
		return null;
	}
	
	
	private Person getMockPerson(String name, String surname, String phone, String email, String address) {
		Person person = new Person();
		person.setName(name);
		person.setSurname(surname);
		person.setPhone(phone);
		person.setEmail(email);
		person.setAddress(address);		
		return person;
	}
	
	
	public Mascota getMockMascota(String name, float w, float h, float l, String clazz){ 
		Mascota mascota = new Mascota();		
		mascota.setName(name);
		mascota.setWeight(w);
		mascota.setHeight(h);
		mascota.setLength(l);
		mascota.setTypeClass(clazz);
		return mascota;
	}

}