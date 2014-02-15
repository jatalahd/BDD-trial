Meta:
@steps WebSteps

Scenario:  Testing basic webSteps

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


Scenario:  Testing skip meta in webSteps

Meta:
@skip
 
Given a variable x with value 0


Scenario:  Testing variables and groovy 

When Groovy script commonSteps.addVariable("groovy","groovyVal"); is executed
When global variable map is printed iteratively
Then print the value of global variable testi2
When Groovy script exampleSteps.givenXValue(100); is executed
When Groovy script exampleSteps.givenXValue(50); is executed



