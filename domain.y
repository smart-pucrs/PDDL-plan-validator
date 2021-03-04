
%{
	import java.util.ArrayList;
	import java.util.List;
  import java.io.*;
  //import java.io.IOException;
%}
      
%token DEFINE, DOMAIN, REQUIREMENTS, TYPES, PREDICATES, CONSTRAINTS
%token ACTION, PARAMETERS, PRECONDITION, EFFECT
%token PROBLEM, PDOMAIN, INIT, OBJECTS, GOAL
%token AND, NOT, FORALL, EXISTS
%token <sval> REQ, OBJ, STRING, CSTAT

%type <obj> pddl, string
//problem
%type <obj> mGoal, gObj, objs, tObjs, pPreds, pPredO, strings
//plan 
%type <obj> plan, plans, planO
//domain
%type <obj> domN, domain, reqs, req, types, preds, predO, predOT, params, paramsT, prec, mPrecs, precs, preObj, eff, mEffs, mEff, effObj

%%

pddl:	'(' DEFINE domN domain ')' 		{}
		| '(' DEFINE probN problem ')' 	{};
		| plans							{};							
		;
		
string: STRING		{$$ = $1;}
		| AND		{$$ = "and";}
		| NOT		{$$ = "not";}
		| FORALL	{$$ = "forall";}
		| EXISTS	{$$ = "exists";}
		| CSTAT		{$$ = $1;}
		| DOMAIN	{$$ = "domain";}
		| PROBLEM	{$$ = "problem";}
		;


/***************
*  PDDL PROBLEM
****************/
probN:	'(' PROBLEM string ')' 	{pddl.problem((String)$3);}
		;
		
problem:'(' PDOMAIN string ')' problem	{pddl.pDomain((String)$3);}
		| '(' INIT pPreds ')' problem	{pddl.iniState((ArrayList<String[]>)$3);}
		| '(' OBJECTS objs ')' problem	{pddl.addObjs((List<String>)$3);}
		| '(' OBJECTS tObjs ')' problem	{pddl.addObjs((List)((Object[])$3)[0], (List)((Object[])$3)[1]);}
		| '(' GOAL goal ')'				{}
		;
		
/* GOAL */
goal:	'(' string ')'			{String[] aux = new String[1]; aux[0] = (String)$2; pddl.addPosGoal(aux);}
		| '(' string gObj ')'	{pddl.addPosGoal(((String)$2 + " " + ((String)$3)).split(" "));}
		| '(' AND mGoals ')'	{}
		;
mGoals: mGoal						{pddl.addPosGoal((String[])$1);}
		| '(' NOT mGoal ')'			{pddl.addNegGoal((String[])$3);}
		| mGoal mGoals				{pddl.addPosGoal((String[])$1);}
		| '(' NOT mGoal ')' mGoals	{pddl.addNegGoal((String[])$3);}
		;
mGoal:	'(' string ')'			{$$ = new String[1]; ((String[])$$)[0] = (String)$2;}
		| '(' string gObj ')'	{$$ = ((String)$2 + " " + ((String)$3)).split(" ");}
		;
gObj: 	string			{$$ = $1;}
		| string gObj {$$ = $1 + " " +((String)$2);}
		;
	
/* OBJECTS */
objs: 	string			{$$ = new ArrayList<String>(); ((List)$$).add($1);}
		| string objs	{$$ = $2; ((List)$$).add($1);} 
		;
		
tObjs:	string '-' string   		{$$ = new Object[2]; ((Object[])$$)[0] = new ArrayList<String>(); ((Object[])$$)[1] = new ArrayList<String>(); ((List)((Object[])$$)[0]).add($1); ((List)((Object[])$$)[1]).add($3);}
		| string '-' string tObjs 	{$$ = $4; ((List)((Object[])$$)[0]).add($1); ((List)((Object[])$$)[1]).add($3);}

/* PREDICATES */
pPreds:	'(' string ')'					{$$ = new ArrayList<String[]>(); String[] aux = new String[1]; aux[0] = (String)$2; ((ArrayList)$$).add(aux);}
		| '(' string ')' pPreds			{String[] aux = new String[1]; aux[0] = (String)$2; $$ = $4; ((ArrayList)$$).add(aux);}
		| '(' string pPredO ')'			{$$ = new ArrayList<String[]>();  ((ArrayList)$$).add(($2 + " " + (String)$3).split(" "));}
		| '(' string pPredO ')' pPreds	{$$ = $5; ((ArrayList)$$).add(($2 + " " + (String)$3).split(" "));}
		;
