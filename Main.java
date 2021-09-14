package com.company;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

public class Main {
    private static MongoClient mongoClient = null;
    private static MongoDatabase library = null;
    private static MongoCollection<Document> collection = null;
    private static boolean shouldExit = true;
    private static Scanner input = new Scanner(System.in);


    public static void main(String[] args) {
        try {
            connectToDbms();

            connectTolibraryDatabase();

            connectToCollection();

            createAuthors();

            menu();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            mongoClient.close();
        }

    }

    private static void connectToCollection() {
        //Anslut till samlingen authors
        collection = library.getCollection("authors");
    }

    private static void connectToDbms() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);

        mongoClient = new MongoClient("localhost", 27017);

        System.out.println("Connection to the server succeeded");
    }

    private static void connectTolibraryDatabase() {
        library = mongoClient.getDatabase("library");
    }


    private static void createAuthors() {
        library.getCollection("authors").drop();

        Document doc1 = new Document("name", "Astrid Lindgren")
                .append("generalGenre", "Fairytales, bedtime stories, children's book")
                .append("about", "Astrid Lindgren is an legendary Swedish author known around the world for her great child-friendly books")
                .append("books", Arrays.asList("That boy Emil", "Pippi Longstocking", "The Brothers Lionheart", "Mio, My Son"));


        Document doc2 = new Document("name", "Hans Christian Andersen")
                .append("generalGenre", "Fairytales, bedtime stories, children's book")
                .append("about", "Hans Christian Andersen, or H.C Andersen, is a famous Danish author who wrote spectacular stories")
                .append("books", Arrays.asList("The Ugly Duckling", "The Little Mermaid", "The Princess and the Pea", "Thumbelina"));

        Document doc3 = new Document("name", "Charles Dickens")
                .append("generalGenre", "Dark, Historical Fiction, Satire")
                .append("about", "Charles Dickens is the king of Victorian era authors with his dark realistic style with breathtaking stories")
                .append("books", Arrays.asList("Oliver Twist", "A tale of Two Cities", "Great Expectations", "A Christmas Carol"));

        List<Document> documents = new ArrayList<Document>();
        documents.add(doc1);
        documents.add(doc2);
        documents.add(doc3);

        collection.insertMany(documents);
        System.out.println("" + documents.size() + "documents added to the collection");
    }

    private static void menu() {

        while (shouldExit) {

            System.out.println("\nWelcome to Library!");
            System.out.println("1. Add an author");
            System.out.println("2. View all authors");
            System.out.println("3. Add a book to an Author");
            System.out.println("4. Show all Author info");
            System.out.println("5. Exit");

            int selection = Integer.parseInt(input.nextLine());

            switch (selection) {
                case 1:
                    addAuthors();
                    break;
                case 2:
                    viewAllAuthors();
                    break;
                case 3:
                    addBooksToAuthor();
                    break;
                case 4:
                    showInformationByAuthor();
                    break;
                default:
                    shouldExit = false;
            }
        }
    }

    private static void addBooksToAuthor() {

        System.out.println("Please enter the name of the author: ");
        String authorName = input.nextLine();

        Document query = new Document("name", authorName);

        System.out.println("Please enter the name of the book: ");
        String bookName = input.nextLine();

        Document update = new Document("$addToSet", new Document("books", bookName));

        collection.updateOne(query, update);

    }

    private static void showInformationByAuthor() {
        System.out.println("Please put in the name of the author");
        String authorName = input.nextLine();

        MongoCursor<Document> selectCursor = collection.find(eq("name", authorName)).iterator();

        while (selectCursor.hasNext()) {
            Document doc = selectCursor.next();
            ArrayList<Object> book = new ArrayList<>(doc.values());
            System.out.println("Name: " + book.get(1));
            System.out.println("General genre: " + book.get(2));
            System.out.println("About : " + book.get(3));
            System.out.println("Books1: " + book.get(4));
        }

        }


    private static void viewAllAuthors() {

        MongoIterable<Document> result = collection.find();

        for (Document doc : result) {
            System.out.println(doc.get("name"));
        }
    }

        private static void addAuthors () {

            System.out.println("Please enter the name of the author: ");
            String authorName = input.nextLine();

            System.out.println("Please enter some general genre for the author: ");
            String generalGenre = input.nextLine();

            System.out.println("Please enter some small information about your author: ");
            String about = input.nextLine();

            Document doc = new Document("name", authorName)
                    .append("generalGenre", generalGenre)
                    .append("about", about);

            collection.insertOne(doc);
            System.out.println("\"This will make a fine addition to my collection\"");

        }

    }












