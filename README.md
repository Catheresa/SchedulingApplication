# SchedulingApplication
Application Title: Scheduling Application. 
Purpose of Application: Allows customer appointments to be scheduled.

Author: Catheresa Stewart

Intellij: Intellij IDEA 2021.1.3 (Community Edition)
SDK: SDK 17 java version "17.0.1"
JavaFX: javafx.runtime.version: 17.0.2-ea+3

How to run program:
1) Login screen will load.
	a) Enter "Username" and "Password" and select "Submit".
 	b) A popup box will appear to let the user know if there is an appointment within the next 15 minutes of login.
2) User is routed to a customer screen where a user can do the following:
	a) View a list of customers.
	b) Search a list of customers by customer name and customer ID.
	c) Navigate to screen where a customer can be added by clicking "Add".
	d) Select a customer from the list and navigate to screen where that customer information can be updated by clicking "Update".
	e) Select a customer from the list and delete that customer and their appointments by clicking "Delete". Custom notifications will appear.
	f) Navigate to an appointments screen by clicking on "Appointments".
		1) View a list of customers by all, week, or month views.
		2) Navigate to screen where an appointment can be added by clicking "Add".
		3) Select an appointment from the list and navigate to screen where that appointment information can be updated by clicking "Update".
		4) Select an appointment from the list and delete that appointment by clicking "Delete". Custom notifications will appear.
		5) Navigate to a customer screen by clicking on "Customers".
		6) Navigate to a report menu by clicking "Reports".
		7) Exit the program by selecting "Exit Screen".
	g) Navigate to a report menu by clicking "Reports".
		1) Contact Schedule - user has the ability to filter a list of appointments by a contact. 
		2) Customer By Division Schedule - user can view a list of customer by division (city/province).
		3) Total Appointments - user can see the number of appointments scheduled by "Type" and by "Month".
		4) Exit Screen - returns user to the customer screen
	h) Exit the program by selecting "Exit Screen".


Description of additional report:
1) User has the ability to filter a list of appointments by a contact.  All appointments for the selected contact will appear.

MySQL Connector: mysql-connector-java-8.0.25
