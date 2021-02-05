//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "domain.y"
	import java.util.ArrayList;
	import java.util.List;
  import java.io.*;
  /*import java.io.IOException;*/
//#line 22 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short DEFINE=257;
public final static short DOMAIN=258;
public final static short REQUIREMENTS=259;
public final static short TYPES=260;
public final static short PREDICATES=261;
public final static short CONSTRAINTS=262;
public final static short ACTION=263;
public final static short PARAMETERS=264;
public final static short PRECONDITION=265;
public final static short EFFECT=266;
public final static short PROBLEM=267;
public final static short PDOMAIN=268;
public final static short INIT=269;
public final static short OBJECTS=270;
public final static short GOAL=271;
public final static short AND=272;
public final static short NOT=273;
public final static short FORALL=274;
public final static short EXISTS=275;
public final static short REQ=276;
public final static short OBJ=277;
public final static short STRING=278;
public final static short CSTAT=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    1,    1,    1,    1,    1,    1,    1,
    1,   29,   30,   30,   30,   30,   30,   31,   31,   31,
   32,   32,   32,   32,    2,    2,    3,    3,    4,    4,
    5,    5,    6,    6,    6,    6,    7,    7,    9,    9,
    8,    8,   10,   10,   11,   12,   12,   12,   12,   12,
   13,   13,   14,   14,   15,   15,   33,   16,   16,   16,
   16,   16,   16,   17,   17,   18,   18,   34,   34,   34,
   19,   19,   20,   20,   21,   21,   21,   21,   22,   22,
   22,   22,   23,   23,   23,   24,   24,   25,   25,   25,
   25,   25,   26,   26,   26,   26,   27,   27,   28,   28,
};
final static short yylen[] = {                            2,
    5,    5,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    4,    5,    5,    5,    5,    4,    3,    4,    4,
    1,    4,    2,    5,    3,    4,    1,    2,    1,    2,
    3,    4,    3,    4,    4,    5,    1,    2,    2,    0,
    3,    4,    1,    2,    4,    5,    5,    5,    5,    0,
    1,    2,    1,    1,    1,    2,    1,    3,    4,    4,
    5,    4,    5,    1,    2,    3,    4,    8,    8,    4,
    1,    2,    3,    4,    2,    5,    5,    0,    1,    4,
    2,    5,    3,    4,    4,    1,    2,    3,    4,    6,
    7,    4,    1,    4,    2,    5,    3,    4,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,   10,   11,    5,    6,    7,
    8,    4,    9,    0,    0,   39,    0,    0,    0,   41,
    0,    0,    0,    0,    0,    0,    0,    0,   44,   42,
    0,    0,    0,    0,    0,    0,    1,    0,    0,    0,
    0,    2,   45,   12,   54,   53,    0,    0,    0,    0,
    0,   57,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   52,   56,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   30,    0,
    0,    0,    0,   17,   46,   47,    0,    0,    0,    0,
   48,    0,    0,   75,    0,   49,   13,    0,    0,    0,
   14,    0,   15,   16,    0,    0,    0,   18,    0,    0,
    0,    0,   65,   59,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   70,   34,   38,    0,    0,   32,
    0,    0,   23,   20,   28,   19,    0,   61,   63,    0,
    0,   72,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   83,    0,    0,    0,    0,   36,    0,    0,   25,
    0,    0,   67,    0,    0,    0,    0,   77,   81,   76,
   87,   85,   84,    0,    0,    0,    0,    0,   88,    0,
    0,   26,    0,   74,    0,    0,    0,    0,    0,   92,
   95,    0,  100,   89,   24,   68,   69,    0,    0,    0,
   97,    0,    0,    0,   82,    0,   98,   90,    0,   96,
   91,
};
final static short yydgoto[] = {                          2,
  109,  106,  110,   79,   61,   58,  100,    3,    4,   22,
   18,   26,   47,   48,   50,   52,  113,   90,  142,  119,
   72,  146,  147,  151,  125,  175,  176,  180,   19,   28,
   63,  107,   53,   55,
};
final static short yysindex[] = {                       -29,
 -171,    0,  -21,    0,  -16,    0,    0,    0,    0,    0,
    0,    0,    0,  -41, -147,    0, -246,  -13,   -1,    0,
 -147,    4, -147, -147, -170,   13, -135,   18,    0,    0,
   23,   26, -232, -147,   30, -147,    0, -147,   43, -147,
   44,    0,    0,    0,    0,    0,   37, -232, -147,   47,
 -147,    0,   56, -215,   59,   64, -147,   65,   28,   68,
   72, -128,   73,  -13,    0,    0,  -13,  -38,  -13,   45,
   75, -229,  -13,   -1,  -31,   -1, -147, -147,    0,   -1,
   -1,   77,  -18,    0,    0,    0,   -9,   30,   80,   82,
    0, -159,  -60,    0,   84,    0,    0,   43, -147,   96,
    0, -147,    0,    0, -118,   77,   97,    0, -147,  101,
 -134, -147,    0,    0,   30,   30,   -3,  107,  111,  113,
  119, -113,  -32,  -95,    0,    0,    0,   43,  121,    0,
  127,   -8,    0,    0,    0,    0, -109,    0,    0, -108,
 -147,    0,  -92,  -92,   38,  129,  113,   51,  130, -113,
  133,    0,  135,  141,  142,  -26,    0, -147,  144,    0,
  145,  147,    0,  -90,  -77,  -76,  119,    0,    0,    0,
    0,    0,    0,  -70,  152,  141, -147,  -81,    0,  154,
   77,    0,  155,    0,   84,   84,  158,  161,  -25,    0,
    0,  -24,    0,    0,    0,    0,    0,  113, -147,  165,
    0,  169,  170,  179,    0,  141,    0,    0,  180,    0,
    0,
};
final static short yyrindex[] = {                       222,
    0,    0,  222,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  182,    0,    0,
   23,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  183,  184,    0,
    0,    0,    0,  -37,    0,    0,    0,    0,  187,    0,
    0,    0,    0,  182,    0,    0,  182,    0,  182,    0,
    0,    0,  182,    0,    0,    0,    0,  187,    0,    0,
    0,    5,    0,    0,    0,    0,   37,  189,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  194,   13,    0,
    0,  205,    0,    0,    0,  228,    0,    0,  234,    0,
   37,    0,    0,    0,  235,  240,  244,    0,    0,  -19,
  -15,    0,    0,    0,    0,    0,    0,  250,    0,    0,
   15,    0,    0,    0,    0,    0,  251,    0,    0,  244,
    0,    0,  -37,  -37,    0,    0,  256,    0,    0,  257,
    0,    0,    0,  -19,  -15,    0,    0,    0,    0,    0,
    0,    0,    0,   59,    0,    0,  -15,    0,    0,    0,
    0,    0,    0,    0,    0,  258,    0,  263,    0,    0,
  267,    0,    0,    0,    0,    0,    0,  -15,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   68,    0,    0,
    0,    0,    0,    0,    0,  273,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
   17,  188, -103,  275,  218,  -94,  223,    0,  318,  306,
    0,   -4,  280,    0,  282,  -68,  264,  196,  242,  171,
  -82, -140,  -69, -115, -114, -163,  148, -164,    0,    1,
    0, -101,    0,    0,
};
final static int YYTABLESIZE=336;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         20,
  122,   94,   88,  126,  133,  135,  169,  153,  152,   98,
    1,   23,  191,  193,  179,  201,  203,   14,   15,  114,
   24,    5,  108,   17,  202,    6,   25,  204,  161,   45,
   21,   14,  160,  157,  171,  112,   95,   21,   27,   31,
   32,  141,  210,   46,   30,    5,  138,  139,   70,   71,
   49,  149,   54,   37,   56,    6,   59,  205,   42,   85,
  165,  166,   86,   43,   91,   49,   44,   68,   96,   51,
  196,  197,   77,   75,   97,   78,  101,   64,   83,  195,
  103,  104,   57,   62,   92,    5,    6,   67,   33,   34,
   35,   99,   36,  102,   78,    7,   69,  187,  122,   73,
    8,    9,   10,   11,   74,   76,   12,   13,   80,  123,
    6,  122,   81,   84,   93,   99,  105,  117,  129,    7,
  115,  132,  116,  124,    8,    9,   10,   11,  137,    6,
   12,   13,   38,   39,   40,   41,  128,  134,    7,    6,
  156,  136,  111,   82,    9,   10,   11,  143,    7,   12,
   13,  144,  145,    8,  131,   10,   11,  164,  148,   12,
   13,  123,    6,  150,  123,   77,  158,  162,  140,  168,
  170,    7,   71,  172,  132,  173,  154,  155,   10,   11,
  174,  177,   12,   13,  181,  182,  183,    6,  185,  186,
  189,  112,  190,  192,  194,  178,    7,    6,  198,  141,
  199,    8,  188,   10,   11,  206,    7,   12,   13,  207,
  208,  120,  121,   10,   11,  189,    6,   12,   13,  209,
  211,   40,   50,   51,   55,    7,    6,   29,   78,   58,
    8,    9,   10,   11,   33,    7,   12,   13,   87,    6,
    8,    9,   10,   11,  150,   31,   12,   13,    7,    6,
  178,  178,  178,    8,    9,   10,   11,    5,    7,   12,
   13,    6,    5,    8,    9,   10,   11,  111,   21,   12,
   13,    5,    6,  140,   27,   60,    5,    5,    5,    5,
   62,    6,    5,    5,   71,    6,    6,    6,    6,    6,
   35,   66,    6,    6,    7,    6,   79,   86,   93,    8,
    9,   10,   11,   99,    7,   12,   13,   22,    6,    8,
  167,   10,   11,   94,   60,   12,   13,    7,  159,  130,
   16,  127,    8,    9,   10,   11,   29,   65,   12,   13,
   66,   89,  163,  118,  184,  200,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   61,   71,   41,   98,  106,  109,  147,  123,   41,   41,
   40,  258,  176,  178,   41,   41,   41,    1,   40,   88,
  267,   41,   41,   40,  189,   41,   40,  192,  132,  262,
   14,   15,   41,  128,  150,   45,  266,   21,   40,   23,
   24,   45,  206,  276,   41,   41,  115,  116,  264,  265,
   34,  121,   36,   41,   38,   41,   40,  198,   41,   64,
  143,  144,   67,   41,   69,   49,   41,   51,   73,   40,
  185,  186,   45,   57,   74,   59,   76,   41,   62,  181,
   80,   81,   40,   40,   40,  257,  258,   41,  259,  260,
  261,   75,  263,   77,   78,  267,   41,  167,   61,   41,
  272,  273,  274,  275,   41,   41,  278,  279,   41,   93,
  258,   61,   41,   41,   40,   99,   40,  277,  102,  267,
   41,  105,   41,   40,  272,  273,  274,  275,  112,  258,
  278,  279,  268,  269,  270,  271,   41,   41,  267,  258,
  124,   41,  277,  272,  273,  274,  275,   41,  267,  278,
  279,   41,   40,  272,  273,  274,  275,  141,   40,  278,
  279,  145,  258,  277,  148,   45,   40,  277,  277,   41,
   41,  267,  265,   41,  158,   41,  272,  273,  274,  275,
   40,   40,  278,  279,   41,   41,  277,  258,  266,  266,
  174,   45,   41,  177,   41,  277,  267,  258,   41,   45,
   40,  272,  273,  274,  275,   41,  267,  278,  279,   41,
   41,  272,  273,  274,  275,  199,  258,  278,  279,   41,
   41,    0,   41,   41,   41,  267,  258,   41,  266,   41,
  272,  273,  274,  275,   41,  267,  278,  279,  277,  258,
  272,  273,  274,  275,  277,   41,  278,  279,  267,  258,
  277,  277,  277,  272,  273,  274,  275,  277,  267,  278,
  279,  277,  258,  272,  273,  274,  275,  277,   41,  278,
  279,  267,  258,  277,   41,   41,  272,  273,  274,  275,
   41,  267,  278,  279,   41,  258,  272,  273,  274,  275,
   41,   41,  278,  279,  267,  258,   41,   41,   41,  272,
  273,  274,  275,   41,  267,  278,  279,   41,  258,  272,
  273,  274,  275,   41,   40,  278,  279,  267,  131,  102,
    3,   99,  272,  273,  274,  275,   21,   48,  278,  279,
   49,   68,  137,   92,  164,  188,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'",null,null,null,