pPredO:	string 		{$$ = $1;}
		| string pPredO	{$$ = $1 + " " + (String)$2;}
		
		
/***************
*  PDDL PLAN
****************/
plans:	plan plans	{}
		|			{}
		;
plan: 	'(' string ')' {String[] aux = new String[1]; aux[0] = (String)$2; pddl.addPAct(aux);}
		| '(' string planO')' {pddl.addPAct(($2 + " " + $3).split(" "));}
		;
planO:	string			{$$ = $1;}
		| string planO	{$$ = $1 + " " + (String)$2;}
		;
		
		
/***************
*  PDDL DOMAIN
****************/
domN:	'(' DOMAIN string ')'	{pddl = new PDDL((String)$3);}

domain: 	 '(' REQUIREMENTS reqs ')' domain 	{}
		| '(' TYPES types ')' domain 			{}
		| '(' TYPES sTypes ')' domain 			{}
		| '(' PREDICATES dPred ')' domain 		{}
		| '(' ACTION action ')' domain			{}
		| 										{}	
		;


/* REQUIREMENTS */
reqs: 	req			{pddl.addReq((String)$1);}
		| req reqs	{pddl.addReq((String)$1);}
		;
req:	REQ 			{$$ = $1;}
		| CONSTRAINTS 	{$$ = ":constraints";}
		;


/* TYPES */
types: 	 string				{pddl.addType((String)$1);}
		 | string types 		{pddl.addType((String)$1);}
		 ;
sTypes:   strings '-' string			{pddl.addType(((String)$1).split(" "), (String)$3);}
		 | strings '-' string sTypes 	{pddl.addType(((String)$1).split(" "), (String)$3);}
		 ;
strings: string				{$$ = $1;}
		 | string strings	{$$ = (String)$1 + " " + (String)$2;}
		 ;

		
/* PREDICATES */
dPred:	preds	{pddl.addPredicates((ArrayList<Pred>)$1);}
		;
preds:	'(' string ')'					{$$ = new ArrayList<Pred>(); ((ArrayList)$$).add(new Pred((String)$2));}
		| '(' string ')' preds			{$$ = $4; ((ArrayList)$$).add(new Pred((String)$2));}
		| '(' string predO ')'			{$$ = new ArrayList<Pred>(); ((ArrayList)$$).add(new Pred((String)$2, (ArrayList<String>)$3));}
		| '(' string predO ')' preds	{$$ = $5; ((ArrayList)$$).add(new Pred((String)$2, (ArrayList<String>)$3));}
		| '(' string predOT ')'			{$$ = new ArrayList<Pred>(); ((ArrayList)$$).add(new Pred((String)$2, (ArrayList<String>)((ArrayList[])$3)[0],(ArrayList<Integer>)((ArrayList[])$3)[1]));}
		| '(' string predOT ')' preds	{$$ = $5; ((ArrayList)$$).add(new Pred((String)$2, (ArrayList<String>)((ArrayList[])$3)[0],(ArrayList<Integer>)((ArrayList[])$3)[1]));}
		;
		
/* PREDICATE OBJECTS */
predO:	OBJ 		{$$ = new ArrayList<String>(); ((ArrayList)$$).add($1);}
		| OBJ predO	{$$ = $2; ((ArrayList<String>)$2).add($1);}

predOT:	 OBJ '-' string 		{$$ = new ArrayList[2]; ((ArrayList[])$$)[0] = new ArrayList<String>(); ((ArrayList[])$$)[1] = new ArrayList<Integer>(); ((ArrayList[])$$)[0].add($1);
								((ArrayList[])$$)[1].add(new Integer(pddl.getTypeId((String)$3)));}
		| OBJ '-' string predOT	{$$ = $4; ((ArrayList[])$$)[0].add($1); ((ArrayList[])$$)[1].add(new Integer(pddl.getTypeId((String)$3)));}
		;


