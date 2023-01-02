## Collision Machine
In the millions of way to learn the fundamentals of Scala, I chose to implement this collision machine, inspired by the [Nature vs Nurture](https://www.verywellmind.com/what-is-nature-versus-nurture-2795392) debate
along with the [King of Diamonds](https://aliceinborderland.fandom.com/wiki/King_of_Diamonds) episode in the viral TV Show [Alice in Borderland](https://www.imdb.com/title/tt10795658/).

The basic idea is given a fixed set of rules to boxes of 3 different colours, which colour will dominate the rest when unleashed in a battle ground?

With the fixed set of rules, one may try to deduce which colour may win. However, the cyclic nature of the rules leaves the deduction indeterminate. Therefore, each colour has an equal chance of dominating 
another colour. As a result, the only way a specific coloured box may win is therefore to provide an external factor, similar to the environment variable in the [Nature vs Nurture](https://www.verywellmind.com/what-is-nature-versus-nurture-2795392) debate, or similar to the 
way Chishiya tells his opponent what move he is going to do in the final round of the [King of Diamonds](https://aliceinborderland.fandom.com/wiki/King_of_Diamonds) game (with a similar set of cyclic rules)
in Alice in Borderland.

In this Collision Machine, the external factor is **Randomness*
