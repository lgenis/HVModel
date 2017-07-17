package dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import model.Mascota;
import model.Person;

public class DBConnector  extends DBManager implements HVServices{

	
	
	
	// Miquel 
	@Override
	public void insert(Person owner, Mascota mascota) {
		connect();
		   EntityManager entityManager = getEntityManager();  
		   
		        entityManager.getTransaction().begin();
		        owner.getMascotas().add(mascota);
		        entityManager.persist(owner);
		        mascota.setOwner(owner);

		        entityManager.getTransaction().commit();

		close();
		
	}

	// Jordi 
	@Override
	public void remove(Mascota mascota) {
		connect();
			EntityManager entiManager = getEntityManager();
			entiManager.getTransaction().begin();
			Mascota  recovered = entiManager.find(Mascota.class, mascota.getId());
			entiManager.remove(recovered);
			entiManager.getTransaction().commit();
		close();
		
	}

	//Jordi //Luis
	
	
	@Override
	public void remove(Person owner) {		
		connect();
			EntityManager entiManager = getEntityManager();		
			entiManager.getTransaction().begin();						
			Person  recovered = entiManager.find(Person.class, owner.getId());
			entiManager.remove(recovered);
			entiManager.getTransaction().commit();			
		close();		
	}

	//Toni 
	@Override
	public void update(Person person) {
		// TODO Auto-generated method stub
		//Runtime exception illegal parameters if not ID
		if (person.getId()<=0 || person.equals(null)){
			throw new RuntimeException("Wrong id or null");
		}
		connect();
			EntityManager entiManager = getEntityManager();
		try{	
			Person  recovered = entiManager.find(Person.class, person.getId());
			entiManager.getTransaction().begin();
			recovered.setName(person.getName());
			recovered.setSurname(person.getSurname());
			recovered.setPhone(person.getPhone());
			recovered.setAddress(person.getAddress());
			recovered.setEmail(person.getEmail());
			entiManager.getTransaction().commit();
		}catch (IllegalArgumentException e){
			throw new RuntimeException("Bad argument in find. Wrong class or Id for " + person.getName());
		}finally{
			close();
		}
				
	}

	//Toni //Eduar 
	@Override
	public void update(Mascota mascota) {
		// TODO Auto-generated method stub
		//Runtime exception illegal parameters if not ID
		if (mascota.getId()<=0 || mascota.equals(null)){
			throw new RuntimeException("Wrong id or null");
		}
		connect();
			EntityManager entiManager = getEntityManager();
		try{	
			Mascota  recovered = entiManager.find(Mascota.class, mascota.getId());
			entiManager.getTransaction().begin();
			recovered.setName(mascota.getName());
			recovered.setTypeClass(mascota.getTypeClass());
			recovered.setHeight(mascota.getHeight());
			recovered.setWeight(mascota.getWeight());
			recovered.setLength(mascota.getLength());
			recovered.setOwner(mascota.getOwner());
			entiManager.getTransaction().commit();
			
		}catch (IllegalArgumentException e){
			throw new RuntimeException("Bad argument in find. Wrong class or Id for " + mascota.getName());
		}finally{
			close();
		}
		
	}

	//Eduard 
	@Override
	public HashSet<Person> findLikeByOwnerName(String strLike) {
		ArrayList<Person> list = selectLike(Person.class, "name", strLike);  
		
		HashSet<Person> set = new HashSet<>(list);
		return set;
		
	}

	//Luis 
	@Override
	public HashSet<Mascota> findMascota(int idPerson) {
		connect();
		HashSet<Mascota> pet = new HashSet<Mascota>();
		pet.add( this.getEntityManager().find(Mascota.class, idPerson));
		close();
		return pet;
	}

	//Dorian 
	@Override
	public HashSet<Person> selectAllPerson() { 
		connect();
			HashSet<Person> list = new HashSet<Person>(selectAll(Person.class));
		close();
		return list;
	}

	//Dorian 
	@Override
	public HashSet<Mascota> selectAllMascota() {
		connect();
			HashSet<Mascota> list = new HashSet<Mascota>(selectAll(Mascota.class));
		close();
		return list;
	}

}