"'-'",null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'='",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"DEFINE","DOMAIN","REQUIREMENTS","TYPES",
"PREDICATES","CONSTRAINTS","ACTION","PARAMETERS","PRECONDITION","EFFECT",
"PROBLEM","PDOMAIN","INIT","OBJECTS","GOAL","AND","NOT","FORALL","EXISTS","REQ",
"OBJ","STRING","CSTAT",
};
final static String yyrule[] = {
"$accept : pddl",
"pddl : '(' DEFINE domN domain ')'",
"pddl : '(' DEFINE probN problem ')'",
"pddl : plans",
"string : STRING",
"string : AND",
"string : NOT",
"string : FORALL",
"string : EXISTS",
"string : CSTAT",
"string : DOMAIN",
"string : PROBLEM",
"probN : '(' PROBLEM string ')'",
"problem : '(' PDOMAIN string ')' problem",
"problem : '(' INIT pPreds ')' problem",
"problem : '(' OBJECTS objs ')' problem",
"problem : '(' OBJECTS tObjs ')' problem",
"problem : '(' GOAL goal ')'",
"goal : '(' string ')'",
"goal : '(' string gObj ')'",
"goal : '(' AND mGoals ')'",
"mGoals : mGoal",
"mGoals : '(' NOT mGoal ')'",
"mGoals : mGoal mGoals",
"mGoals : '(' NOT mGoal ')' mGoals",
"mGoal : '(' string ')'",
"mGoal : '(' string gObj ')'",
"gObj : string",
"gObj : string gObj",
"objs : string",
"objs : string objs",
"tObjs : string '-' string",
"tObjs : string '-' string tObjs",
"pPreds : '(' string ')'",
"pPreds : '(' string ')' pPreds",
"pPreds : '(' string pPredO ')'",
"pPreds : '(' string pPredO ')' pPreds",
"pPredO : string",
"pPredO : string pPredO",
"plans : plan plans",
"plans :",
"plan : '(' string ')'",
"plan : '(' string planO ')'",
"planO : string",
"planO : string planO",
"domN : '(' DOMAIN string ')'",
"domain : '(' REQUIREMENTS reqs ')' domain",
"domain : '(' TYPES types ')' domain",
"domain : '(' PREDICATES dPred ')' domain",
"domain : '(' ACTION action ')' domain",
"domain :",
"reqs : req",
"reqs : req reqs",
"req : REQ",
"req : CONSTRAINTS",
"types : string",
"types : string types",
"dPred : preds",
"preds : '(' string ')'",
"preds : '(' string ')' preds",
"preds : '(' string predO ')'",
"preds : '(' string predO ')' preds",
"preds : '(' string predOT ')'",
"preds : '(' string predOT ')' preds",
"predO : OBJ",
"predO : OBJ predO",
"predOT : OBJ '-' string",
"predOT : OBJ '-' string predOT",
"action : string PARAMETERS '(' params ')' prec EFFECT eff",
"action : string PARAMETERS '(' paramsT ')' prec EFFECT eff",
"action : string prec EFFECT eff",
"params : OBJ",
"params : OBJ params",
"paramsT : OBJ '-' string",
"paramsT : OBJ '-' string paramsT",
"prec : PRECONDITION precs",
"prec : PRECONDITION '(' NOT precs ')'",
"prec : PRECONDITION '(' AND mPrecs ')'",
"prec :",
"mPrecs : precs",
"mPrecs : '(' NOT precs ')'",
"mPrecs : precs mPrecs",
"mPrecs : '(' NOT precs ')' mPrecs",
"precs : '(' string ')'",
"precs : '(' string preObj ')'",
"precs : '(' '=' preObj ')'",
"preObj : OBJ",
"preObj : OBJ preObj",
"eff : '(' string ')'",
"eff : '(' string effObj ')'",
"eff : '(' NOT '(' string ')' ')'",
"eff : '(' NOT '(' string effObj ')' ')'",
"eff : '(' AND mEffs ')'",
"mEffs : mEff",
"mEffs : '(' NOT mEff ')'",
"mEffs : mEff mEffs",
"mEffs : '(' NOT mEff ')' mEffs",
"mEff : '(' string ')'",
"mEff : '(' string effObj ')'",
"effObj : OBJ",
"effObj : OBJ effObj",
};

