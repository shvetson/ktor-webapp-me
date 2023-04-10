Пример
https://betterprogramming.pub/how-to-develop-a-web-application-with-ktor-and-thymeleaf-ccbb23776252



https://github.com/JetBrains/Exposed


The **select** function takes an extension lambda as an argument. The implicit receiver inside this lambda is of type 
SqlExpressionBuilder. You don't use this type explicitly, but it defines a bunch of useful operations on columns, 
which you use to build your queries. You can use comparisons (eq, less, greater), arithmetic operations (plus, times), 
check whether value belongs or doesn't belong to a provided list of values (inList, notInList), check whether the 
value is null or non-null, and many more.

select returns a list of Query values. As before, we convert them to articles. In our case, it should be one article, 
so we return it as a result.