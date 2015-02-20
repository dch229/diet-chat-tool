/* Generated By:JJTree&JavaCC: Do not edit this line. ParserConstants.java */
package lib.bsh;

public interface ParserConstants {

  int EOF = 0;
  int NONPRINTABLE = 6;
  int SINGLE_LINE_COMMENT = 7;
  int HASH_BANG_COMMENT = 8;
  int MULTI_LINE_COMMENT = 9;
  int ABSTRACT = 10;
  int BOOLEAN = 11;
  int BREAK = 12;
  int CLASS = 13;
  int BYTE = 14;
  int CASE = 15;
  int CATCH = 16;
  int CHAR = 17;
  int CONST = 18;
  int CONTINUE = 19;
  int _DEFAULT = 20;
  int DO = 21;
  int DOUBLE = 22;
  int ELSE = 23;
  int ENUM = 24;
  int EXTENDS = 25;
  int FALSE = 26;
  int FINAL = 27;
  int FINALLY = 28;
  int FLOAT = 29;
  int FOR = 30;
  int GOTO = 31;
  int IF = 32;
  int IMPLEMENTS = 33;
  int IMPORT = 34;
  int INSTANCEOF = 35;
  int INT = 36;
  int INTERFACE = 37;
  int LONG = 38;
  int NATIVE = 39;
  int NEW = 40;
  int NULL = 41;
  int PACKAGE = 42;
  int PRIVATE = 43;
  int PROTECTED = 44;
  int PUBLIC = 45;
  int RETURN = 46;
  int SHORT = 47;
  int STATIC = 48;
  int STRICTFP = 49;
  int SWITCH = 50;
  int SYNCHRONIZED = 51;
  int TRANSIENT = 52;
  int THROW = 53;
  int THROWS = 54;
  int TRUE = 55;
  int TRY = 56;
  int VOID = 57;
  int VOLATILE = 58;
  int WHILE = 59;
  int INTEGER_LITERAL = 60;
  int DECIMAL_LITERAL = 61;
  int HEX_LITERAL = 62;
  int OCTAL_LITERAL = 63;
  int FLOATING_POINT_LITERAL = 64;
  int EXPONENT = 65;
  int CHARACTER_LITERAL = 66;
  int STRING_LITERAL = 67;
  int LONG_STRING_LITERAL = 68;
  int FORMAL_COMMENT = 69;
  int IDENTIFIER = 70;
  int LETTER = 71;
  int DIGIT = 72;
  int LPAREN = 73;
  int RPAREN = 74;
  int LBRACE = 75;
  int RBRACE = 76;
  int LBRACKET = 77;
  int RBRACKET = 78;
  int SEMICOLON = 79;
  int COMMA = 80;
  int DOT = 81;
  int ASSIGN = 82;
  int GT = 83;
  int GTX = 84;
  int LT = 85;
  int LTX = 86;
  int BANG = 87;
  int TILDE = 88;
  int HOOK = 89;
  int COLON = 90;
  int EQ = 91;
  int LE = 92;
  int LEX = 93;
  int GE = 94;
  int GEX = 95;
  int NE = 96;
  int BOOL_OR = 97;
  int BOOL_ORX = 98;
  int BOOL_AND = 99;
  int BOOL_ANDX = 100;
  int INCR = 101;
  int DECR = 102;
  int PLUS = 103;
  int MINUS = 104;
  int STAR = 105;
  int SLASH = 106;
  int BIT_AND = 107;
  int BIT_ANDX = 108;
  int BIT_OR = 109;
  int BIT_ORX = 110;
  int XOR = 111;
  int MOD = 112;
  int LSHIFT = 113;
  int LSHIFTX = 114;
  int RSIGNEDSHIFT = 115;
  int RSIGNEDSHIFTX = 116;
  int RUNSIGNEDSHIFT = 117;
  int RUNSIGNEDSHIFTX = 118;
  int PLUSASSIGN = 119;
  int MINUSASSIGN = 120;
  int STARASSIGN = 121;
  int SLASHASSIGN = 122;
  int ANDASSIGN = 123;
  int ANDASSIGNX = 124;
  int ORASSIGN = 125;
  int ORASSIGNX = 126;
  int XORASSIGN = 127;
  int MODASSIGN = 128;
  int LSHIFTASSIGN = 129;
  int LSHIFTASSIGNX = 130;
  int RSIGNEDSHIFTASSIGN = 131;
  int RSIGNEDSHIFTASSIGNX = 132;
  int RUNSIGNEDSHIFTASSIGN = 133;
  int RUNSIGNEDSHIFTASSIGNX = 134;

