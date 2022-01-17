# Flashcards
 Simple flashcards app to help with memorization of for example vocabulary, capitals... Create cards and organize them into decks.
 Use commands to control the app.
 
Commands list:

exit:
exits application

addDeck:
Creates a new deck
<name>
[order] (time, reverse_time, score, reverse_score)
[new cards limit]
[repeated cards limit]

addCard
Creates a new card and adds it to a deck
<deck>
<front side>
<back side>

removeDeck
Removes the deck
<deck>

removeCard
Removes the card from the deck
<deck>
<card id>

browseDecks
Lists all decks and some info about them

browseCards
Lists all cards in the deck and some info about them
<deck>

setOrder
Sets order of cards in the deck
<deck>
<order method>

setLimit
Sets the limit of new or repeated cards in tests
<deck>
<card type> (new, repeated)
<limit>

study
Starts a test
<balíček>

renameDeck
Sets the name of the deck
<old name>
<new name>

setSide
Sets content of one of the card's sides
<deck>
<card id>
<side> (0 - front, 1 - back)
<new content>
