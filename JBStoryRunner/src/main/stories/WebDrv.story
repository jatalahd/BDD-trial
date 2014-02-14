Meta:
@steps WebSteps

Scenario:  A scenario is a collection of executable steps of different type

Meta:
@author NoodleHead

Given find element timeout is 15
Given wait after action is 2.0
When local firefox browser is opened
When user navigates to https://www.google.com
When web element partialLinkText:Kirjaudu is clicked
When web element link-signup is clicked
When web element xpath://div[@id='name-form-element']/descendant::span[@id='firstname-placeholder'] is clicked
When text Jamppa is written to field FirstName
When all browsers are closed

Scenario:  A scenario is a collection of executable steps of different type

Meta:
@skip
 
Given a variable x with value 0


Scenario:  A scenario is a collection of executable steps of different type

When global variable map is printed iteratively
Then print the value of global variable testi2
When Groovy script exampleSteps.givenXValue(100); is executed


