package com.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class InventoryManager {
 
	protected SessionFactory sessionFactory;
	
	// https://docs.jboss.org/hibernate/orm/current/quickstart/html_single/ 
	// took code from example 4 in link above
	protected void setUp() {
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure("hibernate.config.xml") // configures settings from hibernate.cfg.xml
				.build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		}catch (Exception e) {
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
	
	protected void exit() {
		sessionFactory.close();
	}
	
	protected Inventory create(Inventory inventory) {		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(inventory);
		
		System.out.println("Inventory created: ");
		System.out.println(inventory.toString());
		
		session.getTransaction().commit();
		session.close();
		
		return inventory;
	}
	
	protected Inventory read(long id) {
		Session session = sessionFactory.openSession();
		
		Inventory inventory = session.get(Inventory.class, id);
		
		if(inventory != null) {
			System.out.println("Inventory found: ");
			System.out.println(inventory.toString());
		}else {
			System.out.println("Inventory id: " + id + " does not exist");
		}
		
		session.close();
		
		return inventory;
	}
	
	protected Inventory update(Inventory inventory) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		long id = inventory.getId();
		Inventory newInventory = session.load(Inventory.class, id);
		
		if(newInventory != null) {
			session.update(inventory);
			System.out.println("Inventory updated: ");
			System.out.println(inventory.toString());			
		}else {
			System.out.println("Inventory id: " + id + " does not exist");
		}
		
		session.getTransaction().commit();
		session.close();
		
		return newInventory;
	}
	
	protected Inventory delete(long id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Inventory inventory = session.load(Inventory.class, id);
		
		if(inventory != null) {
			session.delete(inventory);
			System.out.println("Inventory deleted: ");
			System.out.println(inventory.toString());			
		}else {
			System.out.println("Inventory id: " + id + " does not exist");
		}
		
		session.getTransaction().commit();
		session.close();
		
		return inventory;
	}
	
	protected List<Inventory> list() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query<Inventory> query = session.createQuery("from Inventory", Inventory.class);
		List<Inventory> list = query.list();
		
		for(Inventory item: list) {
			System.out.println(item.toString());
		}
		
		session.getTransaction().commit();
		session.close();
		
		return list;
	}
}
