Database Setup on Windows

1. install MariaDB with Xampp. 
2. launch the apache and MySQL servers in the Xampp control panel. Default port they should be running on is 3306.
3. Click "Admin" next to MySQL in the Xampp control panel and and use PHPmyadmin to create a user with all permissions. 
Default user credentials are:
username: sushifast
password: nomnomnom

4. Import sushifast.sql into phpmyadmin creating a new database also called sushifast
5. ensure the included jar file "mariadb-java-client-2.3.0" is in your build path in your ide for the imported project.
6. Run it.