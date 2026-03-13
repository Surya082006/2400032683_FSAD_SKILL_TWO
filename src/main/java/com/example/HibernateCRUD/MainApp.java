package com.example.HibernateCRUD;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MainApp {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        //INSERT
//        Product p1 = new Product("Laptop", "Lenovoo", 900.89, 3);
//        Product p2 = new Product("Mobile", "Oppoo", 79, 4);
//
//        session.save(p1);
//        session.save(p2);
//
//        System.out.println("Products inserted successfully");

        // RETRIEVE
        Product product = session.get(Product.class, 1);
//
//        if(product != null) {
//            System.out.println("\nProduct Retrieved:");
//            System.out.println("ID: " + product.getId());
//            System.out.println("Name: " + product.getName());
//            System.out.println("Description: " + product.getDescription());
//            System.out.println("Price: " + product.getPrice());
//            System.out.println("Quantity: " + product.getQuantity());
//        }
//
//        // UPDATE
//        if(product != null) {
//            product.setPrice(10000);
//            session.update(product);
//            System.out.println("\nProduct Updated");
//        }
//
        // DELETE
        Product deleteProduct = session.get(Product.class,2);

        if(deleteProduct != null) {
            session.delete(deleteProduct);
            System.out.println("\nProduct Deleted");
        }

        tx.commit();

        session.close();
        factory.close();
    }
}