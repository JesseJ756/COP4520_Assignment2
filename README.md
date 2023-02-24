Problem 1: Minotaur’s Birthday Party

How to run through terminal. - Navigate to the src directory - Run "javac BirthdayParty.java" then "java BirthdayParty"

The protocol that BirthdayParty.java and EnterLabrinth.java uses to solve the first problem goes as the following: 
- There are two types of guests: the counter guest (guest 0) and the other non-counter guests.

    - When a non-counter guest comes in and
        - They havnt been in the cave before. If theres a
            - Cupcake - eat
            - No cupcake - leave
        - They’ve been in the cave before and ate a cupcake. If theres a
            - Cupcake – leave
            - No cupcake – leave

    - Counter guest comes in and if 
        - its their first time 
            - increment counter 
        - No cupcake
            – check counter to see if counter = guest num 
                - If yes then signal everyones been there 
                - If no add to counter and leave cupcake 
        - Cupcake 
            - Leave

Using lock/unlock we can simulate that the users are going in one after another and uncommenting out the comments show the random guest comming in and deciding what to based on whether they have ate a cupcake or not. Running it a couple of times show that because of repetition of guests being invited, there can be a variety with which the program is finished.

-------------

Problem 2: Minotaur’s Crystal Vase

How to run through terminal. - Navigate to the src directory - Run "javac CrystalVase.java" then "java CrystalVase"

I think that the best strategy out of all three would be the second one. One advanatage that it has is that it doesnt take up to much overhead for the strategy, just the sign. It also allows the guests to quickly check the sign before doing something else. One disadvantage is that the guest will have to be lucky when checking the sign and depending on how many times a guest can view the vase, it may take a while before a new guest can see it for the first time.
