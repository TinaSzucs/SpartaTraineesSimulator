# Sparta Trainees Simulator

## Submission

This project will be submitted to your trainer, by email or Microsoft Teams chat message, as a GitHub link on Monday at 09:00. If you want to make the repository private on GitHub, invite your trainer to collaborate on the project and also send them an email explaining which GitHub user name is yours. In addition, a brief (10 mins max) presentation on the outcome of the project will be made on Friday at 16:00 (subject to no other academy presentations being scheduled to conflict with this).

## Groups

### Javanese Crew

- **Callum Peden** Scrum Leader
- Tina Szucs
- Alex Costi
- Michel Jean

### The Dockers

- **Usama Navid** Scrum Leader
- Andy Marincas
- Mauro Sorrentino
- Reman Teladia

## Overview

You have been asked to build a simulator which will help track the number of people currently training.

## Stage 1 requirements:

- The tracker needs to be able to track time in a consistent way.
- The program starts by asking how long the simulation will run for (for example, how many months).
- Every month, a random number of trainees are generated, wanting to be trained (50 - 100).
- Every 2 months, Sparta Global opens training centres; they open instantly and can take trainees every month.
- A centre can train a max of 100 trainees and takes a random number of trainees every month (0 - 50 trainees up to their capacity).
- If a centre is full, trainees can be moved to any other centre which is not full.
- If all centres are full, the trainees go onto a waiting list; this list must be served first before new trainees are taken.
- At the end of the simulation, output should show:
	- number of open centres
	- number of full centres
	- number of trainees currently training
	- number of trainees on the waiting list

## Stage 2 requirements:
- Sparta will now check centres each month.
	- If a centre has fewer than 25 trainees, it will close.
	- The trainees will be randomly moved to another suitable centre.
- The simulation should now offer the choice of summary data at the end of the simulation or a running output updated each month.
- Trainees will now have a course type (Java, C#, Data, DevOps or Business); a trainee will be randomly assigned a course when they are created and this will never change.
- Sparta now has 3 different types of centre - when a new centre can be opened, one of the following will be randomly chosen:
	- Training Hub: can train a maximum of 100 trainees, but 3 (randomly 1-3) can be opened at a time each month.
	- Bootcamp: can train a maximum of 500 trainees, but can remain open for 3 months if there are fewer than 25 trainees in attendance. If a Bootcamp has 3 consecutive months of low attendance, it will close. For the lifetime of the simulation, only 2 Bootcamps can exist at a time.
	- Tech Centre: Can train 200 trainees but only teaches one course per centre. This is chosen randomly when a Tech Centre is opened.
- The simulation should report on the following:
	- number of open centres (breakdown for each type)
	- number of closed centres (breakdown for each type)
	- number of full centres (breakdown for each type)
	- number of trainees currently training (breakdown for each type)
	- number of trainees on the waiting list (breakdown for each type)

## Stage 3 requirements:
- If a trainee has been in training for 3 months, they are moved to a bench state.
- Clients will begin to be randomly created after 1 year of the simulation.
- A client will have a requirement when they are created (for example, a need for 27 Java trainees). The requirement can be any value greater than or equal to 15.
- A client will take a random number of trainees from the bench each month (1 - full requirement) until their requirement is met.
- A client will only take one type of trainee (Java, C#, Data, DevOps or Business).
- If a client does not collect enough trainees from the bench within a year, they will leave unhappy.
- If a client does collect enough trainees from the bench within a year, they will leave happy and return the next year with the same requirement.
