# Advanced Object Orientated Programming Project
This is my 4th year project for the advanced object orientated programming module in college. For this project I was required to develop a Java web application that enables two or more text documents to be compared for similarity.

## Minimum Requirements
1. A document or URL should be specified or selected from a web browser and then dispatched to a servlet instance running under Apache Tomcat.
2. Each submitted document should be parsed into its set of constituent shingles and then compared against the existing document(s) in an object-oriented database (db4O) and then stored in the database.
3. The similarity of the submitted document to the set of documents stored in the database should be returned and presented to the session user.

## Design Patterns and Decisions
The main goal of this project was not only to create an application that worked correctly, but to build a loosely coupled and highly cohesive application that can be easily maintained and extended. Therefore, it was important to be cautious when making any design decisions throughout the project, and to base these decisions on established design principles and patterns. In this section I will describe the various design patterns I've used, as well as my reasoning for them.

1. Asynchronous message facade
An asynchronous message facade was used to queue requests for processing rather than dealing with them immediately. The requests in this queue are processed by threads in a thread pool. Once processed, they are added to an out queue. This improves scalablility as you can control resources by starting as many, or few, threads as you want.

2. Singleton
The singleton pattern was used for the class that manages the thread pool. The reason for this is that we do not want more than one thread pool to be created.

3. Bridge
A variant of the bridge pattern is used throughout the request hierarchy. It was used to overcome the problem of deciding what parser and comparator should be used in the worker thread to process each request. My first thought was to use the chain of responsibilities pattern, where each handler would create a different parser and comparator combination based on the type of the request. However, I found that it was not well suited to the problem I was trying to solve. With the bridge pattern, it is easy to create a new type of request. For example, if we decide that we want to check the similarity of DNA samples we can create a new parser for parsing k-shingles and create a new type of request that uses this parser, along with the Jaccard comparator.

4. Template pattern
The template pattern is used in the comparator hierarchy where it isn't yet clear in the abstract class how two documents will be compared. Instead, the implementation is left up to the subclasses.

On top of this, I also paid attention to how I was using the concepts of abstraction, encapsulation, composition, inheritance and polymorphism throughout the application.
