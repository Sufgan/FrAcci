# FrAcci (from accidents)

**FrAcci** is a final project for a Java course, developed in collaboration with **Nastya**, aimed at reducing the risk of secondary accidents on the road (post-accident collisions). The key idea is that once a user running the app is involved in an accident, the accident’s coordinates are sent to a server, and all nearby drivers (within a 1 km radius) heading in the accident’s direction are notified immediately.

---

## Table of Contents

1. [About the Project](#about-the-project)  
2. [Key Features](#key-features)  
3. [How It Works](#how-it-works)  
4. [Technologies Used](#technologies-used)  
5. [Authors](#authors)  

---

## About the Project

**FrAcci** (short for **From Accidents**) helps prevent or minimize post-accident collisions by rapidly alerting drivers to accidents up ahead. Early warnings enable them to slow down, change their route, or otherwise prepare to safely navigate around the incident.

## Key Features

- **Real-Time Accident Detection**  
  Utilizes sensors (e.g., accelerometer, gyroscope, GPS) to detect collisions.

- **Immediate Location Sharing**  
  Sends accident coordinates to the server the moment a collision is recognized.

- **Driver Notification**  
  Alerts drivers within 1 km of the accident site if they are approaching the affected area.

- **Scalable Architecture**  
  Can handle multiple concurrent users and accidents.

- **Modular Design**  
  Allows future enhancements, such as integrating navigation systems or communicating with emergency services.

## How It Works

1. **Detecting the Accident**  
   - The mobile app monitors the device’s sensors for abrupt changes in speed or orientation.  
   - On detecting a collision, it flags an accident event.

2. **Server Notification**  
   - Accident data, including precise coordinates, is sent to the server.  
   - The server logs the event’s timestamp and location.

3. **Alert Nearby Drivers**  
   - The server checks for any users within a 1 km radius who are traveling toward the accident.  
   - Those drivers receive a push notification or in-app message informing them of the hazard ahead.

4. **Real-Time Updates**  
   - Drivers can receive ongoing alerts regarding the status of the accident (e.g., road blockage, additional incidents).

## Technologies Used

- **Java (8+)** — Primary language for both backend and client.  
- **Spring Boot** *(optional)* — For server-side REST APIs.  
- **GPS/Location Services** — To track driver coordinates in real time.  
- **Push Notifications** — (Firebase Cloud Messaging or similar) to issue instant alerts.  
- **Database** — (PostgreSQL, MySQL, H2, etc.) for storing user data, accident info, and more.

> For more details, see the [FrAcci repository](https://github.com/Sufgan/FrAcci).

## Authors

- @sufgan
