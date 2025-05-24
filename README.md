# Introduction

The Railway Reservation System is a Java-based application designed to manage train ticket bookings. It provides a user-friendly graphical interface built with Java Swing, allowing users to:

- Book tickets  
- Cancel bookings  
- Search for passenger details  
- View the reservation chart  
- Check the availability of unbooked tickets  
- Exit the application

The system supports three travel classes—**AC**, **First**, and **Sleeper**—each with distinct fare rates and seat capacities.

# Main Menu

Upon starting the application, the main menu presents the following options:

- **Book Ticket**: Initiate the ticket booking process  
- **Cancel Ticket**: Cancel an existing booking  
- **Search Passenger**: Look up passenger details using a passenger number  
- **Reservation Chart**: View the list of booked passengers by class  
- **Unbooked Tickets**: Check the number of available tickets in each class  
- **Exit**: Close the application with a farewell message

# Booking a Ticket

To book a ticket:

1. Click the **"Book Ticket"** button on the main menu  
2. A booking form will appear with these fields:
   - **Class**: Select from AC, First, or Sleeper  
   - **Departure**: Choose the departure station (e.g., Karachi, Hyderabad)  
   - **Destination**: Choose the destination station  
   - **Distance (km)**: Automatically calculated based on station selections  
   - **Fare per Ticket**: Automatically computed based on class and distance  
   - **Name**: Enter the passenger’s name  
   - **Age**: Enter the passenger’s age  
   - **Phone Number**: Enter the passenger’s phone number  
   - **Number of Tickets**: Select a number between 1 and 10  
3. Fill in all fields, ensuring departure and destination stations differ  
4. Click **"Book"** to submit the booking  
5. A confirmation message will display booking details and the total fare  
6. If insufficient tickets are available in the selected class, an error message will appear

# Canceling a Ticket

To cancel a ticket:

1. Click the **"Cancel Ticket"** button on the main menu  
2. Enter the passenger number of the ticket to cancel  
3. Click **"Cancel Ticket"**  
4. If the passenger number exists:
   - The ticket is canceled  
   - A refund is calculated based on the class:
     - AC: 80% of the fare  
     - First: 75% of the fare  
     - Sleeper: 70% of the fare  
   - A confirmation message shows the cancellation details and refund amount  
5. If the passenger number is invalid, an error message is displayed

# Searching for a Passenger

To search for a passenger:

1. Click the **"Search Passenger"** button on the main menu  
2. Enter the passenger number in the provided field  
3. Click **"Search"**  
4. If found, the passenger’s details are displayed, including:
   - Passenger number, name, class, phone number, age, journey details, distance, and fare  
5. If not found, a **"No such passenger found"** message is shown

# Viewing the Reservation Chart

To view the reservation chart:

1. Click the **"Reservation Chart"** button on the main menu  
2. A tabbed interface appears with three tabs:
   - **AC Class**: Lists all AC class passengers  
   - **First Class**: Lists all First class passengers  
   - **Sleeper Class**: Lists all Sleeper class passengers  
3. Each tab displays passenger details in a table format: passenger number, name, age, departure, destination, distance, and fare

# Checking Unbooked Tickets

To check available tickets:

1. Click the **"Unbooked Tickets"** button on the main menu  
2. The screen displays the current number of available tickets:
   - **AC Class**: Remaining out of 75  
   - **First Class**: Remaining out of 125  
   - **Sleeper Class**: Remaining out of 175

# Exiting the Application

To exit:

1. Click the **"Exit"** button on the main menu  
2. A farewell message appears, crediting the project contributors:
   - Abdul Gaffar  
   - Hassaan Ahmad  
   - Abdullah Nadeem  
   - Muhammad Rahat  
3. The application closes

# Additional Information

- **Station Distances** (in km):
  - Karachi: 0  
  - Hyderabad: 210  
  - Sadiqabad: 670  
  - Multan: 925  
  - Lahore: 1270  
  - Rawalpindi: 1440  
  - *Distance is the absolute difference between departure and destination distances*
  
- **Fare Calculation**:
  - AC: ₹6.0 per km  
  - First: ₹5.0 per km  
  - Sleeper: ₹4.0 per km  
  
- **Seat Availability**:
  - AC: 75 seats  
  - First: 125 seats  
  - Sleeper: 175 seats  
  - *Bookings reduce availability; cancellations increase it*

# Conclusion

The Railway Reservation System offers a simple yet effective solution for managing train ticket bookings. It demonstrates Java Swing for GUI development and basic data handling with collections, providing a practical example of a reservation system.
