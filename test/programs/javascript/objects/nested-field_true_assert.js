function __VERIFIER_error() {}

var a = {};
var b = {};
var c = {};
a.b = b;
a.b.c = c;
if (a.b.c !== c) {
  __VERIFIER_error();
}