Voraussetzungen:

TomEE 1.5.1
Mysql DB

Vorgehen:
- Datenbank mit Namen "forum" erstellen 
- Tabelle über DB-Skript erstellen
- Konfiguration der Datenbank in der <tomcat_home>/conf/tomee.xml eintragen

<Resource id="forum" type="DataSource">
  
JdbcDriver com.mysql.jdbc.Driver
  
JdbcUrl jdbc:mysql://127.0.0.1:3306/forum
  
UserName root
  
Password
  
JtaManaged true

</Resource>


- MySQL Datenbanktreiber in <tomcat_home>/lib/ ablegen
- Anwendung bauen und in <tomcat_home>/webapps/ ablegen
