# Concept

**Monte Carlo Simulation concept**

Monte Carlo methods are a broad class of computational algorithms that rely on repeated random sampling to obtain numerical results.
We run a simulation using random variables thousands or millions of times and then calculate the average value as the expected value.
It works well when do not have a defintive way or formula to calculate a value but can trigger a pool of values ad select the best fitting values using a relation.
we can than avg it out to get the optimal value.

Let’s say you’re playing darts, and there’s a circle drawn inside a rectangular board. You want to estimate the area of the circle, but you don't know the formula or radius.
You have millions of darts with tips of unit area size. To estimate the circle's area using Monte Carlo:
Shoot darts randomly and uniformly over the entire rectangle.
Count the number of darts that land inside the circle.
Use the ratio of darts that landed inside the circle vs. the total darts thrown.

Then the estimated area of the circle is:
Area of circle = (number of darts inside the circle / total number of darts thrown)​	* area of the rectangle
 
The more darts you throw, the closer this estimate gets to the real value — thanks to the Law of Large Numbers.

if this was a unit circle then the area of the circle would be pie.


**GBM Formula**

<img width="810" height="320" alt="image" src="https://github.com/user-attachments/assets/78f3591b-64fb-4f9d-bb9c-f81ce3ffb7a8" />