  int DEFAULT = 0;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\r\"",
    "\"\\f\"",
    "\"\\n\"",
    "<NONPRINTABLE>",
    "<SINGLE_LINE_COMMENT>",
    "<HASH_BANG_COMMENT>",
    "<MULTI_LINE_COMMENT>",
    "\"abstract\"",
    "\"boolean\"",
    "\"break\"",
    "\"class\"",
    "\"byte\"",
    "\"case\"",
    "\"catch\"",
    "\"char\"",
    "\"const\"",
    "\"continue\"",
    "\"default\"",
    "\"do\"",
    "\"double\"",
    "\"else\"",
    "\"enum\"",
    "\"extends\"",
    "\"false\"",
    "\"final\"",
    "\"finally\"",
    "\"float\"",
    "\"for\"",
    "\"goto\"",
    "\"if\"",
    "\"implements\"",
    "\"import\"",
    "\"instanceof\"",
    "\"int\"",
    "\"interface\"",
    "\"long\"",
    "\"native\"",
    "\"new\"",
    "\"null\"",
    "\"package\"",
    "\"private\"",
    "\"protected\"",
    "\"public\"",
    "\"return\"",
    "\"short\"",
    "\"static\"",
    "\"strictfp\"",
    "\"switch\"",
    "\"synchronized\"",
    "\"transient\"",
    "\"throw\"",
    "\"throws\"",
    "\"true\"",
    "\"try\"",
    "\"void\"",
    "\"volatile\"",
    "\"while\"",
    "<INTEGER_LITERAL>",
    "<DECIMAL_LITERAL>",
    "<HEX_LITERAL>",
    "<OCTAL_LITERAL>",
    "<FLOATING_POINT_LITERAL>",
    "<EXPONENT>",
    "<CHARACTER_LITERAL>",
    "<STRING_LITERAL>",
    "<LONG_STRING_LITERAL>",
    "<FORMAL_COMMENT>",
    "<IDENTIFIER>",
    "<LETTER>",
    "<DIGIT>",
    "\"(\"",
    "\")\"",
    "\"{\"",
    "\"}\"",
    "\"[\"",
    "\"]\"",
    "\";\"",
    "\",\"",
    "\".\"",
    "\"=\"",
    "\">\"",
    "\"@gt\"",
    "\"<\"",
    "\"@lt\"",
    "\"!\"",
    "\"~\"",
    "\"?\"",
    "\":\"",
    "\"==\"",
    "\"<=\"",
    "\"@lteq\"",
    "\">=\"",
    "\"@gteq\"",
    "\"!=\"",
    "\"||\"",
    "\"@or\"",
    "\"&&\"",
    "\"@and\"",
    "\"++\"",
    "\"--\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"&\"",
    "\"@bitwise_and\"",
    "\"|\"",
    "\"@bitwise_or\"",
    "\"^\"",
    "\"%\"",
    "\"<<\"",
    "\"@left_shift\"",
    "\">>\"",
    "\"@right_shift\"",
    "\">>>\"",
    "\"@right_unsigned_shift\"",
    "\"+=\"",
    "\"-=\"",
    "\"*=\"",
    "\"/=\"",
    "\"&=\"",
    "\"@and_assign\"",
    "\"|=\"",
    "\"@or_assign\"",
    "\"^=\"",
    "\"%=\"",
    "\"<<=\"",
    "\"@left_shift_assign\"",
    "\">>=\"",
    "\"@right_shift_assign\"",
    "\">>>=\"",
    "\"@right_unsigned_shift_assign\"",
  };

}