//#line 213 "domain.y"

	private Yylex lexer;
	private static PDDL pddl; 

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

	static boolean interactive;

	public static PDDL parseDomain(String filename){
		try {
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
		pddl = domain; 
		try {
			Parser yyparser = new Parser(new FileReader(filename));
			yyparser.yyparse();
		}
		catch (IOException e) {
		  System.err.println("IO error :"+e);
		}
		pddl.problemFile = filename;
	}

	public static void parsePlan(PDDL domain, String filename){
		pddl = domain; 
		try {
			Parser yyparser = new Parser(new FileReader(filename));
			yyparser.yyparse();
		}
		catch (IOException e) {
		  System.err.println("IO error :"+e);
		}
		pddl.fixPlanCase();
		pddl.planFile = filename;
	}



	public void yyerror (String error) {
		System.err.println ("Error: " + error);
	}

	public static void main(String args[]) throws IOException {
		pddl = parseDomain("test\\domain.pddl");
		pddl.printTest();
		parseProblem(pddl, "test\\problem.pddl");
		parsePlan(pddl, "test\\plan.pddl");
		//pddl.PrintTest2();
		System.out.println("__________________________");
		//pddl.planTest();
		pddl.valOut("out");
		/*Parser yyparser;
		// interactive mode
		System.out.println("[Quit with CTRL-D]");
		System.out.print("Expression: ");
		interactive = true;
		  yyparser = new Parser(new FileReader("td.pddl"));
		//yyparser = new Parser(new InputStreamReader(System.in));
		pddl = new PDDL("TEST");
		yyparser.yyparse();
		System.out.println("DONE");
		  yyparser = new Parser(new FileReader("tp"));
		yyparser.yyparse();
		System.out.println("\n\n");
		pddl.test();
		if (interactive) {
		  System.out.println();
		}*/
	}
//#line 525 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 24 "domain.y"
{}
break;
case 2:
//#line 25 "domain.y"
{}
break;
case 3:
//#line 26 "domain.y"
{}
break;
case 4:
//#line 29 "domain.y"
{yyval.obj = val_peek(0).sval;}
break;
case 5:
//#line 30 "domain.y"
{yyval.obj = "and";}
break;
case 6:
//#line 31 "domain.y"
{yyval.obj = "not";}
break;
case 7:
//#line 32 "domain.y"
{yyval.obj = "forall";}
break;
case 8:
//#line 33 "domain.y"
{yyval.obj = "exists";}
break;
case 9:
//#line 34 "domain.y"
{yyval.obj = val_peek(0).sval;}
break;
case 10:
//#line 35 "domain.y"
{yyval.obj = "domain";}
break;
case 11:
//#line 36 "domain.y"
{yyval.obj = "problem";}
break;
case 12:
//#line 43 "domain.y"
{pddl.problem((String)val_peek(1).obj);}
break;
case 13:
//#line 46 "domain.y"
{pddl.pDomain((String)val_peek(2).obj);}
break;
case 14:
//#line 47 "domain.y"
{pddl.iniState((ArrayList<String[]>)val_peek(2).obj);}
break;
case 15:
//#line 48 "domain.y"
{pddl.addObjs((List<String>)val_peek(2).obj);}
break;
case 16:
//#line 49 "domain.y"
{pddl.addObjs((List)((Object[])val_peek(2).obj)[0], (List)((Object[])val_peek(2).obj)[1]);}
break;
case 17:
//#line 50 "domain.y"
{}
break;
case 18:
//#line 54 "domain.y"
{String[] aux = new String[1]; aux[0] = (String)val_peek(1).obj; pddl.addPosGoal(aux);}
break;
case 19:
//#line 55 "domain.y"
{pddl.addPosGoal(((String)val_peek(2).obj + " " + ((String)val_peek(1).obj)).split(" "));}
break;
case 20:
//#line 56 "domain.y"
{}
break;
case 21:
//#line 58 "domain.y"
{pddl.addPosGoal((String[])val_peek(0).obj);}
break;
case 22:
//#line 59 "domain.y"
{pddl.addNegGoal((String[])val_peek(1).obj);}
break;
case 23:
//#line 60 "domain.y"
{pddl.addPosGoal((String[])val_peek(1).obj);}
break;
case 24:
//#line 61 "domain.y"
{pddl.addNegGoal((String[])val_peek(2).obj);}
break;
case 25:
//#line 63 "domain.y"
{yyval.obj = new String[1]; ((String[])yyval.obj)[0] = (String)val_peek(1).obj;}
break;
case 26:
//#line 64 "domain.y"
{yyval.obj = ((String)val_peek(2).obj + " " + ((String)val_peek(1).obj)).split(" ");}
break;
case 27:
//#line 66 "domain.y"
{yyval.obj = val_peek(0).obj;}
break;
case 28:
//#line 67 "domain.y"
{yyval.obj = val_peek(1).obj + " " +((String)val_peek(0).obj);}
break;
case 29:
//#line 71 "domain.y"
{yyval.obj = new ArrayList<String>(); ((List)yyval.obj).add(val_peek(0).obj);}
break;
case 30:
//#line 72 "domain.y"
{yyval.obj = val_peek(0).obj; ((List)yyval.obj).add(val_peek(1).obj);}
break;
case 31:
//#line 75 "domain.y"
{yyval.obj = new Object[2]; ((Object[])yyval.obj)[0] = new ArrayList<String>(); ((Object[])yyval.obj)[1] = new ArrayList<String>(); ((List)((Object[])yyval.obj)[0]).add(val_peek(2).obj); ((List)((Object[])yyval.obj)[1]).add(val_peek(0).obj);}
break;
case 32:
//#line 76 "domain.y"
{yyval.obj = val_peek(0).obj; ((List)((Object[])yyval.obj)[0]).add(val_peek(3).obj); ((List)((Object[])yyval.obj)[1]).add(val_peek(1).obj);}
break;
case 33:
//#line 79 "domain.y"
{yyval.obj = new ArrayList<String[]>(); String[] aux = new String[1]; aux[0] = (String)val_peek(1).obj; ((ArrayList)yyval.obj).add(aux);}
break;
case 34:
//#line 80 "domain.y"
{String[] aux = new String[1]; aux[0] = (String)val_peek(2).obj; yyval.obj = val_peek(0).obj; ((ArrayList)yyval.obj).add(aux);}
break;
case 35:
//#line 81 "domain.y"
{yyval.obj = new ArrayList<String[]>();  ((ArrayList)yyval.obj).add((val_peek(2).obj + " " + (String)val_peek(1).obj).split(" "));}
break;
case 36:
//#line 82 "domain.y"
{yyval.obj = val_peek(0).obj; ((ArrayList)yyval.obj).add((val_peek(3).obj + " " + (String)val_peek(2).obj).split(" "));}
break;
case 37:
//#line 84 "domain.y"
{yyval.obj = val_peek(0).obj;}
break;
case 38:
//#line 85 "domain.y"
{yyval.obj = val_peek(1).obj + " " + (String)val_peek(0).obj;}
break;
case 39:
//#line 91 "domain.y"
{}
break;
case 40:
//#line 92 "domain.y"
{}
break;
case 41:
//#line 94 "domain.y"
{String[] aux = new String[1]; aux[0] = (String)val_peek(1).obj; pddl.addPAct(aux);}
break;
case 42:
//#line 95 "domain.y"
{pddl.addPAct((val_peek(2).obj + " " + val_peek(1).obj).split(" "));}
break;
case 43:
//#line 97 "domain.y"
{yyval.obj = val_peek(0).obj;}
break;
case 44:
//#line 98 "domain.y"
{yyval.obj = val_peek(1).obj + " " + (String)val_peek(0).obj;}
break;
case 45:
//#line 105 "domain.y"
{pddl = new PDDL((String)val_peek(1).obj);}
break;
case 46:
//#line 107 "domain.y"
{}
break;
case 47:
//#line 108 "domain.y"
{}
break;
case 48:
//#line 109 "domain.y"
{}
break;
case 49:
//#line 110 "domain.y"
{}
break;
case 50:
//#line 111 "domain.y"
{}
break;
case 51:
//#line 116 "domain.y"
{pddl.addReq((String)val_peek(0).obj);}
break;
case 52:
//#line 117 "domain.y"
{pddl.addReq((String)val_peek(1).obj);}
break;
case 53:
//#line 119 "domain.y"
{yyval.obj = val_peek(0).sval;}
break;
case 54:
//#line 120 "domain.y"
{yyval.obj = ":constraints";}
break;
case 55:
//#line 126 "domain.y"
{pddl.addType((String)val_peek(0).obj);}
break;
case 56:
//#line 127 "domain.y"
{pddl.addType((String)val_peek(1).obj);}
break;
case 57:
//#line 132 "domain.y"
{pddl.addPredicates((ArrayList<Pred>)val_peek(0).obj);}
break;
case 58:
//#line 134 "domain.y"
{yyval.obj = new ArrayList<Pred>(); ((ArrayList)yyval.obj).add(new Pred((String)val_peek(1).obj));}
break;
case 59:
//#line 135 "domain.y"
{yyval.obj = val_peek(0).obj; ((ArrayList)yyval.obj).add(new Pred((String)val_peek(2).obj));}
break;
case 60:
//#line 136 "domain.y"
{yyval.obj = new ArrayList<Pred>(); ((ArrayList)yyval.obj).add(new Pred((String)val_peek(2).obj, (ArrayList<String>)val_peek(1).obj));}
break;
case 61:
//#line 137 "domain.y"
{yyval.obj = val_peek(0).obj; ((ArrayList)yyval.obj).add(new Pred((String)val_peek(3).obj, (ArrayList<String>)val_peek(2).obj));}
break;
case 62:
//#line 138 "domain.y"
{yyval.obj = new ArrayList<Pred>(); ((ArrayList)yyval.obj).add(new Pred((String)val_peek(2).obj, (ArrayList<String>)((ArrayList[])val_peek(1).obj)[0],(ArrayList<Integer>)((ArrayList[])val_peek(1).obj)[1]));}
break;
case 63:
//#line 139 "domain.y"
{yyval.obj = val_peek(0).obj; ((ArrayList)yyval.obj).add(new Pred((String)val_peek(3).obj, (ArrayList<String>)((ArrayList[])val_peek(2).obj)[0],(ArrayList<Integer>)((ArrayList[])val_peek(2).obj)[1]));}
break;
case 64:
//#line 143 "domain.y"
{yyval.obj = new ArrayList<String>(); ((ArrayList)yyval.obj).add(val_peek(0).sval);}
break;
case 65:
//#line 144 "domain.y"
{yyval.obj = val_peek(0).obj; ((ArrayList<String>)val_peek(0).obj).add(val_peek(1).sval);}
break;
case 66:
//#line 146 "domain.y"
{yyval.obj = new ArrayList[2]; ((ArrayList[])yyval.obj)[0] = new ArrayList<String>(); ((ArrayList[])yyval.obj)[1] = new ArrayList<Integer>(); ((ArrayList[])yyval.obj)[0].add(val_peek(2).sval);
								((ArrayList[])yyval.obj)[1].add(new Integer(pddl.getTypeId((String)val_peek(0).obj)));}
break;
case 67:
//#line 148 "domain.y"
{yyval.obj = val_peek(0).obj; ((ArrayList[])yyval.obj)[0].add(val_peek(3).sval); ((ArrayList[])yyval.obj)[1].add(new Integer(pddl.getTypeId((String)val_peek(1).obj)));}
break;
case 68:
//#line 153 "domain.y"
{pddl.addAction(new Act((String)val_peek(7).obj,(ArrayList<String>)val_peek(4).obj, (ArrayList<String[]>)((Object[])val_peek(2).obj)[0],
																			(ArrayList<String[]>)((Object[])val_peek(2).obj)[1], (ArrayList<String[]>)((Object[])val_peek(0).obj)[0], (ArrayList<String[]>)((Object[])val_peek(0).obj)[1]));}
break;
case 69:
//#line 155 "domain.y"
{pddl.addAction(new Act((String)val_peek(7).obj, (ArrayList<String>)((Object[])val_peek(4).obj)[0], (ArrayList<String>)((Object[])val_peek(4).obj)[1], 
																			(ArrayList<String[]>)((Object[])val_peek(2).obj)[0], (ArrayList<String[]>)((Object[])val_peek(2).obj)[1], (ArrayList<String[]>)((Object[])val_peek(0).obj)[0],
																			(ArrayList<String[]>)((Object[])val_peek(0).obj)[1], pddl.types));}
break;
case 70:
//#line 158 "domain.y"
{pddl.addAction(new Act((String)val_peek(3).obj, new ArrayList<String>(), (ArrayList<String[]>)((Object[])val_peek(2).obj)[0],
																			(ArrayList<String[]>)((Object[])val_peek(2).obj)[1], (ArrayList<String[]>)((Object[])val_peek(0).obj)[0], (ArrayList<String[]>)((Object[])val_peek(0).obj)[1]));}
break;
case 71:
//#line 161 "domain.y"
{yyval.obj = new ArrayList<String>(); ((ArrayList)yyval.obj).add(val_peek(0).sval);}
break;
case 72:
//#line 162 "domain.y"
{yyval.obj = val_peek(0).obj; ((ArrayList)yyval.obj).add(0,val_peek(1).sval);}
break;
case 73:
//#line 164 "domain.y"
{yyval.obj = new Object[2]; ((Object[])yyval.obj)[0] = new ArrayList<String>(); ((Object[])yyval.obj)[1] = new ArrayList<String>(); ((ArrayList)((Object[])yyval.obj)[0]).add(val_peek(2).sval); ((ArrayList)((Object[])yyval.obj)[1]).add(val_peek(0).obj);}
break;
case 74:
//#line 165 "domain.y"
{yyval.obj = val_peek(0).obj	; ((ArrayList)((Object[])yyval.obj)[0]).add(0,val_peek(3).sval); ((ArrayList)((Object[])yyval.obj)[1]).add(0,val_peek(1).obj);}
break;
case 75:
//#line 169 "domain.y"
{yyval.obj = new Object[2]; ((Object[])yyval.obj)[0] = new ArrayList<String[]>(); ((Object[])yyval.obj)[1] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])yyval.obj)[0]).add((String[])val_peek(0).obj);}
break;
case 76:
//#line 170 "domain.y"
{yyval.obj = new Object[2]; ((Object[])yyval.obj)[0] = new ArrayList<String[]>(); ((Object[])yyval.obj)[1] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])yyval.obj)[1]).add((String[])val_peek(1).obj);}
break;
case 77:
//#line 171 "domain.y"
{yyval.obj = val_peek(1).obj;}
break;
case 78:
//#line 172 "domain.y"
{yyval.obj = new Object[2]; ((Object[])yyval.obj)[0] = new ArrayList<String[]>(); ((Object[])yyval.obj)[1] = new ArrayList<String[]>();}
break;
case 79:
//#line 174 "domain.y"
{yyval.obj = new Object[2]; ((Object[])yyval.obj)[0] = new ArrayList<String[]>(); ((Object[])yyval.obj)[1] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])yyval.obj)[0]).add((String[])val_peek(0).obj);}
break;
case 80:
//#line 175 "domain.y"
{yyval.obj = new Object[2]; ((Object[])yyval.obj)[0] = new ArrayList<String[]>(); ((Object[])yyval.obj)[1] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])yyval.obj)[1]).add((String[])val_peek(1).obj);}
break;
case 81:
//#line 176 "domain.y"
{yyval.obj = val_peek(0).obj; ((ArrayList<String[]>)((Object[])val_peek(0).obj)[0]).add((String[])val_peek(1).obj);}
break;
case 82:
//#line 177 "domain.y"
{yyval.obj = val_peek(0).obj; ((ArrayList<String[]>)((Object[])val_peek(0).obj)[1]).add((String[])val_peek(2).obj);}
break;
case 83:
//#line 179 "domain.y"
{yyval.obj = new String[1]; ((String[])yyval.obj)[0] = (String)val_peek(1).obj;}
break;
case 84:
//#line 180 "domain.y"
{yyval.obj = (val_peek(2).obj + " " + (String)val_peek(1).obj).split(" ");}
break;
case 85:
//#line 181 "domain.y"
{yyval.obj = ("= " + (String)val_peek(1).obj).split(" ");}
break;
case 86:
//#line 183 "domain.y"
{yyval.obj = val_peek(0).sval;}
break;
case 87:
//#line 184 "domain.y"
{yyval.obj = val_peek(1).sval + " " + ((String)val_peek(0).obj);}
break;
case 88:
//#line 188 "domain.y"
{String[] aux = new String[1]; aux[0] = (String)val_peek(1).obj; yyval.obj = new Object[2]; ((Object[])yyval.obj)[0] = new ArrayList<String[]>(); 
											((ArrayList<String[]>)((Object[])yyval.obj)[0]).add(aux); ((Object[])yyval.obj)[1] = new ArrayList<String[]>();}
