# Advanced Object Orientated Programming Project
This is my 4th year project for the advanced object orientated software development module in college. For this project I was required to develop a Java web application that enables two or more text documents to be compared for similarity.

## Minimum Requirements
1. A document or URL should be specified or selected from a web browser and then dispatched to a servlet instance running under Apache Tomcat.
2. Each submitted document should be parsed into its set of constituent shingles and then compared against the existing document(s) in an object-oriented database (db4O) and then stored in the database.
3. The similarity of the submitted document to the set of documents stored in the database should be returned and presented to the session user.

## Packaging and running the application
The following are the steps to run this project:
1. [Download](https://tomcat.apache.org/download-90.cgi) Tomcat 9 if you haven't done so already.
2. Start Tomcat by navigating to the bin directory in a terminal and running `bash startup.sh` on Linux, or `startup.bat` on Windows.
3. Open a terminal and clone the repository: `https://github.com/KeithWilliamsGMIT/Advanced-OOP-Project.git`.
4. Copy the contents of the src directory to the WEB-INF/classes directory.
5. Compile these Java classes by navigating to the WebContent directory in a terminal and using one of the below commands. Note that you will need to add the JAR file `$TOMCAT_HOME/lib/servlet-api.jar` to your CLASSPATH variable in order to compile a servlet from a command line. You will also need to add the DB4O JAR files, `xtea_db4o_8.0.jar` and `db4o-8.0.249.16098-all-java5.jar`, to the CLASSPATH.  
    - Mac/Linux: `javac -cp .:$TOMCAT_HOME/lib/servlet-api.jar:WEB-INF/classes WEB-INF/classes/ie/gmit/sw/**/*.java`
    - Windows: `javac -cp .;%TOMCAT_HOME%/lib/servlet-api.jar;WEB-INF/classes WEB-INF/classes/ie/gmit/sw/**/*.java`
6. Create the WAR file with the following command from inside the WebContent directory: `jar –cf jaccard.war *`.
7. Copy the WAR file to the webapps directory in Tomcat and wait for it to unpack.
8. In a browser, navigate to [http://localhost:8080/jaccard/](http://localhost:8080/jaccard/).
9. When ready, shutdown Tomcat by navigating to the bin directory in a terminal and running `bash shutdown.sh` on Linux, or `shutdown.bat` on Windows.

## Application Overview
This application covers the minimum requirements outlined in the project specification. The user selects a document to upload. That document is parsed into a set of shingles before being saved to a DB4O database. It it then compared against other documents stored in the database. The application is build with scalability and extensibility in mind. Each request is processed asynchronously and it is easy to create a new type of request. For example, if you decide that you want to check the similarity of DNA samples we can create a new parser for parsing k-shingles and create a new type of request that uses this parser, along with the Jaccard comparator. The only relevant feature in this application is that it is capable of comparing the uploaded document with ALL the documents in the database. The results are returned as a table, with the first column being the id of each document the uploaded document was compared to, and the second column being the similarity measurement.

## Design Patterns and Decisions
The main goal of this project was not only to create an application that worked correctly, but to build a loosely coupled and highly cohesive application that can be easily maintained and extended. Therefore, it was important to be cautious when making any design decisions throughout the project, and to base these decisions on established design principles and patterns. In this section I will describe some of the design patterns I've used, as well as my reasoning for them.

1. Asynchronous message facade  
An asynchronous message facade was used to queue requests for processing rather than dealing with them immediately. The requests in this queue are processed by threads in a thread pool. Once processed, they are added to an out queue. This improves scalablility as you can control resources by starting as many, or few, threads as you want.

2. Singleton  
The singleton pattern was used for the classes that manage the thread pool and database. The reason for this is that we do not want more than one thread pool or database to be created.

3. Bridge  
A variant of the bridge pattern is used throughout the request hierarchy. It was used to overcome the problem of deciding what parser and comparator should be used in the worker thread to process each request. My first thought was to use the chain of responsibilities pattern, where each handler would create a different parser and comparator combination based on the type of the request. However, I found that it was not well suited to the problem I was trying to solve. With the bridge pattern, it is easy to create a new type of request.

4. Template pattern  
The template pattern is used in the comparator hierarchy where it isn't yet clear in the abstract class how two documents will be compared. Instead, the implementation is left up to the subclasses.

On top of this, I also paid attention to how I was using the concepts of abstraction, encapsulation, composition, inheritance and polymorphism throughout the application.

## Conclusion
After completing this project I realise the importance of making the right design decisions throughout the development of any application. Although many different design patterns and principles have been established, every application is different and the "best" design is not always immediately clear. Before thinking about the design, it is important to have a clear understanding of the requirements. Also, sI found that paying attention to the basic rules of abstraction and encapulation is a good starting point for any design. Overall, while this project was relatively challenging, it was an opportunity to experiment with a number of these design patterns, whcih I now feel I have a better understanding of.
