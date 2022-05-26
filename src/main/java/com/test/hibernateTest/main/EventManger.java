package com.test.hibernateTest.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.test.hibernateTest.domain.Event;
import com.test.hibernateTest.domain.Person;
import com.test.hibernateTest.utils.HibernateUtil;

public class EventManger {

	

	public long addEvent(){
		// TODO Auto-generated method stub
		
		Session session=HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Event evt=new Event();
		try {
			tx = session.beginTransaction();
			evt.setTitle("abc");
			evt.setDate(new Date());
			session.save(evt);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return evt.getId();
	}
	
	public long addPerson(){
		// TODO Auto-generated method stub
		
		Session session=HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Person prs=new Person();
		try {
			tx = session.beginTransaction();
			
			prs.setFirstname("abc");
			prs.setLastname("def");
			prs.setAge(21);
			session.save(prs);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return prs.getId();
	}
	
	
	public static void main(String[] args) {
		System.out.println("1");
		EventManger evnt=new EventManger();
		long eventId=evnt.addEvent();
		long personId=evnt.addPerson();
		System.out.println(evnt.getEvent(2));
		System.out.println(evnt.getEventList());
		evnt.addEventToPerson(personId, eventId);
		evnt.addEmailToPerson(personId, "12@gmail.com");
	}

	public Event getEvent(long id) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Event evnt=null;
		
		try {
			tx = session.beginTransaction();
			evnt= (Event) session.get(Event.class,id);
			
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
			
		}
		return evnt;
	}
	
	public List getEventList() {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		List list =new ArrayList();
		try {
		list=session.createQuery("from Event").list();
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return list;
	}
	private void addEventToPerson(Long personId, Long eventId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Person aPerson = (Person) session.load(Person.class, personId);
		Event anEvent = (Event) session.load(Event.class, eventId);
		aPerson.getEvents().add(anEvent);
		session.getTransaction().commit();
		}
	
	private void addEmailToPerson(Long personId, String emailAddress) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Person aPerson = (Person) session.load(Person.class, personId);
		// adding to the emailAddress collection might trigger a lazy load of the collection
		aPerson.getEmailAddresses().add(emailAddress);
		session.getTransaction().commit();
		}
	
	

}