/* ACTION */
action:	string PARAMETERS '(' params ')' prec EFFECT eff		{pddl.addAction(new Act((String)$1,(ArrayList<String>)$4, (ArrayList<String[]>)((Object[])$6)[0],
																			(ArrayList<String[]>)((Object[])$6)[1], (ArrayList<String[]>)((Object[])$8)[0], (ArrayList<String[]>)((Object[])$8)[1]));}
		| string PARAMETERS '(' paramsT ')' prec EFFECT eff		{pddl.addAction(new Act((String)$1, (ArrayList<String>)((Object[])$4)[0], (ArrayList<String>)((Object[])$4)[1], 
																			(ArrayList<String[]>)((Object[])$6)[0], (ArrayList<String[]>)((Object[])$6)[1], (ArrayList<String[]>)((Object[])$8)[0],
																			(ArrayList<String[]>)((Object[])$8)[1], pddl.types));}
		| string prec EFFECT eff								{pddl.addAction(new Act((String)$1, new ArrayList<String>(), (ArrayList<String[]>)((Object[])$2)[0],
																			(ArrayList<String[]>)((Object[])$2)[1], (ArrayList<String[]>)((Object[])$4)[0], (ArrayList<String[]>)((Object[])$4)[1]));}
		;
params:	OBJ 					{$$ = new ArrayList<String>(); ((ArrayList)$$).add($1);}
		| OBJ params			{$$ = $2; ((ArrayList)$$).add(0,$1);}
		;
paramsT: OBJ '-' string 			{$$ = new Object[2]; ((Object[])$$)[0] = new ArrayList<String>(); ((Object[])$$)[1] = new ArrayList<String>(); ((ArrayList)((Object[])$$)[0]).add($1); ((ArrayList)((Object[])$$)[1]).add($3);}
		| OBJ '-' string paramsT	{$$ = $4	; ((ArrayList)((Object[])$$)[0]).add(0,$1); ((ArrayList)((Object[])$$)[1]).add(0,$3);}
		;

/* ACTION PRECONDITIONS */
prec:	PRECONDITION precs					{$$ = new Object[2]; ((Object[])$$)[0] = new ArrayList<String[]>(); ((Object[])$$)[1] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])$$)[0]).add((String[])$2);}
		| PRECONDITION '(' NOT precs ')'	{$$ = new Object[2]; ((Object[])$$)[0] = new ArrayList<String[]>(); ((Object[])$$)[1] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])$$)[1]).add((String[])$4);}
		| PRECONDITION '(' AND mPrecs ')'	{$$ = $4;}
		|									{$$ = new Object[2]; ((Object[])$$)[0] = new ArrayList<String[]>(); ((Object[])$$)[1] = new ArrayList<String[]>();}
		;
mPrecs: precs						{$$ = new Object[2]; ((Object[])$$)[0] = new ArrayList<String[]>(); ((Object[])$$)[1] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])$$)[0]).add((String[])$1);}
		| '(' NOT precs ')'			{$$ = new Object[2]; ((Object[])$$)[0] = new ArrayList<String[]>(); ((Object[])$$)[1] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])$$)[1]).add((String[])$3);}
		| precs mPrecs				{$$ = $2; ((ArrayList<String[]>)((Object[])$2)[0]).add((String[])$1);}
		| '(' NOT precs ')'	mPrecs	{$$ = $5; ((ArrayList<String[]>)((Object[])$5)[1]).add((String[])$3);}
		;
precs:	'(' string ')'			{$$ = new String[1]; ((String[])$$)[0] = (String)$2;}
		| '(' string preObj ')'	{$$ = ($2 + " " + (String)$3).split(" ");}
		| '(' '=' preObj ')'	{$$ = ("= " + (String)$3).split(" ");}
		;
preObj: OBJ				{$$ = $1;}
		| OBJ preObj 	{$$ = $1 + " " + ((String)$2);}
		;

