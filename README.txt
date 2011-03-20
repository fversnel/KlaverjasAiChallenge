(Note: the following information is in English, for a Dutch version of this
document you can open the LEESMIJ file.)

KlaverjasAIChallenge is a game engine for the dutch card game Klaverjas that
lets AI's compete with each other.

To write an AI for the klaverjasaichallenge, the following information is of
use to you:
- Source code can be checked out using Mercurial:
  hg clone https://klaverjasaichallenge.googlecode.com/hg/ klaverjasaichallenge
- In package src/org/klaverjasaichallange/ai there is an example of a very
  simple AI to get you started.
- The AI has to implement the KlaverJasAI interface.
- The shared package provides useful (helper) classes for your AI. Use it to
  code and compile your AI.
- The AI has to reside in the package: org.klaverjasaichallenge.ai
- The AI must not communicate with his teammate.
- The AI must not cheat, but if he does the application will crash with a
  CheatException.
- To run your AI you can download the klaverjas.jar file from the google code
  site or compile it from source using Ant.

The rules of klaverjas can be found here:
- http://en.wikipedia.org/wiki/Klaverjas

Glossary for klaverjas related terminology:
Trump = the suit to rule all others.
Trick = 4 cards, from each player 1 card.
Round = 8 tricks.
Game = 16 rounds.
Hand = the cards that are in the players hands (each player starts with 8
cards).
Rule set = the rule set that is used. Currently only the Rotterdam variation is
implemented. In the future the Amsterdam variation could also be implemented.
Team = 2 cooperative players.
CheatException = Will occur when an AI cheats. For example, by playing a card
that's not allowed by the rule set, and by playing a card that is not in his
hand.
Order = Used for identifying (for example) the rank of a card. A higher
order means a better rank.
