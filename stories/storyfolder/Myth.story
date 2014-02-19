Meta:
@steps BasicSteps
@cheese

GivenStories: Given.story

Scenario:  Testing Examples table with author meta 

Meta:
@author NudelHead

Given a variable x with value <val>

Examples:
|val|
|1  |
|2  |
|3  |


Scenario:  Testing meta tags inside Examples table

Meta:
@executeMe

Given a variable x with value <val>

Examples:
|Meta:     |val|
|@notMe    |1  |
|@notMe    |2  |
|@executeMe|3  |


Scenario:  Testing a composite


When a variable with 2 is set and multiplied with 3 the result is 6


Scenario:  Testing a composite with Examples table and meta

Meta:
@executeMe

When a variable with <val> is set and multiplied with <mul> the result is <res>

Examples:
|Meta:     |val|mul|res|
|          |1  |1  |1  |
|          |2  |2  |4  |
|@executeMe|3  |3  |9  |


Scenario:  Testing skip meta

Meta:
@skip
 
Given a variable x with value 0


Scenario:  Testing variable settings in common class

Given a global variable testi1 with value muuttuja1
Given a global variable testi2 with value muuttuja2
Given a global variable testi3 with value muuttuja3
Given a global variable testi4 with value muuttuja4 
Given replace a variable with its value in Some String with testi2 occurring testi2testi2 several testi3 times
When global variable map is printed iteratively

