# Morning Brew

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Morning Brew alerts individuals daily by informing them of the current weather for a specified location at a specified time.

### App Evaluation
- **Category:** Lifestyle
- **Mobile:** This app is primarily developed for mobile. A mobile app will allow clients more convenience to use it whenever they wake up in the morning. Thus, it is more practical to develop this as a mobile app than a desktop app in order for it to suit its purpose.
- **Story:** The app delivers daily notifications regarding weather for the day. The user can see past notifications, change the time they receive daily notifications, and change the weather's location.
- **Market:** It can be marketed to anyone interested in changing or improving their lifestyle. This app also appeals to anyone who wants to know the weather every day without checking their weather app.
- **Habit:** This app will be used every day, once a day. Because the app's purpose is to notify the user every day at a specified time, the user will interact with the app at least once a day.
- **Scope:** In the beginning, the app's functionality is simple and alerts the user with the weather. Its usage could then be broadened to include a calendar and a to-do list. Adding a calendar and a to-do list would allow users to organize their time in one application, establishing its goal as a lifestyle app. 

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can register a new account
* User can login
* User can set location for weather
* User can set time for daily notification
* User can see previous notifications
* User receives daily notification 

**Optional Nice-to-have Stories**

* User can adjust when they recieve notifications(Ex. Mon-Fri)
* User can set profile picture
* User can set location to auto
* Add more content into notifcation
  * Joke of the Day
  * Coffee fact of the day
  * Daily Playlist
* User can opt in for email notification

### 2. Screen Archetypes

* Login
  * User can login
* Register
  * User can register a new account
  * User can set location for weather
  * User can set time for daily notification
* Stream
  * User can see previous notifications
* Settings
  * User can set location for weather
  * User can set time for daily notification

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Stream
* Settings

**Flow Navigation** (Screen to Screen)

* Login
   * Stream
* Register
   * Stream
* Stream
   * Settings
* Settings
   * Stream
    
## Wireframes

### [BONUS] Digital Wireframes & Mockups
<img src="MorningBrew_Wireframe.PNG" >


### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
