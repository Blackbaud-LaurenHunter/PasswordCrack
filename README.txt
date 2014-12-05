UTEID: lmh2742;krm965;

FIRSTNAME: Lauren; Kyle;

LASTNAME: Hunter; Martinez;

CSACCOUNT: hunter7;kmart;

EMAIL: hunter7@cs.utexas.edu;kylemartinez10@yahoo.com;

[Program 6]
[Description]
PasswordCrack.java was the main file we used to determine the password. We created an array of alphabetical characters, both captial and not, and an array of special charaters to help find the password.  We define a class called User, which contains a users firstname, lastname, salt and encrypted password. We read in the dictonary and password text file, then extracted the information from the password file into a users array list. once we have the word list and user info, we start cracking. our implementation goes through the users list for each attempt, so the easier passwords are cracked first. once a password is found that user is removed from the users list. we got through the scenarios listed on the project page such as trying the users information then manipulating it with the mangles. Once those options fail, we move on to using the words list to generate passwords. We first try using one word, which goes through the entire word list and applies these mangles, then we try two words, which takes each word and combines it with the rest, and then applies the mangles. We use jcrypt.java for encrypting our passwords.  

How to compile and run:
	compile by:  javac *.java
	run by: java PasswordCrack words.txt passwords.txt

[Finish]
 We were able to crack 18/19 out of 20 for the first input and 14 out of 20 for the second input

