# Different ways of using scopes

## The scope that is being used with the arguments
top-scope = { $var1 } { $var2 }

## The scope shouldn't change at all
message-scope1 = { int-message-scope1 }
int-message-scope1 = { $var1 } { $var2 }

message-scope2 = { int-message-scope2 }
int-message-scope2 = { int-message-scope1 }

## The scope should change to a scope without
## any values.
term-scope1 = { -int-term-scope1 }
-int-term-scope1 = { $var1 } { $var2 }

## The scope should change to a scope with
## the same variable names and values
term-scope2 = { -int-term-scope2(var1: $var1, var2: $var2) }
-int-term-scope2 = { $var1 } { $var2 }
