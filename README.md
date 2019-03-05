# SuperHero
A full stack web project that incorporates MySQL as the database, Java as the business logic, and web UI as the view.  It is a hero watchlist that stores data on many heroes, their individual powers, organizations, and sightings.  The front end allows buttons and nav tabs to get around.  Every piece of data is fully C.R.U.D.able.  The overall organization is Spring MVC pattern.

**Database**

I used MySQL to persist the changes and information. I gather information from the table using the rowMapper interface.  For organization I use 2nd form normalization and bridge tables.

**Web UI**

For my front end I use HTML, CSS, Bootstrap, and JQuery.  It communicates by httpServlet requests, JSON formatted data.  To receive information I use Models.

**Java**

Java is the language chosen for the business logic.  I keep things loosly couped using dependency injection with interfaces.  I also perform try-catches on all incoming data to prevent crashes and dirty data.
