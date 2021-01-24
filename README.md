## MySQL Final Project

By Mike Franzen, Luis Gonzalez, Paul Willis

We used the emplyees database from https://github.com/datacharmer/test_db for testing, just adding our sql tables to it, but this can be replaced in reservationSRC/DBconnection.java

Please remember to update your database access credentials in reservationSRC/DBconnection before testing.

# Room Reservation Manager
- Users are prompted to sign in with first and last name
- If they don't exist in the user table, a new entry is made for them
- After sign-in, users are presented with a menu of options to select from including "Show Your Reservations", "Make Reservation", "Cancel Reservation", "Sign out", and "Quit"
- Make Reservation only shows rooms available for the requested date
- Reservations are for all-day, not time blocks

## An admin (or "backend") mode could be added in the future with features such as:
- Allow the user to look up rooms by id and view information like all reservations with users
- Lookup reservation by ID to confirm the user, date, and room
- Delete a user and all related reservations
- Add and remove rooms
