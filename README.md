ExpressionSolver
================

Android app that solves simple expressions. Created and submitted as an Android prject for ECEC 203 (Programming for Engineers). The app so far is just an EditText, Button, and TextView, though there is still more room for improvement (like solving for more complex expressions).

The app solves equations by splitting the equation by mathematical operators. So far, the app splits by +,-,*,/, and = (for expressions) by regular order of operations.
Ex: "2*3-5/2+4" returns "7.5"

The app solves expressions for the variable "x" the same way it solves equations, except it first splits the input expression into 2 equations by splitting at the equals sign.
Ex: "2x-6=5x+7*2*" returns (whatever 20/3 is in decimal format)
