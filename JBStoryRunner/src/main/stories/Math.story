Meta:
@steps BasicSteps

GivenStories: Given.story

Scenario:  A scenario is a collection of executable steps of different type

Meta:
@author NudelHead

Given a variable x with value <val>

Examples:
|val|
|1  |
|2  |
|3  |


Scenario:  A scenario is a collection of executable steps of different type

Meta:
@executeMe

Given a variable x with value <val>

Examples:
|Meta:     |val|
|@notMe    |1  |
|@notMe    |2  |
|@executeMe|3  |


Scenario:  A scenario is a collection of executable steps of different type


When a variable with 2 is set and multiplied with 3 the result is 6


Scenario:  A scenario is a collection of executable steps of different type

Meta:
@executeMe

When a variable with <val> is set and multiplied with <mul> the result is <res>

Examples:
|Meta:     |val|mul|res|
|          |1  |1  |1  |
|          |2  |2  |4  |
|@executeMe|3  |3  |9  |


Scenario:  A scenario is a collection of executable steps of different type

Meta:
@skip
 
Given a variable x with value 0


Scenario:  A scenario is a collection of executable steps of different type

Given a global variable testi1 with value muuttuja1
Given a global variable testi2 with value muuttuja2
Given a global variable testi3 with value muuttuja3
Given a global variable testi4 with value muuttuja4 
Given replace a variable with its value in Some String with testi2 occurring testi2testi2 several testi3 times
