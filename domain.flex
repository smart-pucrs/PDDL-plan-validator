/**
 *	Domain Parser
 */
 
%%

%byaccj

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

req = ":"([:letter:]|[:digit:])+([:letter:]|[:digit:]|"-"|"_")*
obj = "?"([:letter:]|[:digit:])+([:letter:]|[:digit:]|"-"|"_")*
str = ([:letter:]|[:digit:])+([:letter:]|[:digit:]|"-"|"_")*


comment = ";"[^\n]*\n
%%

define 		{return Parser.DEFINE;}
domain 		{return Parser.DOMAIN;}
problem 	{return Parser.PROBLEM;}


":requirements" {return Parser.REQUIREMENTS;}
":types" 		{return Parser.TYPES;}
":predicates" 	{return Parser.PREDICATES;}
":domain" 		{return Parser.PDOMAIN;}

":init"			{return Parser.INIT;}
":objects"		{return Parser.OBJECTS;}
":goal"			{return Parser.GOAL;}

":action" 		{return Parser.ACTION;}
":parameters" 	{return Parser.PARAMETERS;}
":precondition" {return Parser.PRECONDITION;}
":effect" 		{return Parser.EFFECT;}
":constraints" 	{return Parser.CONSTRAINTS;}


"and" 			{return Parser.AND;}
"not" 			{return Parser.NOT;}

"forall" 		{return Parser.FORALL;}
"exists" 		{return Parser.EXISTS;}
//constraint statement type
"always"		 |
"sometime"		 |
"within"		 |
"at-most-once"	 |
"sometime-after" |
"sometime-before"|
"always-within"	 |
"hold-during"	 |
"hold-after"	 {yyparser.yylval = new ParserVal(yytext());
				  return Parser.CSTAT;}	


"("	|
")"	|
"=" |
"-" 			{return (int) yycharat(0);}


{req}  			{yyparser.yylval = new ParserVal(yytext());
				 return Parser.REQ;}
{obj}  			{yyparser.yylval = new ParserVal(yytext());
				 return Parser.OBJ;}
{str}  			{yyparser.yylval = new ParserVal(yytext());
				 return Parser.STRING;}
{comment}		{}
[ \t\r\n]+		{}