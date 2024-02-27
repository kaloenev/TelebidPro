I have chosen Java to implement the assignment as it is a first choice nowadays for web development and happens to be the language, that I have the most experience with. The dependencies in my application are
stored in a xml format, in the pom.xml file. Those include Tomcat, Jsp, Junit, javax Mail, java Awt and Mockito. At the beginning a connection with a MySQL database is established using JDBC. A servlet listener is also
implemented in the DatabaseInitializer class, which checks if a database with the needed users table is already established on port 3306 and if not creates it. The users table contains the automatically generated
Ids (specified in the UserDao class on registration) a name, a username and password for login, an email for verification of the registration and password reset, a isEnabled boolean variable to allow only users with
a verified Email to use the app and the last verification/password reset token. The passwords are hashed using the PBKDF2WithHmacSHA1 algorithm in the UserServlet class with Java native library,
the hash is saved in the database and used for the login. As the servlet I used Tomcat because of it's convenient implementation of HttpServlet-Request/-Responses, which I used to extract and integrate parameters
in html documents to be then displayed with the help of Jsp. Although a framework, it is one of the core JEE technologies and does not allow anything further than the integration of Java code in the html document,
much like Javascript does. For example the register.jsp page is first displayed using a GET request and then can be filled out and sent as a POST request for the user to be registered in the database thanks to jsp.
After the registration, an Email is sent with SMTP server on port 587 using the EmailSender class. After the confirmation of the email the user is redirected to the login page (login.jsp) by the VerificationServlet
class or an error is displayed. On login the user must also solve a captcha that represents an image with 6 symbols on it created using the already implemented functions in the awt api,
one of the first graphics libraries in java. The algorithm for the captcha generation is integrated in the captcha.jsp file. The captcha is stored in the session and compared to the one the user solved in the
LoginServlet class. Then after authentication a session id is sent after a login to be stored in a cookie, which is then used in all other servlet methods to authenticate the user. From then the user can edit their profile,
as well as delete it, for which his username stored in the session parameters is used to query the database. The logic for these 3 function (getting user data, editing and deleting the profile) is mainly in the UserDao
and servlet classes with the corresponding names. The display part is played by the editprofile.jsp, from which there is a link to also request a password reset multiple times as per the assignment.
From there the Email is directly queried using the username from the session and an Email with the corresponding link is sent. The the user may access the password-reset.jsp document and type in the new password.
Of course the token from the email is also automatically sent together with the new password to ensure that some other user has not accessed this screen without having access to the given Email.
Finally we have the JUnit with Mockito test cases, which cover the two Dao Classes and most of the Servlets. In some classes mocking the JDBC connection has proven very difficult to Mock,
but the most functions have been covered by at least one unit test. Overall I believe I have implemented all the required functionalities. Another function that I used, but have not implemented myself would be
the java.io UUID class for the generation of a random 128 bit String value for the Email verifications.