break;
case 89:
//#line 190 "domain.y"
{String[] aux = ((String)val_peek(2).obj + " " + ((String)val_peek(1).obj)).split(" "); yyval.obj = new Object[2]; ((Object[])yyval.obj)[0] = new ArrayList<String[]>(); 
											((ArrayList<String[]>)((Object[])yyval.obj)[0]).add(aux); ((Object[])yyval.obj)[1] = new ArrayList<String[]>();}
break;
case 90:
//#line 192 "domain.y"
{String[] aux = new String[1]; aux[0] = (String)val_peek(2).obj; yyval.obj = new Object[2]; ((Object[])yyval.obj)[0] = new ArrayList<String[]>(); 
											((Object[])yyval.obj)[1] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])yyval.obj)[1]).add(aux);}
break;
case 91:
//#line 194 "domain.y"
{String[] aux = ((String)val_peek(3).obj + " " + ((String)val_peek(2).obj)).split(" "); yyval.obj = new Object[2]; ((Object[])yyval.obj)[0] = new ArrayList<String[]>(); 
											((Object[])yyval.obj)[1] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])yyval.obj)[1]).add(aux);}
break;
case 92:
//#line 196 "domain.y"
{yyval.obj = val_peek(1).obj;}
break;
case 93:
//#line 198 "domain.y"
{yyval.obj = new Object[2]; ((Object[])yyval.obj)[0] = new ArrayList<String[]>(); ((ArrayList<String[]>)((Object[])yyval.obj)[0]).add((String[])val_peek(0).obj);
									((Object[])yyval.obj)[1] = new ArrayList<String[]>();}
