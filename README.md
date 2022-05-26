# thesis-ch8.1
The repository contains the source code for Chapter 8, Section 8.1 - Trade.

Folder *InstitutionAwarePlanning* contains the complete source code for institution framework. Short description of the folders is as follows:

- framework - the source code for the framework (Institution, Domain, Grounding and Utility classes)
- institutions - the specifications of institutions used in use cases.
- translator - the code that translates specifications into planning domain language and utility classes/methods.
- planning - the folder contains *InstPlanner.java*, which is the entry point for the application. It instantiates institution, grounding, and domain; translates specifications into a planning domain language, starts planning, dispatches actions to robots, and creates timelines. Similarly, *Verifier.java* is the entry point for verification.

Other folders provide communications with other software components to obtain values of state-variables.






