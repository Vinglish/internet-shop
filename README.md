# Internet-shop

# Table of Contents
* [Project purpose](#purpose)
* [Project structure](#structure)
* [For developer](#developer-start)
* [Authors](#authors)

# <a name="purpose"></a>Project purpose
This is a template for creating an e-shop.
<hr>
It has login and registration forms.

There are controllers for working with products, users, orders and shopping carts:
<hr>
Inject - for injection mock data,

Registration - for registering new users,

Login -  for user authentication and authorization,

Users - for displaying a list of all app users. Available for users with an ADMIN role only,

Products - for displaying  all product in stock,

Shopping Cart - for displaying  user’s carts. Available for users with a USER role only,

Orders - for displaying user’s order history. Available for users with a USER role only,

Logout - for logging out.
<hr>

# <a name="structure"></a>Project Structure
* Java 11
* Maven 4.0.0
* javax.servlet 3.1.0
* jstl 1.2
* apache.logging.log4j 2.13.3
* maven-checkstyle-plugin
<hr>

# <a name="developer-start"></a>For developer

Open the project in your IDE.

Add it as maven project.

Configure Tomcat:
* add artifact;
* add sdk 11.0.3

Add sdk 11.0.3 in project struсture.

Create a schema "internet_shop" in any SQL database.

Use file src.main.resources.init_db.sql to create all the tables required by this app.

At src.main.java.internetshop.util.ConnectionUtil class use username and password for your DB to create a Connection.

At src.main.resources.hibernate.cfg.xml configure Hibernate with correct username and password.

Change a path in src.main.resources.log4j.properties. It has to reach your logFile.

Run the project.

<hr>

# <a name="authors"></a>Authors
* [Vinglish](https://github.com/Vinglish)

