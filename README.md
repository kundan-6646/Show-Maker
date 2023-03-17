
# Show Maker

* Show-Maker is a movie ticket booking system project
* It's backend code written using Spring-Boot
* It consists of Different Layers like Controller, Service, Repository , DTO(Data Transfer Object)
* These layers in the project increases its code maintanablity, understadability and readablity




# Functionality



## I. For Admins :
Admins can :-
- Add a Movie.
- Add a Theater.
- Add Theater Seats
- Create a Show for any Movie in any theater
- Create Show Seats for booking purpose
- Get the movie having maximum shows



## II. For Users :

## Users can :-
- User can Register
- Get the Show.
- Can see the Show Seats
- Can Book the Ticket for available seats
- Can Cancel the Ticket




# Different Models / Entities / (Table in MySQL) In the Project
- User Entity
- Movie Entity
- Theater Entity
- Show Entity
- Theatres Seat Entity
- Show Seat Entity
- Ticket Entity



# Teck-Stack Used :
- Spring-Boot
- MySQL
- Java
- Hibernate
- JPA

# Schema Design

![image](/screenshots/schema.png)








# Booking Confirmation Email

![image](/screenshots/mail-1.png)
![image](/screenshots/mail-2.png)



# Cancel Ticket's Confirmation Email

![image]()



# Admin API's

- Get Movie Having Maximum Shows Scheduled



![image](/screenshots/get-shows-max.png)

- Add Show




![image](/screenshots/show-add.png)

- Add Movie



![image](/screenshots/movie-add.png)

- Add Theatre



![image](/screenshots/theatre-add.png)


# User API's

- Register




![image](/screenshots/user-register.png)

- Book Tickets




![image](/screenshots/ticket-book.png)

- Cancel Tickets




![image]()