[Test Cases]
[Input of test 1]
michael:atbWfKL4etk4U:500:500:Michael Ferris:/home/michael:/bin/bash		
abigail:&i4KZ5wmac566:501:501:Abigail Smith:/home/abigail:/bin/tcsh
samantha:(bUx9LiAcW8As:502:502:Samantha Connelly:/home/samantha:/bin/bash
tyler:<qt0.GlIrXuKs:503:503:Tyler Jones:/home/tyler:/bin/tcsh
alexander:feohQuHOnMKGE:504:504:Alexander Brown:/home/alexander:
james:{ztmy9azKzZgU:505:505:James Dover:/home/james:/bin/bash
benjamin:%xPBzF/TclHvg:506:506:Benjamin Ewing:/home/benjamin:/bin/bash
morgan:khnVjlJN3Lyh2:507:507:Morgan Simmons:/home/morgan:/bin/bash
jennifer:e4DBHapAtnjGk:508:508:Jennifer Elmer:/home/jennifer:/bin/bash
nicole:7we09tBSVT76o:509:509:Nicole Rizzo:/home/nicole:/bin/tcsh
evan:3dIJJXzELzcRE:510:510:Evan Whitney:/home/evan:/bin/bash
jack:jsQGVbJ.IiEvE:511:511:Jack Gibson:/home/jack:/bin/bash
victor:w@EbBlXGLTue6:512:512:Victor Esperanza:/home/victor:
caleb:8joIBJaXJvTd2:513:513:Caleb Patterson:/home/caleb:/bin/bash
nathan:nxsr/UAKmKnvo:514:514:Nathan Moore:/home/nathan:/bin/ksh
connor:gwjT8yTnSCVQo:515:515:Connor Larson:/home/connor:/bin/bash
rachel:KelgNcBOZdHmA:516:516:Rachel Saxon:/home/rachel:/bin/bash
dustin:5WW698tSZJE9I:517:517:Dustin Hart:/home/dustin:/bin/csh
maria:!cI6tOT/9D2r6:518:518:Maia Salizar:/home/maria:/bin/zsh
paige:T8jwuve9rQBo.:519:519:Paige Reiser:/home/paige:/bin/bash


[output of test 1]
Cracking Passwords
Password for Michael is michael with encryptedPassword atbWfKL4etk4U
Found in 0.022 seconds 
Password for Abigail is liagiba with encryptedPassword &i4KZ5wmac566
Found in 0.034 seconds 
Password for Maia is Salizar with encryptedPassword !cI6tOT/9D2r6
Found in 0.8170000000000001 seconds 
Password for Benjamin is abort6 with encryptedPassword %xPBzF/TclHvg
Found in 0.971 seconds 
Password for Samantha is amazing with encryptedPassword (bUx9LiAcW8As
Found in 1.454 seconds 
Password for Tyler is eeffoc with encryptedPassword <qt0.GlIrXuKs
Found in 3.575 seconds 
Password for Morgan is rdoctor with encryptedPassword khnVjlJN3Lyh2
Found in 4.881 seconds 
Password for Evan is Impact with encryptedPassword 3dIJJXzELzcRE
Found in 6.882000000000001 seconds 
Password for Rachel is obliqu3 with encryptedPassword KelgNcBOZdHmA
Found in 8.689 seconds 
Password for Caleb is teserP with encryptedPassword 8joIBJaXJvTd2
Found in 9.443 seconds 
Password for Dustin is litpeR with encryptedPassword 5WW698tSZJE9I
Found in 10.001 seconds 
Password for Jack is sATCHEL with encryptedPassword jsQGVbJ.IiEvE
Found in 10.256 seconds 
Password for Alexander is squadro with encryptedPassword feohQuHOnMKGE
Found in 10.752 seconds 
Password for Victor is THIRTY with encryptedPassword w@EbBlXGLTue6
Found in 11.103 seconds 
Password for James is icious with encryptedPassword {ztmy9azKzZgU
Found in 11.379 seconds 
Password for Jennifer is doorrood with encryptedPassword e4DBHapAtnjGk
Found in 42.157000000000004 seconds 
Password for Connor is enoggone with encryptedPassword gwjT8yTnSCVQo
Found in 51.202 seconds 
Password for Nicole is keyskeys with encryptedPassword 7we09tBSVT76o
Found in 57.52 seconds 
Password for Nathan is sHREWDq with encryptedPassword nxsr/UAKmKnvo
Found in 70.272 seconds 
Found 19 out of 20 Passwords

[input of test 2]
michael:atQhiiJLsT6cs:500:500:Michael Ferris:/home/michael:/bin/bash
abigail:&ileDTgJtzCRo:501:501:Abigail Smith:/home/abigail:/bin/tcsh
samantha:(bt0xiAwCf7nA:502:502:Samantha Connelly:/home/samantha:/bin/bash
tyler:<qf.L9z1/tZkA:503:503:Tyler Jones:/home/tyler:/bin/tcsh
alexander:fe8PnYhq6WoOQ:504:504:Alexander Brown:/home/alexander:
james:{zQOjvJcHMs7w:505:505:James Dover:/home/james:/bin/bash
benjamin:%xqFrM5RVA6t6:506:506:Benjamin Ewing:/home/benjamin:/bin/bash
morgan:kh/1uC5W6nDKc:507:507:Morgan Simmons:/home/morgan:/bin/bash
jennifer:e4EyEMhNzYLJU:508:508:Jennifer Elmer:/home/jennifer:/bin/bash
nicole:7wKTWsgNJj6ac:509:509:Nicole Rizzo:/home/nicole:/bin/tcsh
evan:3d1OgBYfvEtfg:510:510:Evan Whitney:/home/evan:/bin/bash
jack:js5ctN1leUABo:511:511:Jack Gibson:/home/jack:/bin/bash
victor:w@FxBZv.d0y/U:512:512:Victor Esperanza:/home/victor:
caleb:8jGWbU0xbKz06:513:513:Caleb Patterson:/home/caleb:/bin/bash
nathan:nxr9OOqvZjbGs:514:514:Nathan Moore:/home/nathan:/bin/ksh
connor:gw9oXmw1L08RM:515:515:Connor Larson:/home/connor:/bin/bash
rachel:KenK1CTDGr/7k:516:516:Rachel Saxon:/home/rachel:/bin/bash
dustin:5Wb2Uqxhoyqfg:517:517:Dustin Hart:/home/dustin:/bin/csh
maria:!cSaQELm/EBV.:518:518:Maia Salizar:/home/maria:/bin/zsh
paige:T8U5jQaDVv/o2:519:519:Paige Reiser:/home/paige:/bin/bash


[output of test 2]
Cracking Passwords
Password for Samantha is cOnNeLlY with encryptedPassword (bt0xiAwCf7nA
Found in 0.608 seconds 
Password for Connor is nosral with encryptedPassword gw9oXmw1L08RM
Found in 0.888 seconds 
Password for Abigail is Saxon with encryptedPassword &ileDTgJtzCRo
Found in 0.888 seconds 
Password for Evan is ^bribed with encryptedPassword 3d1OgBYfvEtfg
Found in 3.1510000000000002 seconds 
Password for Morgan is dIAMETER with encryptedPassword kh/1uC5W6nDKc
Found in 5.2410000000000005 seconds 
Password for James is enchant$ with encryptedPassword {zQOjvJcHMs7w
Found in 5.955 seconds 
Password for Tyler is eltneg with encryptedPassword <qf.L9z1/tZkA
Found in 6.914 seconds 
Password for Nicole is INDIGNIT with encryptedPassword 7wKTWsgNJj6ac
Found in 7.777 seconds 
Password for Alexander is Lacque with encryptedPassword fe8PnYhq6WoOQ
Found in 8.314 seconds 
Password for Abigail is Saxon with encryptedPassword &ileDTgJtzCRo
Found in 11.073 seconds 
Password for Michael is tremors with encryptedPassword atQhiiJLsT6cs
Found in 12.378 seconds 
Password for Jack is ellows with encryptedPassword js5ctN1leUABo
Found in 12.924 seconds 
Password for Nathan is uPLIFTr with encryptedPassword nxr9OOqvZjbGs
Found in 169.828 seconds 
Password for Benjamin is soozzoos with encryptedPassword %xqFrM5RVA6t6
Found in 176.606 seconds 
Found 14 out of 20 Passwords