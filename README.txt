This program simulates a library system in which the user (or users) comes in and interacts with the program as they would with an actual library employee. It utilizes interactive user input, file inputs, and StdAudio, and it was inspired by my proclivity for fiction podcasts and an interest in choose-your-own-adventure games/books/shows. I wanted to incorporate books and sound into each other somehow, with choosing your own adventure as a framework.

SOME USAGE NOTES:
- Please unzip the audio files and put them into /src, and then unzip the book files and put them into /project. I'm not sure if this is how it works on every computer, but it's how the program works for me.
- Please expand the console to fullscreen; it will make reading easier.
- I personally suggest "borrowing" when successful and "paying a fine" with 'cash' as the input for fun audios. This will make sense later.

Because this program utilizes interactive user input, there are few command-line arguments needed; the only one that the user needs to provide is the number of patrons (minimum 1, maximum 5) that will be coming into the library today.

First, the user is welcomed by printed welcome message accompanied by an audio greeting that I recorded. (There will be audio to accompany all the printed text throughout the program.) The user will then be given an account number and asked what their purpose is for coming to the library today. The options are returning, borrowing, renewing, placing a hold, or paying a fine. This option has to be typed in all lowercase into the console when prompted.

Then, the user will be guided to the next steps. The user needs to type in inputs when prompted, and these inputs could be integers (for naming a book), letters (y/n), or various other simple commands.

When the user is done with their first action, they will be given the option to select another purpose for being at the library so that the session can continue longer, racking up more borrowed books, renewals, holds, or even fine payments. Then, when the user decides the end their session, a bell will ring and the next patron will be called forward. (At this point, no input is necessary; the user only needs to wait.)

The number of renewals allowed, statuses of borrowed books, and fine balances should remain consistent within each patron and across every patron. For example, if a book was borrowed by the first patron, then that same book should come across as unavailable for the next patron. The initial availability of every book is randomized at the very beginning, as are fine balances.

When the user borrows books or explicitly asks to check the readability of a book, a readability analysis will be performed using the Coleman-Liau index, and the user will be shown the reading level as well as the academic grade associated with it.

The usage of this program should be rather straightforward; just type in inputs when prompted (and only when prompted), enjoy the sounds, and have fun choosing your own library adventure :)