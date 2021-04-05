(define  (domain test)
	(:requirements :strips :equality :typing)

	(:types
		type1 type2
	)

	(:predicates 
		(pred1 ?obj - type1)
	)

	(:action action1 
		:parameters (?obj1 - type1 ?obj2 - type2)
		:precondition (not (pred1 ?obj1))
		:effect (pred1 ?obj1)
	)
	
)