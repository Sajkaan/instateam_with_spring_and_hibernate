This application is a tool that allows a user to manage software projects.  The app provides users with a way to see all their projects, statuses and project collaborators in one place.  The user can add projects, roles and collaborators and each project can have multiple roles.  Collaborators can be assigned to each project by role from a list of collaborators.

This app has persisted data using an H2 database and manages data with Hibernate.

What this app does:

- Serves dynamic web content according to URI, including index and detail pages
- Includes database connectivity, where data is stored
- Allows a user to perform CRUD (create, read, update, delete) operations on data
- Performs server-side form validation for adding/updating
- Uses a database
- Serves static assets

Application uses server mode - in order to run, start the server first with the command line:

java -cp h2*jar org.h2.tools.Server

Once the browser window opens, add the following to the JDBC URL field: jdbc:h2:tcp://localhost/./data/instateam

The user name and password fields are blank.