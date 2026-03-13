package com.example.HibernateCRUD;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class MainApp {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // INSERT additional products
        Product p3 = new Product("Keyboard", "Accessories", 500, 10);
        Product p4 = new Product("Mouse", "Accessories", 300, 15);
        Product p5 = new Product("Monitor", "Display", 15000, 5);
        Product p6 = new Product("Tablet", "Device", 20000, 3);
        Product p7 = new Product("Headphones", "Audio", 1200, 8);

        session.save(p3);
        session.save(p4);
        session.save(p5);
        session.save(p6);
        session.save(p7);

        System.out.println("Additional products inserted");

        // SORT BY PRICE ASCENDING
        System.out.println("\nProducts sorted by price ASC:");
        List<Product> list1 = session.createQuery("FROM Product ORDER BY price ASC", Product.class).list();
        list1.forEach(p -> System.out.println(p.getName() + " " + p.getPrice()));

        // SORT BY PRICE DESCENDING
        System.out.println("\nProducts sorted by price DESC:");
        List<Product> list2 = session.createQuery("FROM Product ORDER BY price DESC", Product.class).list();
        list2.forEach(p -> System.out.println(p.getName() + " " + p.getPrice()));

        // SORT BY QUANTITY (HIGHEST FIRST)
        System.out.println("\nProducts sorted by quantity DESC:");
        List<Product> list3 = session.createQuery("FROM Product ORDER BY quantity DESC", Product.class).list();
        list3.forEach(p -> System.out.println(p.getName() + " " + p.getQuantity()));

        // PAGINATION (FIRST 3)
        System.out.println("\nFirst 3 products:");
        Query<Product> q1 = session.createQuery("FROM Product", Product.class);
        q1.setFirstResult(0);
        q1.setMaxResults(3);
        q1.list().forEach(p -> System.out.println(p.getName()));

        // PAGINATION (NEXT 3)
        System.out.println("\nNext 3 products:");
        Query<Product> q2 = session.createQuery("FROM Product", Product.class);
        q2.setFirstResult(3);
        q2.setMaxResults(3);
        q2.list().forEach(p -> System.out.println(p.getName()));

        // COUNT TOTAL PRODUCTS
        Long count = session.createQuery("SELECT COUNT(*) FROM Product", Long.class).uniqueResult();
        System.out.println("\nTotal Products: " + count);

        // COUNT WHERE QUANTITY > 0
        Long count2 = session.createQuery(
                "SELECT COUNT(*) FROM Product WHERE quantity > 0", Long.class).uniqueResult();
        System.out.println("Products with quantity > 0: " + count2);

        // GROUP BY DESCRIPTION
        System.out.println("\nCount grouped by description:");
        List<Object[]> group = session.createQuery(
                "SELECT description, COUNT(*) FROM Product GROUP BY description").list();

        for (Object[] row : group) {
            System.out.println(row[0] + " : " + row[1]);
        }

        // MIN AND MAX PRICE
        Object[] minmax = (Object[]) session.createQuery(
                "SELECT MIN(price), MAX(price) FROM Product").uniqueResult();

        System.out.println("\nMinimum Price: " + minmax[0]);
        System.out.println("Maximum Price: " + minmax[1]);

        // WHERE PRICE RANGE
        System.out.println("\nProducts price between 500 and 20000:");
        List<Product> range = session.createQuery(
                "FROM Product WHERE price BETWEEN 500 AND 20000", Product.class).list();
        range.forEach(p -> System.out.println(p.getName() + " " + p.getPrice()));

        // LIKE QUERIES
        System.out.println("\nNames starting with 'M':");
        List<Product> like1 = session.createQuery(
                "FROM Product WHERE name LIKE 'M%'", Product.class).list();
        like1.forEach(p -> System.out.println(p.getName()));

        System.out.println("\nNames ending with 'e':");
        List<Product> like2 = session.createQuery(
                "FROM Product WHERE name LIKE '%e'", Product.class).list();
        like2.forEach(p -> System.out.println(p.getName()));

        System.out.println("\nNames containing 'top':");
        List<Product> like3 = session.createQuery(
                "FROM Product WHERE name LIKE '%top%'", Product.class).list();
        like3.forEach(p -> System.out.println(p.getName()));

        System.out.println("\nNames with length 5:");
        List<Product> like4 = session.createQuery(
                "FROM Product WHERE LENGTH(name)=5", Product.class).list();
        like4.forEach(p -> System.out.println(p.getName()));

        tx.commit();
        session.close();
        factory.close();
    }
}