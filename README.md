[![Build Status](https://buildhive.cloudbees.com/job/SelfStudyGuide/job/repo/badge/icon)](https://buildhive.cloudbees.com/job/SelfStudyGuide/job/repo/)

**Self Study Guide** is a hobby project which were written by Maxim Bryzhko.
 
By this project I targeted to aim:
* Helping my friend in his PhD work where he needed application of self study guide for student of foreign  
language classes. For various reasons this project is not in live :(
* Challenge myself with making project from the cratch. Practice in Test-Driven-Development.
  * Presentation layer is build with GWT. User MVP pattern and Event driven model to decouple view and presenter components. 
  * Communication with a server is implemented based on Command pattern.
  * Application is designed to be potentially horizontally scratch (with sharding data across several db servers)
  * Used technologies: Tomcat, Maven, Spring, Spring Security, Hibernate, Flyway, MySQL, Postgres.
  
Demo applocation is hosted on heroku: http://ssg-demo.herokuapp.com/login.jsp
There is no UI design so dont be afraid.

Also you can check build status on shared jenkins: https://buildhive.cloudbees.com/job/SelfStudyGuide/job/repo/

Everyone can clone this repo, build and run tests on local environment.

