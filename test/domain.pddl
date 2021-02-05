(define (domain hospital)
	(:requirements :strips :equality :typing)

	(:types
		paciente
		leito
		tipoDeEncaminhamento
		genero
	)

	(:predicates 
		(in ?paciente - paciente ?leito - leito)
		(ocupado ?leito - leito)
		(alocado ?paciente - paciente)
		(tipoDeEncaminhamento-Eletivo ?paciente - paciente)
		(tipoDeEncaminhamento-Agudo ?paciente - paciente)
		(tipoDeEncaminhamento-_NONE ?paciente - paciente)
		(genero-Masculino ?paciente - paciente)
		(genero-Feminino ?paciente - paciente)
	)

	(:action aloc-Neurologia 
		:parameters (?paciente - paciente ?leito - leito ?genero-paciente - genero ?genero-leito - genero)
		:precondition (and (not (alocado ?paciente))
			(not (ocupado ?leito))
			(= ?genero-paciente ?genero-leito))
		:effect (and (in ?paciente ?leito)
			(ocupado ?leito)
			(alocado ?paciente))
	)

	(:action aloc-Eletivo 
		:parameters (?paciente - paciente ?leito - leito)
		:precondition (and (not (alocado ?paciente))
			(not (ocupado ?leito)))
		:effect (and (in ?paciente ?leito)
			(ocupado ?leito)
			(alocado ?paciente))
	)

	(:action aloc-Medicina-Interna 
		:parameters (?paciente - paciente ?leito - leito ?genero-paciente - genero ?genero-leito - genero)
		:precondition (and (not (alocado ?paciente))
			(not (ocupado ?leito))
			(= ?genero-paciente ?genero-leito)
			(not (tipoDeEncaminhamento-_NONE ?paciente)))
		:effect (and (in ?paciente ?leito)
			(ocupado ?leito)
			(alocado ?paciente))
	)

	(:action aloc-Oncologia 
		:parameters (?paciente - paciente ?leito - leito ?genero-paciente - genero ?genero-leito - genero)
		:precondition (and (not (alocado ?paciente))
			(not (ocupado ?leito))
			(= ?genero-paciente ?genero-leito))
		:effect (and (in ?paciente ?leito)
			(ocupado ?leito)
			(alocado ?paciente))
	)

	(:action aloc-Cardiologia 
		:parameters (?paciente - paciente ?leito - leito ?genero-paciente - genero ?genero-leito - genero)
		:precondition (and (not (alocado ?paciente))
			(not (ocupado ?leito))
			(= ?genero-paciente ?genero-leito))
		:effect (and (in ?paciente ?leito)
			(ocupado ?leito)
			(alocado ?paciente))
	)

	(:action aloc-Agudo 
		:parameters (?paciente - paciente ?leito - leito)
		:precondition (and (not (alocado ?paciente))
			(not (ocupado ?leito)))
		:effect (and (in ?paciente ?leito)
			(ocupado ?leito)
			(alocado ?paciente))
	)
)