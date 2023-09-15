# Abdellah ORM MVC Library

This library provides a simple and efficient way to work with object-relational mapping (ORM) and model-view-controller (MVC) architecture in your Java applications. With this library, you can easily perform CRUD (Create, Read, Update, Delete) operations on your models and map them to database tables.

<<<<<<< HEAD
## 1. Configuration
=======
## 1. Set Up Databases
To set up dependencies in your Maven project for using either MySQL or PostgreSQL, you can follow these steps:

#### For MySQL:

1.  Open your project's `pom.xml` file.
    
2.  Inside the `<dependencies>` section, add the following dependency for MySQL:
    

xmlCopy code

```
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.49</version> <!-- You can use a different version if needed -->
</dependency>
``` 

3.  Save the `pom.xml` file. Maven will automatically download and manage the MySQL JDBC driver for you.

#### For PostgreSQL:

1.  Open your project's `pom.xml` file.
    
2.  Inside the `<dependencies>` section, add the following dependency for PostgreSQL:
    

xmlCopy code

```
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.2.23</version> <!-- You can use a different version if needed -->
</dependency>
``` 

3.  Save the `pom.xml` file. Maven will automatically download and manage the PostgreSQL JDBC driver for you.

By adding these dependencies to your project's `pom.xml`, you ensure that the necessary database drivers are available for connecting to MySQL or PostgreSQL, depending on your requirements. Maven will handle the download and classpath configuration for you, making it easy to work with these databases in your Java application.

### download dependencies


To run Maven from the command line, follow these steps:

1.  **Open a Command Prompt (Windows) or Terminal (macOS and Linux)**:
    
    -   On Windows: Press `Win + R`, type `cmd`, and press Enter.
    -   On macOS: Press `Cmd + Space`, type `Terminal`, and press Enter.
    -   On Linux: You can usually open a terminal by pressing `Ctrl + Alt + T` or searching for "Terminal" in your applications.
2.  **Navigate to Your Project Directory**:
    
    Use the `cd` (Change Directory) command to navigate to your Maven project's directory. For example:
    
    shellCopy code
    
    `cd path/to/your/project` 
    
    Replace `path/to/your/project` with the actual path to your Maven project directory.
    
3.  **Run Maven Commands**:
    
    You can now run Maven commands from the command line. Here are some common Maven commands:
    
    -   **Compile Your Project**:
        
        shellCopy code
        
        `mvn compile` 
        
    -   **Run Tests**:
        
        shellCopy code
        
        `mvn test` 
        
    -   **Package Your Project (Create a JAR, WAR, or Other Artifact)**:
        
        shellCopy code
        
        `mvn package` 
        
    -   **Clean Compiled Files and Target Directory**:
        
        shellCopy code
        
        `mvn clean` 
        
    -   **Install Your Project in the Local Repository**:
        
        shellCopy code
        
        `mvn install` 
        
    -   **Run a Specific Goal/Plugin (Replace `your-goal-or-plugin`)**:
        
        shellCopy code
        
        `mvn your-goal-or-plugin` 
        
    
    Replace `your-goal-or-plugin` with the actual Maven goal or plugin you want to run.
    
4.  **Maven Wrapper (Optional)**:
    
    Some projects use a Maven Wrapper script (`mvnw` or `mvnw.cmd`) that allows you to run Maven without having to install it globally. If your project includes a wrapper script, you can use it instead of the `mvn` command. For example:
    
    shellCopy code
    
    `./mvnw clean install` 
    
    Note: The `./` is used to execute a script in the current directory (Unix-like systems).
    

That's it! You can now use Maven to build and manage your project from the command line. Be sure to replace the Maven commands with the specific goals or plugins you need for your project.

## 2. Configuration
>>>>>>> ccdba83003af636695165722aa9d9c021ecb6fd4

### App Configuration

1.  Navigate to `dao/utils/config/AppConfig.java`.
2.  Update the following settings:
    -   `APP_NAME`: Set your application's name.
    -   `APP_VERSION`: Specify the application version.
    -   `APP_DEVELOPER`: Enter the developer's name.
    -   `APP_MODE`: Choose between "production" and "development" modes.

### Database Configuration

1.  Go to `dao/utils/config/DatabaseConfig.java`.
2.  Modify the database settings as follows:
    -   `DATABASE_NAME`: Set your database name.
    -   `USERNAME`: Enter your database username.
    -   `PASSWORD`: Provide your database password.
    -   `PORT`: Specify the database port.
    -   `HOST`: Enter the database host.
    -   `DATABASE_TYPE`: Choose between "mysql" and "postgresql" for your database type.

## 2. Create Your Model

1.  Navigate to the `app/model` directory.
2.  Create your model class.
3.  Extend it from `BaseRepository` and implement the `createInstance` method. This method is used to create a new instance of your model class for data mapping.

Example:

javaCopy code

```
public class User extends ModelMapper<User> {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role;

    @Override
    public User createInstance() {
        return new User();
    }
}
```
## 3. Create Your Repository

1.  In the `app/repository` directory, create your repository class.
2.  Extend it from `BaseRepository`.
3.  In the constructor, call the `super` constructor and pass your model class.

Example:

javaCopy code

```
public class UserRepository extends BaseRepository<User> {
    public UserRepository() {
        super(User.class);
    }
}
```



Use `super(User.class)` to invoke the constructor of `BaseRepository` and pass the model class as an argument.



### 5. Create Your Service

In the previous section, we created a basic `UserService` class that interacts with the `UserRepository`. With the introduction of the `CrudService` interface, your service class has become more standardized and can be used as follows:

javaCopy code

```
public class UserService implements CrudService<User> {
    private static final User user = new User();
    private static final UserRepository userRepository = new UserRepository();

    @Override
    public User save(User user) {
        return user.mapData(userRepository.save(user));
    }

    @Override
    public User select(int id) {
        return user.mapData(userRepository.find(id));
    }

    @Override
    public List<User> selectAll() {
        return user.mapDataList(userRepository.findAll());
    }

    @Override
    public User update(User user, int id) {
        return user.mapData(userRepository.update(user, id));
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }
}
``` 

By implementing the `CrudService` interface, your `UserService` class adheres to a standardized set of CRUD (Create, Read, Update, Delete) methods, making it easier for developers to use in their applications.

Developers should use the `UserService` class to perform operations on `User` objects, following the standard CRUD methods provided by the `CrudService` interface. This allows for consistent and predictable interactions with your application's data layer.

### 6. Test Your Code

You can now use your enhanced service class in your application's main class to perform various operations on your model objects. Here's a sample of how to use the updated `UserService`:

javaCopy code

```
public class App {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserService();

        // Create a new user
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setPhone("+1234567890");
        user.setAddress("123 Main St");
        user.setRole("user");

        // Save the user to the database
        userService.saveUser(user);

        // Update the user's information
        User updatedUser = userService.getUser(1); // Replace 1 with the actual user ID
        updatedUser.setName("Updated Name");
        userService.updateUser(updatedUser, 1);

        // Delete a user
        userService.deleteUser(2); // Replace 2 with the actual user ID to delete

        // Retrieve all users
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println); // Print all users
    }
}

<<<<<<< HEAD
```
=======
```
>>>>>>> ccdba83003af636695165722aa9d9c021ecb6fd4
