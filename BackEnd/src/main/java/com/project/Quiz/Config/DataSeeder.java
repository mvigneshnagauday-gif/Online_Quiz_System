package com.project.Quiz.Config;

import com.project.Quiz.Entity.Course;
import com.project.Quiz.Entity.Question;
import com.project.Quiz.Repository.CourseRepository;
import com.project.Quiz.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void run(String... args) {
        if (courseRepository.count() > 0) {
            System.out.println("DataSeeder: courses already exist, skipping seed.");
            return;
        }

        Course java = courseRepository.save(new Course("Java", "Core Java programming and OOP concepts"));
        Course python = courseRepository.save(new Course("Python", "Python fundamentals to advanced concepts"));
        Course cpp = courseRepository.save(new Course("C++", "C++ programming and object-oriented design"));
        Course database = courseRepository.save(new Course("Database", "SQL, DBMS concepts, and database design"));
        Course springBoot = courseRepository.save(new Course("Spring Boot", "Building REST APIs with Spring Boot"));

        seedJava(java);
        seedPython(python);
        seedCpp(cpp);
        seedDatabase(database);
        seedSpringBoot(springBoot);

        System.out.println("DataSeeder: seeded 5 courses and 150 questions.");
    }

    // Adds one question row to the database
    private void add(Course course, int level, String text, String o1, String o2, String o3, String o4, int correct) {
        Question q = new Question();
        q.setCourse(course);
        q.setLevel(level);
        q.setQuestionText(text);
        q.setOption1(o1);
        q.setOption2(o2);
        q.setOption3(o3);
        q.setOption4(o4);
        q.setCorrectOption(correct);
        questionRepository.save(q);
    }

    private void seedJava(Course c) {
        // Level 1 - Easy
        add(c, 1, "What is the size of an int in Java?", "2 bytes", "4 bytes", "8 bytes", "16 bytes", 1);
        add(c, 1, "Which keyword is used to create a class?", "class", "struct", "define", "object", 0);
        add(c, 1, "Which of these is NOT a Java primitive type?", "int", "float", "String", "boolean", 2);
        add(c, 1, "What is the default value of a boolean variable?", "true", "false", "0", "null", 1);
        add(c, 1, "Which method is the entry point of a Java program?", "start()", "main()", "run()", "init()", 1);
        add(c, 1, "Which symbol is used for single-line comments?", "//", "/* */", "#", "--", 0);
        add(c, 1, "Which keyword is used to inherit a class?", "extends", "implements", "inherits", "super", 0);
        add(c, 1, "What does JVM stand for?", "Java Virtual Machine", "Java Verified Method", "Java Variable Method", "Java Virtual Method", 0);
        add(c, 1, "Which operator is used to compare two values?", "=", "==", "~", "!", 1);
        add(c, 1, "Which collection type does not allow duplicate elements?", "List", "Set", "Map", "Array", 1);

        // Level 2 - Intermediate
        add(c, 2, "Which keyword prevents a class from being inherited?", "static", "final", "private", "abstract", 1);
        add(c, 2, "What is method overloading?", "Same method name with different parameters", "Same method redefined in subclass", "Calling a method twice", "None of these", 0);
        add(c, 2, "Which exception is thrown when dividing by zero (int)?", "NullPointerException", "ArithmeticException", "ArrayIndexOutOfBoundsException", "ClassCastException", 1);
        add(c, 2, "Which interface must a class implement for a for-each loop?", "Iterable", "Iterator", "Collection", "List", 0);
        add(c, 2, "What is the purpose of the 'super' keyword?", "Call parent class constructor/methods", "Create a new object", "Define a static method", "None of these", 0);
        add(c, 2, "Which of these supports key-value pairs?", "ArrayList", "HashSet", "HashMap", "LinkedList", 2);
        add(c, 2, "What does 'static' mean for a method?", "Belongs to the instance", "Belongs to the class, not instance", "Cannot be overridden", "Runs only once ever", 1);
        add(c, 2, "Which access modifier limits access to only its own class?", "public", "protected", "default", "private", 3);
        add(c, 2, "What is polymorphism?", "One interface, many implementations", "Multiple inheritance", "Hiding data", "None of these", 0);
        add(c, 2, "Which block always executes, exception or not?", "try", "catch", "finally", "throw", 2);

        // Level 3 - Hard
        add(c, 3, "What is the output of System.out.println(1/2) in Java?", "0.5", "0", "1", "Compile error", 1);
        add(c, 3, "What is true about Java's garbage collector?", "Manually invoked always", "Automatically reclaims unused objects", "Deletes files", "Compiles code", 1);
        add(c, 3, "What is a functional interface?", "Interface with exactly one abstract method", "Interface with no methods", "Interface with static methods only", "Any interface", 0);
        add(c, 3, "Which keyword ensures variable visibility across threads?", "synchronized", "volatile", "transient", "final", 1);
        add(c, 3, "What's the key difference between '==' and '.equals()' for Strings?", "No difference", "== compares references, .equals compares values", ".equals compares references only", "Both always compare values", 1);
        add(c, 3, "Which design pattern does Java's Iterator represent?", "Singleton", "Iterator pattern", "Factory", "Observer", 1);
        add(c, 3, "What happens if you call start() twice on the same Thread?", "Runs twice", "Throws IllegalThreadStateException", "No effect", "Compiles but does nothing", 1);
        add(c, 3, "Which collection is best suited for LIFO operations?", "ArrayList", "Deque/Stack", "HashMap", "TreeSet", 1);
        add(c, 3, "What is autoboxing?", "Automatic conversion of primitive to wrapper class", "Automatic conversion of object to primitive", "Automatic compilation", "None of these", 0);
        add(c, 3, "Which of these is immutable in Java?", "ArrayList", "StringBuilder", "String", "HashMap", 2);
    }

    private void seedPython(Course c) {
        // Level 1 - Easy
        add(c, 1, "Which symbol is used for comments in Python?", "//", "#", "--", "/* */", 1);
        add(c, 1, "What is the correct file extension for Python files?", ".pt", ".python", ".py", ".pyt", 2);
        add(c, 1, "Which data type stores True/False?", "int", "bool", "str", "float", 1);
        add(c, 1, "Which function is used to print output?", "echo()", "print()", "printf()", "console.log()", 1);
        add(c, 1, "How do you start a multi-line comment/string in Python?", "Using triple quotes", "Using //", "Using <!-- -->", "Using /* */", 0);
        add(c, 1, "Which keyword is used to define a function?", "func", "function", "def", "define", 2);
        add(c, 1, "What is the index of the first element in a Python list?", "1", "0", "-1", "None", 1);
        add(c, 1, "Which of these is a mutable data type?", "tuple", "string", "list", "int", 2);
        add(c, 1, "How do you assign 5 to variable x in Python?", "x = 5", "int x = 5", "var x = 5", "x := 5", 0);
        add(c, 1, "Which operator is used for exponentiation?", "^", "**", "exp()", "pow", 1);

        // Level 2 - Intermediate
        add(c, 2, "What does 'self' refer to in a class method?", "The class itself", "The current instance of the class", "A static variable", "None of these", 1);
        add(c, 2, "Which method is called when an object is created?", "__init__", "__new__", "__create__", "__start__", 0);
        add(c, 2, "What is a list comprehension?", "A concise way to create lists", "A way to comment lists", "A built-in function", "A type of loop only", 0);
        add(c, 2, "Which exception is raised when dividing by zero?", "ValueError", "ZeroDivisionError", "TypeError", "ArithmeticError", 1);
        add(c, 2, "What does len() do?", "Returns the length of an object", "Converts to lowercase", "Returns the type", "Rounds a number", 0);
        add(c, 2, "Which keyword pairs with 'try' for exception handling?", "catch", "except", "rescue", "handle", 1);
        add(c, 2, "What is the output of type([1,2,3])?", "<class 'list'>", "<class 'tuple'>", "<class 'array'>", "<class 'set'>", 0);
        add(c, 2, "Which module is commonly used for regular expressions?", "regex", "re", "pyregex", "restring", 1);
        add(c, 2, "What does 'pip' do?", "Package manager for Python", "Python interpreter", "Prints in Python", "None of these", 0);
        add(c, 2, "Which of these creates a dictionary?", "{}", "[]", "()", "<>", 0);

        // Level 3 - Hard
        add(c, 3, "What is a Python decorator?", "A function that modifies another function", "A comment style", "A data type", "A loop construct", 0);
        add(c, 3, "What does the 'yield' keyword do?", "Returns a value and pauses the function state (generator)", "Exits the program", "Same as return", "None of these", 0);
        add(c, 3, "What is the GIL in Python?", "Global Interpreter Lock", "General Index List", "Global Instance Loop", "None of these", 0);
        add(c, 3, "What determines method lookup order in multiple inheritance?", "MRO (Method Resolution Order)", "GIL", "PEP8", "None of these", 0);
        add(c, 3, "What does '*args' allow in a function definition?", "Variable number of positional arguments", "Keyword arguments only", "A single argument", "None of these", 0);
        add(c, 3, "What's the difference between deep copy and shallow copy?", "Deep copy also copies nested objects", "No difference", "Shallow copy copies more", "None of these", 0);
        add(c, 3, "Which keyword is used for context managers?", "with", "using", "as", "context", 0);
        add(c, 3, "What does the '__str__' method control?", "The string representation of an object", "Object creation", "Object deletion", "None of these", 0);
        add(c, 3, "What is a lambda function?", "An anonymous inline function", "A named function", "A class", "A module", 0);
        add(c, 3, "Which built-in function returns an iterator from an iterable?", "iter()", "range() only", "len()", "list()", 0);
    }

    private void seedCpp(Course c) {
        // Level 1 - Easy
        add(c, 1, "Which header is required for cout/cin?", "<stdio.h>", "<iostream>", "<conio.h>", "<string.h>", 1);
        add(c, 1, "Which symbol is used to end a statement in C++?", ":", ";", ".", ",", 1);
        add(c, 1, "Which keyword declares a constant in C++?", "final", "const", "static", "readonly", 1);
        add(c, 1, "What is the correct way to declare a pointer?", "int *p;", "int p*;", "ptr int p;", "*int p;", 0);
        add(c, 1, "Which operator is used for scope resolution?", "::", "->", ".", "#", 0);
        add(c, 1, "What is the size of a char in C++ typically?", "1 byte", "2 bytes", "4 bytes", "8 bytes", 0);
        add(c, 1, "Which loop checks the condition after executing the body?", "for", "while", "do-while", "if", 2);
        add(c, 1, "Which keyword is used to define a class?", "class", "struct only", "object", "define", 0);
        add(c, 1, "What does 'cout' do?", "Reads input", "Prints output", "Declares a variable", "Ends the program", 1);
        add(c, 1, "Which of these is a valid comment in C++?", "// comment", "<!-- comment -->", "# comment", "' comment", 0);

        // Level 2 - Intermediate
        add(c, 2, "What is function overloading?", "Same function name with different parameters", "Redefining a function in a derived class", "Calling a function multiple times", "None of these", 0);
        add(c, 2, "Which keyword is used for inheritance in C++?", ": (colon) after class name", "extends", "implements", "inherits", 0);
        add(c, 2, "What is a virtual function used for?", "Enabling runtime polymorphism", "Creating static members", "Memory allocation", "None of these", 0);
        add(c, 2, "What does 'new' do in C++?", "Allocates memory dynamically", "Deletes memory", "Declares a constant", "Starts a loop", 0);
        add(c, 2, "Which access specifier allows access only within the class?", "public", "protected", "private", "friend", 2);
        add(c, 2, "What is a constructor?", "A special method called when an object is created", "A method to delete objects", "A static method", "None of these", 0);
        add(c, 2, "Which STL container stores unique sorted elements?", "vector", "list", "set", "map", 2);
        add(c, 2, "What does 'delete' do in C++?", "Frees dynamically allocated memory", "Removes a class", "Ends a loop", "Declares a variable", 0);
        add(c, 2, "What is operator overloading?", "Redefining an operator's behavior for user-defined types", "Using multiple operators together", "Overriding a function", "None of these", 0);
        add(c, 2, "Which keyword makes a base class function overridable?", "virtual", "override", "static", "final", 0);

        // Level 3 - Hard
        add(c, 3, "What is a memory leak?", "Memory allocated but never freed", "A syntax error", "A compile-time warning", "None of these", 0);
        add(c, 3, "What does RAII stand for/mean in C++?", "Resource Acquisition Is Initialization", "Random Access Iterator Interface", "Runtime Allocation In Instance", "None of these", 0);
        add(c, 3, "What is the diamond problem related to?", "Multiple inheritance ambiguity", "Array indexing", "Pointer arithmetic", "Template specialization", 0);
        add(c, 3, "What does a smart pointer like unique_ptr do?", "Automatically manages object lifetime/memory", "Speeds up compilation", "Replaces all pointers", "None of these", 0);
        add(c, 3, "What is template metaprogramming used for?", "Computations performed at compile time", "Runtime error handling", "Memory management only", "None of these", 0);
        add(c, 3, "What does 'mutable' allow in a const member function?", "Modifying a specific member even in a const method", "Making the whole object mutable", "Disabling const-ness entirely", "None of these", 0);
        add(c, 3, "What is move semantics primarily used for?", "Efficiently transferring resources instead of copying", "Moving files on disk", "Renaming variables", "None of these", 0);
        add(c, 3, "What does 'explicit' prevent for a constructor?", "Implicit type conversions", "Object creation entirely", "Inheritance", "None of these", 0);
        add(c, 3, "What is a pure virtual function?", "A virtual function with no implementation, making the class abstract", "A function with no arguments", "A static function", "None of these", 0);
        add(c, 3, "What does 'std::move' actually do?", "Casts an object to an rvalue reference", "Physically moves memory", "Deletes an object", "None of these", 0);
    }

    private void seedDatabase(Course c) {
        // Level 1 - Easy
        add(c, 1, "What does SQL stand for?", "Structured Query Language", "Simple Query Language", "Standard Query Language", "Sequential Query Language", 0);
        add(c, 1, "Which command retrieves data from a table?", "GET", "SELECT", "FETCH", "RETRIEVE", 1);
        add(c, 1, "Which clause is used to filter rows?", "WHERE", "FILTER", "HAVING", "IF", 0);
        add(c, 1, "What is a primary key?", "A column that uniquely identifies each row", "Any column", "A foreign key", "An index only", 0);
        add(c, 1, "Which command adds a new row?", "INSERT", "ADD", "CREATE", "APPEND", 0);
        add(c, 1, "Which command removes a table completely?", "DELETE", "DROP", "REMOVE", "TRUNCATE only", 1);
        add(c, 1, "What does DBMS stand for?", "Database Management System", "Data Base Main System", "Database Mode System", "None of these", 0);
        add(c, 1, "Which keyword sorts query results?", "ORDER BY", "SORT BY", "GROUP BY", "ARRANGE BY", 0);
        add(c, 1, "Which data type stores whole numbers?", "VARCHAR", "INT", "TEXT", "BOOLEAN", 1);
        add(c, 1, "Which symbol selects all columns in a query?", "*", "#", "%", "@", 0);

        // Level 2 - Intermediate
        add(c, 2, "What is normalization?", "Organizing data to reduce redundancy", "Encrypting data", "Backing up data", "Indexing data", 0);
        add(c, 2, "What is a foreign key?", "A key that links to a primary key in another table", "A duplicate primary key", "An encrypted key", "A random unique value", 0);
        add(c, 2, "Which JOIN returns only matching rows from both tables?", "LEFT JOIN", "RIGHT JOIN", "INNER JOIN", "FULL JOIN", 2);
        add(c, 2, "What does GROUP BY do?", "Groups rows sharing a value for aggregation", "Sorts rows", "Filters rows", "Joins tables", 0);
        add(c, 2, "What is an index used for?", "Speeding up data retrieval", "Storing backups", "Encrypting data", "Formatting data", 0);
        add(c, 2, "Which normal form removes partial dependency?", "1NF", "2NF", "3NF", "BCNF", 1);
        add(c, 2, "What is a transaction in DBMS?", "A unit of work that is atomic", "A single SQL query", "A backup operation", "A type of index", 0);
        add(c, 2, "What does ACID stand for?", "Atomicity, Consistency, Isolation, Durability", "Accuracy, Concurrency, Integrity, Data", "Approval, Commit, Index, Delete", "None of these", 0);
        add(c, 2, "Which command modifies existing table structure?", "ALTER", "UPDATE", "MODIFY", "CHANGE", 0);
        add(c, 2, "What is a composite key?", "A key made of two or more columns", "A duplicate key", "An auto-increment key", "A foreign key", 0);

        // Level 3 - Hard
        add(c, 3, "Which isolation level prevents dirty reads but allows non-repeatable reads?", "Read Uncommitted", "Read Committed", "Repeatable Read", "Serializable", 1);
        add(c, 3, "What is a deadlock in DBMS?", "Two transactions waiting on each other indefinitely", "A crashed server", "A slow query", "A missing index", 0);
        add(c, 3, "What does denormalization trade off?", "Adds redundancy to improve read performance", "Removes all redundancy", "Improves write performance only", "None of these", 0);
        add(c, 3, "Which index type is best for range queries?", "B-Tree index", "Hash index", "Bitmap only", "None of these", 0);
        add(c, 3, "What is a materialized view?", "A view whose results are physically stored", "A temporary table", "A backup", "A trigger", 0);
        add(c, 3, "What is the purpose of database sharding?", "Splitting data across multiple servers for scalability", "Encrypting data", "Indexing only", "Normalizing data", 0);
        add(c, 3, "What is a covering index?", "An index that contains all columns needed by a query", "A unique index", "A foreign key index", "None of these", 0);
        add(c, 3, "Which anomalies does normalization primarily fix?", "Update, Insert, and Delete anomalies", "Deadlocks", "Network latency", "Backup failures", 0);
        add(c, 3, "What does the EXPLAIN command show?", "The query execution plan", "Table structure only", "Deletes a query", "Formats output", 0);
        add(c, 3, "What is optimistic locking?", "Assuming conflicts are rare and checking at commit time", "Locking rows immediately on read", "Never locking", "Locking the entire database", 0);
    }

    private void seedSpringBoot(Course c) {
        // Level 1 - Easy
        add(c, 1, "What is Spring Boot mainly used for?", "Simplifying Spring app setup and configuration", "Frontend styling", "Database design only", "OS-level tasks", 0);
        add(c, 1, "Which annotation marks the main class of a Spring Boot app?", "@SpringBootApplication", "@Main", "@Application", "@Boot", 0);
        add(c, 1, "What is the default embedded server in Spring Boot?", "Apache Tomcat", "Nginx", "IIS", "Apache HTTPD", 0);
        add(c, 1, "Which file is commonly used for configuration?", "application.properties", "config.xml", "settings.json", "boot.config", 0);
        add(c, 1, "Which annotation creates a REST controller?", "@RestController", "@Controller only", "@Service", "@Repository", 0);
        add(c, 1, "What does @Autowired do?", "Injects a dependency automatically", "Creates a new thread", "Starts the server", "Compiles code", 0);
        add(c, 1, "Which annotation marks a class as a JPA entity?", "@Entity", "@Table", "@Model", "@Data", 0);
        add(c, 1, "What is the default port for a Spring Boot web app?", "8080", "3000", "80", "5000", 0);
        add(c, 1, "Which annotation handles GET requests?", "@GetMapping", "@PostMapping", "@RequestGet", "@FetchMapping", 0);
        add(c, 1, "What does 'IoC' stand for in Spring?", "Inversion of Control", "Input or Control", "Internal Object Control", "None of these", 0);

        // Level 2 - Intermediate
        add(c, 2, "What is dependency injection?", "Objects receive dependencies from an external source", "Manually creating objects", "A database technique", "A testing method", 0);
        add(c, 2, "Which annotation defines a bean manually?", "@Bean", "@Component only", "@Service", "@Autowired", 0);
        add(c, 2, "What is the purpose of Spring Data JPA?", "Simplifies database access using repository interfaces", "Handles frontend rendering", "Manages security only", "Compiles Java code", 0);
        add(c, 2, "Which annotation is required to map a class to a table?", "@Table alone", "@Entity", "@Column", "@Repository", 1);
        add(c, 2, "What does @RequestBody do?", "Binds the HTTP request body to a Java object", "Sends a response", "Creates a bean", "Starts a transaction", 0);
        add(c, 2, "Which annotation reads path variables from a URL?", "@PathVariable", "@RequestParam", "@RequestBody", "@Query", 0);
        add(c, 2, "What does spring.jpa.hibernate.ddl-auto control?", "Automatic schema generation behavior", "The server port", "Security config", "Logging level", 0);
        add(c, 2, "What is the default scope for a Spring bean?", "Singleton", "Prototype", "Request", "Session", 0);
        add(c, 2, "What does @Transactional ensure?", "The method runs within a database transaction", "The method runs asynchronously", "The method result is cached", "The method is secured", 0);
        add(c, 2, "Which annotation enables cross-origin requests?", "@CrossOrigin", "@CORS", "@AllowOrigin", "@ExternalAccess", 0);

        // Level 3 - Hard
        add(c, 3, "What's the practical difference between @Component, @Service, @Repository?", "Functionally similar but semantically indicate different layers", "Completely different behavior", "Only @Component actually works", "They are unrelated annotations", 0);
        add(c, 3, "What is Spring AOP mainly used for?", "Cross-cutting concerns like logging and security", "Database access", "Frontend rendering", "Compilation", 0);
        add(c, 3, "What does @Transactional(rollbackFor = Exception.class) do?", "Rolls back for any Exception, not just unchecked ones", "Prevents the method from running", "Disables transactions", "None of these", 0);
        add(c, 3, "What is the purpose of a Spring Security filter chain?", "Defines the sequence of security checks applied to requests", "Compiles code", "Handles DB migrations", "Renders views", 0);
        add(c, 3, "What does @Value do?", "Injects a value from application.properties into a field", "Creates a bean", "Starts a transaction", "Defines a controller", 0);
        add(c, 3, "What is the purpose of @Profile?", "Allows different configurations for different environments", "Encrypts passwords", "Compiles the app", "Only used for testing", 0);
        add(c, 3, "What does CommandLineRunner do?", "Runs code after the Spring application context is loaded", "Starts the embedded server", "Handles HTTP requests", "Defines entities", 0);
        add(c, 3, "What is a circular dependency in Spring?", "Two or more beans depending on each other, causing injection issues", "A database loop", "An infinite HTTP redirect", "None of these", 0);
        add(c, 3, "What does @ExceptionHandler do?", "Handles exceptions thrown by controller methods", "Throws exceptions", "Logs requests", "Starts transactions", 0);
        add(c, 3, "What is the purpose of Spring Boot Actuator?", "Provides production-ready features like health checks and metrics", "Compiles code", "Renders HTML", "Manages DB migrations", 0);
    }
}