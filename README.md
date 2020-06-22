# CPSC-329-Unessay

Follow this to set up github on eclipse: https://github.com/collab-uniba/socialcde4eclipse/wiki/How-to-import-a-GitHub-project-into-Eclipse

This project attempts to come up with a total number of guesses for different types of password guessing algorithms, and shows how many guesses it takes different attacks to break different styles of passwords.

Here's some more in-depth explanation about this project.

- Total Guesses: The way we calculated total number of attempts is by taking the quickest algorithm that arrived at that password and multiplying it by the number of password-generation algorithms we have implemented. A chain is only as strong as its weakest link, and therefore a password that is weak against only 1 type of attack is as strong as a password that is weak against all types of attacks.

- Time Calculation: 
Based on the linked source[0], a good generalization to get the number of MD5 hashes/second is to multiply the shader clock by the number of cores / 8 (pg 40), and extrapolate from the chart (pg 56). For Nvidia, the shader clock is almost always twice the speed of the core clock[1]. Since each attack’s  instruction cycles per guess largely depends on the algorithm being implemented, we aimed for accuracy and decided to factor out the time it takes for an algorithm to generate a guess, leaving a floor for the number of guesses per second equal to the number of MD5 hashes per second.

[0]https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&cad=rja&uact=8&ved=2ahUKEwjgjIi4-5HqAhUWGTQIHVJDDrQQFjAEegQIBRAB&url=https%3A%2F%2Fwww.ru.nl%2Fpublish%2Fpages%2F769526%2Fthesis.pdf&usg=AOvVaw11lbEV0yAlmMrS5GQ-paYO
[1]https://linustechtips.com/main/topic/182694-whats-the-difference-between-shader-clock-and-core-clock/

We chose different hardware based on 

When comparing and contrasting our estimated time guesses with different password strength sites, we found it worrying that on some sites passwords that would take quadrillions of years to break would be cracked in days on other sites due to the following logic. It's dangerous to assure users that their passwords are strong when they are actually weak, because users tend to use very strong passwords repeatedly. What our project shows users are how different types of algorithms arrive at different guess amounts.