break;
case 94:
//#line 200 "domain.y"
{yyval.obj = new Object[2]; ((Object[])yyval.obj)[0] = new ArrayList<String[]>(); ((Object[])yyval.obj)[1] = new ArrayList<String[]>();
									((ArrayList<String[]>)((Object[])yyval.obj)[1]).add((String[])val_peek(1).obj);}
break;
case 95:
//#line 202 "domain.y"
{yyval.obj = val_peek(0).obj; ((ArrayList<String[]>)((Object[])val_peek(0).obj)[0]).add((String[])val_peek(1).obj);}
break;
case 96:
//#line 203 "domain.y"
{yyval.obj = val_peek(0).obj; ((ArrayList<String[]>)((Object[])val_peek(0).obj)[1]).add((String[])val_peek(2).obj);}
break;
case 97:
//#line 205 "domain.y"
{yyval.obj = new String[1]; ((String[])yyval.obj)[0] = (String)val_peek(1).obj;}
break;
case 98:
//#line 206 "domain.y"
{yyval.obj = ((String)val_peek(2).obj + " " + ((String)val_peek(1).obj)).split(" ");}
break;
case 99:
//#line 208 "domain.y"
{yyval.obj = val_peek(0).sval;}
break;
case 100:
//#line 209 "domain.y"
{yyval.obj = val_peek(1).sval + " " +((String)val_peek(0).obj);}
break;
//#line 1085 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
