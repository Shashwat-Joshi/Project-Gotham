# Basic Authentication !

This is a low level implementation of a basic authentication mechanism using email and password.

The key takeaway from this project was:
- How to store passwords in DB using **BCrypt**.
- Importance of using **salts** and **pepper** and how they decrease the chances of **rainbow table** cracking the password.
- Writing success only code flow and throwing exception which are further handled by **ControllerAdvice** for a cleaner code.
- API Contracts and using a consistent response format for better handling at Front End.


## Architecture

Tool used [draw.io](https://www.drawio.com/) to create architecture diagram.

![Architecture](https://raw.githubusercontent.com/Shashwat-Joshi/Project-Gotham/main/Authentication/basicauthentication/gotham/BasicAuthCodeArchitectre.jpg)

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)