/* ACTION EFFECTS */
eff:	'(' string ')'						{String[] aux = new String[1]; aux[0] = (String)$2; $$ = new Object[2]; ((Object[])$$)[0] = new ArrayList<String[]>(); 
											((ArrayList<String[]>)((Object[])$$)[0]).add(aux); ((Object[])$$)[1] = new ArrayList<String[]>();}
		| '(' string effObj ')'				{String[] aux = ((String)$2 + " " + ((String)$3)).split(" "); $$ = new Object[2]; ((Object[])$$)[0] = new ArrayList<String[]>(); 
											((ArrayList<String[]>)((Object[])$$)[0]).add(aux); ((Object[])$$)[1] = new ArrayList<String[]>();}
		| '(' NOT '(' string ')' ')'		{String[] aux = new String[1]; aux[0] = (String)$4; $$ = new Object[2]; ((Object[])$$)[0] = new ArrayList<String[]>(); 
											((Object[])$$)[1] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])$$)[1]).add(aux);}
		| '(' NOT '(' string effObj ')' ')'	{String[] aux = ((String)$4 + " " + ((String)$5)).split(" "); $$ = new Object[2]; ((Object[])$$)[0] = new ArrayList<String[]>(); 
											((Object[])$$)[1] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])$$)[1]).add(aux);}
		| '(' AND mEffs ')'					{$$ = $3;}
		;
mEffs: 	mEff						{$$ = new Object[2]; ((Object[])$$)[0] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])$$)[0]).add((String[])$1);
									((Object[])$$)[1] = new ArrayList<String[]>();}
		| '(' NOT mEff ')'			{$$ = new Object[2]; ((Object[])$$)[0] = new ArrayList<String[]>(); ((Object[])$$)[1] = new ArrayList<String[]>();
									((ArrayList<String[]>)((Object[])$$)[1]).add((String[])$3);}
		| mEff mEffs				{$$ = $2; ((ArrayList<String[]>)((Object[])$2)[0]).add((String[])$1);}
		| '(' NOT mEff ')' mEffs	{$$ = $5; ((ArrayList<String[]>)((Object[])$5)[1]).add((String[])$3);}
		;
mEff:	'(' string ')'			{$$ = new String[1]; ((String[])$$)[0] = (String)$2;}
		| '(' string effObj ')'	{$$ = ((String)$2 + " " + ((String)$3)).split(" ");}
		;
effObj: OBJ				{$$ = $1;}
		| OBJ effObj 	{$$ = $1 + " " +((String)$2);}
		;
		
%%

	private Yylex lexer;
	private int yylex () {
		int yyl_return = -1;
		try {
		  yylval = new ParserVal(0);
		  yyl_return = lexer.yylex();
		}
		catch (IOException e) {
		  System.err.println("IO error :"+e);
		}
		return yyl_return;
	}
	public Parser(Reader r) {
		lexer = new Yylex(r, this);
	}
	public void yyerror (String error) {
		System.err.println ("Error: " + error + "\nin file " + file + " line " + String.valueOf(Yylex.lineCount));
	}
	
	
	private static PDDL pddl; 
	private static String file;
	public static PDDL parseDomain(String filename){
		file = filename;
		try {
			Yylex.reset();
			Parser yyparser = new Parser(new FileReader(filename));
			yyparser.yyparse();
		}
		catch (IOException e) {
		  System.err.println("IO error :"+e);
		}
		pddl.domainFile = filename;
		return pddl;
	}
	public static void parseProblem(PDDL domain, String filename){
		file = filename;
		pddl = domain; 
		try {
			Yylex.reset();
			Parser yyparser = new Parser(new FileReader(filename));
			yyparser.yyparse();
		}
		catch (IOException e) {
		  System.err.println("IO error :"+e);
		}
		pddl.problemFile = filename;
	}
	public static void parsePlan(PDDL domain, String filename){
		file = filename;
		pddl = domain; 
		try {
			Yylex.reset();
			Parser yyparser = new Parser(new FileReader(filename));
			yyparser.yyparse();
		}
		catch (IOException e) {
		  System.err.println("IO error :"+e);
		}
		pddl.fixPlanCase();
		pddl.planFile = filename;
	}
	

	public static void main(String args[]) throws IOException {
		pddl = parseDomain("test\\domain.pddl");
		pddl.printTest();
		parseProblem(pddl, "test\\problem.pddl");
		parsePlan(pddl, "test\\plan.pddl");
		if(!pddl.checkA()){
			//pddl.PrintTest2();
			System.out.println("__________________________");
			pddl.valOut("out");
			//pddl.planTest();
		}
	}
