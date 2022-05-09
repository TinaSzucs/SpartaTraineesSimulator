# Sparta Trainees Simulator

This program simulates people training in different types of courses in training centres.

Every month between 50-100 new trainees appear wanting to be trained, however between 0-50 new trainees can be trained each month in different courses (Java, C#, Data, DevOps or Business). If a trainee has been in training for 3 months, they are moved to a bench state ready to be handed over to clients. 1 new training centres are created every second month, but if a centre has fewer than 25 trainees, it will close. Then the trainees will be then moved to other training centres or at the beginning of the waiting list. There are 3 different type of training centres. Training Hub, which can train a maximum of 100 trainees but only 3 can be open at the time. Bootcamp, which can train a maximum of 500 trainees, but can remain open for 3 months if there are fewer than 25 trainees in attendance. Tech Centre, which can train 200 trainees but only teaches one course per centre. If a client does collect enough trainees from the bench within a year, they will leave happy and return the next year with the same requirement, however if a client does not collect enough trainees from the bench within a year, they will leave unhappy.

After the first year (12 ticks pass by) every month random amount of new clients will be generated, which is based on the users input. These clients will have a requirement of between 15-50 trainees and a randomly selected course type.

# Instructions

After you start the program you need to input a time in months to run the simulation.
Then if you have chosen a length which is over 12 months, it will ask you to input the minimum and maximum limit of the clients appearing each month.
As the last input, it will ask you to choose if you would like a summary data at the end of the simulation or a running output updated each month.
