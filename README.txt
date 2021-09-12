=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2-D Arrays: I use the 2-D Arrays to store the values in the game. It's a 5 by 7 array where 
  each entry marks a possible location for a target to spawn. The entries range between 0 and 7, 
  where each number marks a different type of orb. 2D arrays was the best thing to use in this case 
  because it is the easiest way to simulate the board state in a simple manner. 

  2. Collections: I used a List to allow for one of the types of orbs that I spawn-- there's a orb 
  that flips the sign of the previous change in score, and so I store the changes in score 
  in a List. The List is the best thing to use in this context, because there are duplicates, and I 
  want to be able to easily access the last and first entries of the list. 

  3. File I/O: I use file I/O to store high scores-- I only keep the top three scores, but they're 
  stored in a file that basically just has three lines of score-username format. 

  4. Testable Component-- All of my tests test the core logic of the AimTrainer.java file, which 
  is the main logic of my game. I can test the 2D array to make sure it's detecting clicks, 
  as well as the spawn function and changes in difficulty. 

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
AimTrainer is the main logic behind the game-- it defines the 2D array and has all of the 
spawning/what happens when you click an orb logic. It also contains the difficulty settings, 
which determine the types of orbs that spawn. GameCourt is more or less the rest of the logic-- 
it contains a lot of the shifts in JLabels that are seen from outside, and it detects mouseclicks
and other stuff. The Game.java file is just to create the frame and make it look nice. 

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
I definitely had quite a bit of trouble figuring out how to implement collections in a cool manner, 
which is why I ended up adding a new orb that flips the sign of the scores. 

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
I think that the design is pretty solid given what I tried to do; if I had another chance to design
this, I would've rethought how I store high scores, since I don't think that the method 
I use right now is necessarily the best. 


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
