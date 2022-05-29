Here, I am interacting with JDBC API to get Connection.

You can execute `mvn test` to test the JDBC connections against sql (postgres) server.

At [JdbcConnectionTest.java](src/test/java/com/ubaid/forj/JdbcConnectionTest.java) there are following test cases:
1. `testConnectionFromDriverManager` this method test the connection which is directly got from DriverManager
2. `testConnectionUsingPGSimpleDataSource` this method test the connection which is got from PGSimpleDataSource.
   1. PGSimpleDataSource is behind the scene using `DriverManager` to get Connection
   2. It do not support connection pooling


Note: Here, I am using Test Containers, so there is no need of any external db.